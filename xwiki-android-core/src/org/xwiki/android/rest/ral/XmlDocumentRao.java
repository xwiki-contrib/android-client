package org.xwiki.android.rest.ral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.context.UserSession;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.XWikiRestfulAPI;
import org.xwiki.android.rest.ral.algo.DocRetreiveStrategy;
import org.xwiki.android.rest.ral.algo.DocUpdateStrategy;
import org.xwiki.android.rest.ral.algo.IDocRetreiveStrategy;
import org.xwiki.android.rest.ral.algo.IDocUpdateStragegy;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.transformation.DocLaunchPadForXML;
import org.xwiki.android.rest.transformation.DocumentDismantler_XML;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.util.Log;

/**
 * uses simplexml Rest model. Non public. Use XMLRestfulManager to create new. Very primitive implementation with single
 * Http Connection for all work.
 *  
 * @author xwiki gsoc 2012
 * @version 0.8 alpha
 */
// TODO: current impl only support single http connection through Requests
// class. Implement for multiple parallel connections to reduce latency.
class XmlDocumentRao implements DocumentRao
{
    private static String tag = "DocumentRao_XML";
    private static int PAGE = DocumentDismantler_XML.PAGE;
    private static int OBJECTS = DocumentDismantler_XML.OBJECTS;
    private static int ALL = DocumentDismantler_XML.ALL;
    private XWikiRestfulAPI api;
    private IDocRetreiveStrategy retStr;
    private IDocUpdateStragegy updStr;

    public XmlDocumentRao(String serverUrl, String username, String password)
    {

        api = new XWikiRestConnector(serverUrl, username, password);
        retStr = new DocRetreiveStrategy(username, password, serverUrl);
        updStr = new DocUpdateStrategy(serverUrl, username, password);
        // consider IoC to app context
    }

    /**
     * create the doc on server.
     */
    @Override
    public Document create(Document doc) throws RestConnectionException, RaoException
    {
        // TODO: make advanced algo.Put it in ral.algo package. Here we use simple non parellal way to create doc.
        DocumentDismantler_XML con = new DocumentDismantler_XML(ALL);
        DocLaunchPadForXML pad = con.convertDocument(doc);

        Page page = pad.getPage();
        String wikiName = pad.getWikiName();
        String spaceName = pad.getSpaceName();
        String pageName = pad.getPageName();

        try {
            if (api.existsPage(wikiName, spaceName, pageName)) {
                throw new RaoException("document already exists: try updating the doucment");
            } else {
                api.addPage(wikiName, spaceName, pageName, page); 
                
                
                // start Algo . Order objects by number, fill with new
                LinkedList<Object> obList=new LinkedList<Object>();//'cause we need to add in the middle
                class Index{                   
                    public Index(int first, int last)
                    {                        
                        this.first = first;
                        this.last = last;
                    }
                    int first;
                    int last;
                }
                Map<String,Index> indexes=new HashMap<String, Index>(10);//for < 10 classes of objs.
                               
                Map<String, Object> edObjs = pad.getEditedObjects();
                
                A:for(String k: edObjs.keySet()){
                    String ss[]=k.split("/");
                    String clsName=ss[0];
                    Object o=edObjs.get(k);
                    Index i= indexes.get(clsName);
                    if(i==null){//new class    
                        obList.add(o);
                        int first=obList.size();
                        int last=first;
                        indexes.put(clsName, new Index(first,last));
                    }else{
                        int last=i.last;
                        for(int j=i.first; j<=last;j++){
                            Object curr=obList.get(j);
                            if(o.getNumber()<curr.getNumber()){
                                obList.add(j,o);
                                i.last++;
                                continue A;
                            }
                        }
                        obList.add(last+1,edObjs.get(k));
                        i.last++;
                    }
                }
                
                List<Object> newObjects = pad.getNewObjects();
                
                A:for(Object o:newObjects){                    
                    String clsName=o.getClassName();
                    Index i=indexes.get(clsName);
                    if(i==null){//this is new object
                       obList.add(o); 
                       int first=obList.size();
                       int last=first;
                       indexes.put(clsName, new Index(first, last));
                    }else{
                        int first=i.first;
                        int last=i.last;
                        
                        Object prev=obList.get(first);
                        for(int j=first+1; j<=last; j++){
                           Object curr=obList.get(j);
                           if(curr.getNumber()-prev.getNumber()>1){
                               obList.add(j, o);
                               last++;
                               i.last=last;
                               continue A; //restart from A.
                           }
                        }
                        //if did not get added add to the end.
                        obList.add(last+1,o);
                        i.last= ++last;
                    }
                    
                }
                //end algo
                
                //upload objects.                
                for (Object object : obList) {
                    api.addObject(wikiName, spaceName, pageName, object);
                }                
                return null; // TODO RET CREATED DOC. NEED TO ADD CUSTOMIZATION CODE WETHER TO RET OR NOT.
            }
        } catch (RestException e) {
            Log.d(tag, "couldn't create page" + e.getCode());
            e.printStackTrace();
            throw new RaoException(e);
        }

    }

    @Override
    public Document retreive(Document doc) throws RestConnectionException, RaoException
    {
        return retStr.retreive(doc);
    }

    @Override
    public Document retreive(DocumentReference dref) throws RestConnectionException, RaoException
    {
        return retStr.retreive(dref);
    }

    @Override
    public Document retreive(DocumentReference dref, int flags)
    {
        return retStr.retreive(dref, flags);
    }

    @Override
    public Document retreive(DocumentReference dref, int flags, String... objTypeArgs)
    {
        return retStr.retreive(dref, flags, objTypeArgs);
    }

    @Override
    public List<Document> querry() throws RestConnectionException, RaoException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Document update(Document res) throws RestConnectionException, RaoException
    {
        return updStr.update(res);
    }

    @Override
    public void delete(DocumentReference dRef) throws RestConnectionException, RaoException
    {
        DocumentReference docRef = dRef;
        String wikiName = docRef.getWikiName();
        String spaceName = docRef.getSpaceName();
        String pageName = docRef.getPageName();
        try {
            api.deletePage(wikiName, spaceName, pageName);
        } catch (RestException e) {
            e.printStackTrace();
            throw new RaoException("Could not dletete", e);
            // TODO: Identify reason for unable to delete. Use e.getCode to identify.
        }

    }

    @Override
    public void delete(Document resrc) throws RestConnectionException, RaoException
    {
        delete(resrc.getDocumentReference());
    }

}
