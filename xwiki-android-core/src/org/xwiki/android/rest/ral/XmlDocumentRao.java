package org.xwiki.android.rest.ral;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.context.UserSession;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.algo.DocRetreiveStrategy;
import org.xwiki.android.rest.ral.algo.DocUpdateStrategy;
import org.xwiki.android.rest.ral.algo.IDocRetreiveStrategy;
import org.xwiki.android.rest.ral.algo.IDocUpdateStragegy;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.rest.transformation.DocLaunchPadForXML;
import org.xwiki.android.rest.transformation.DocumentDismantler_XML;
import org.xwiki.android.rest.transformation.RestModelTransformer;
import org.xwiki.android.xmodel.entity.Attachment;
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
    private static String TAG = XmlDocumentRao.class.getSimpleName();
    private static int PAGE = DocumentDismantler_XML.PAGE;
    private static int OBJECTS = DocumentDismantler_XML.OBJECTS;
    private static int ALL = DocumentDismantler_XML.ALL;
    private XWikiAPI api;
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
        Verifier.verifyDocumentForCreate(doc);// just logs warn msgs if verification failed. Runtime exceptions will be
                                              // thrown
        // when server responds with errors.

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

                // start Algo . Order objects by number, fill with new --> try to keep the number seq of created obj in
                // server, if objects are numbered.
                // keeping seq makes parallelizations hard. But this semantic must be guaranteed by the RAL Create
                // methods. Note: when paralleling You have to add work for jobs at points in this algo. Do it in
                // another strategy.
                LinkedList<Object> obList = new LinkedList<Object>();// 'cause we need to add in the middle
                class Index
                {
                    public Index(int first, int last)
                    {
                        this.first = first;
                        this.last = last;
                    }

                    int first;
                    int last;
                }
                Map<String, Index> indexes = new HashMap<String, Index>(10);// for < 10 classes of objs.

                Map<String, Object> edObjs = pad.getEditedObjects(); // objects added using set method. explicitly
                                                                     // palced at smplObject.number

                A: for (String k : edObjs.keySet()) {
                    String ss[] = k.split("/");
                    String clsName = ss[0];
                    Object o = edObjs.get(k);
                    Index i = indexes.get(clsName);
                    if (i == null) {// new class
                        obList.add(o);
                        int first = obList.size() - 1;
                        int last = first;
                        indexes.put(clsName, new Index(first, last));
                    } else {
                        int last = i.last;
                        for (int j = i.first; j <= last; j++) {
                            Object curr = obList.get(j);
                            if (o.getNumber() < curr.getNumber()) {
                                obList.add(j, o);
                                i.last++;
                                continue A;
                            }
                        }
                        obList.add(last + 1, o);
                        i.last++;
                    }
                }

                List<Object> newObjects = pad.getNewObjects();

                A: for (Object o : newObjects) {
                    String clsName = o.getClassName();
                    Index i = indexes.get(clsName);
                    if (i == null) {// this is new object
                        obList.add(o);
                        int first = obList.size() - 1;
                        int last = first;
                        indexes.put(clsName, new Index(first, last));
                    } else {
                        int first = i.first;
                        int last = i.last;

                        Object prev = obList.get(first);
                        for (int j = first + 1; j <= last; j++) {
                            Object curr = obList.get(j);
                            if (curr.getNumber() - prev.getNumber() > 1) {
                                obList.add(j, o);
                                last++;
                                i.last = last;
                                continue A; // restart from A.
                            }
                        }
                        // if did not get added add to the end.
                        obList.add(last + 1, o);
                        i.last = ++last;
                    }

                }
                // end algo

                // upload objects.
                for (Object object : obList) {
                    api.addObject(wikiName, spaceName, pageName, object);
                }

                List<Comment> newCmnts = pad.getNewComments();
                List<Comment> edCmnts = pad.getEditedComments();
                
                List<Comment> cmnts=new LinkedList<Comment>(newCmnts);
                
                if(edCmnts.size()>0){
                    RestModelTransformer transformer=new RestModelTransformer();
                    Comparator<Comment> comparator=new Comparator<Comment>()
                        {
                            @Override
                            public int compare(Comment c1, Comment c2)
                            {                        
                                return c1.getId()-c2.getId();
                            }
                        };
                        Collections.sort(edCmnts, comparator);
                       
                        Comment ec;
                        for(int i=0 ; i<edCmnts.size(); i++){
                            ec=edCmnts.get(i);
                            if(ec.getId()<cmnts.size()){
                                cmnts.add(ec.id, ec);
                            }else{
                                List<Comment>collection=edCmnts.subList(i, edCmnts.size());
                                cmnts.addAll(collection);
                                break;
                            }
                        }
                }
                for(int i=0; i<cmnts.size(); i++){
                    Comment p=cmnts.get(i);
                    int tmpId=p.getId();
                    int id=i;
                    p.setId(id);
                    for(int j=i+1; j<cmnts.size();j++){
                        Comment c=cmnts.get(j);
                        if(c.getReplyTo()!=null && c.replyTo==tmpId){
                            c.setReplyTo(id);
                        }
                    }
                }
                
                for(Comment comment:cmnts){
                    api.addPageComment(wikiName, spaceName, pageName, comment);
                }
                
            
                
                Tags tags= pad.getTags();
                if(tags!=null){
                    api.setTags(wikiName, spaceName, pageName, tags);
                }
                
                //attachments
                for (Attachment a : pad.getAttatchmentsToupload()) {
                    File f=a.getFile();
                    if(f!=null){
                        String filePath=f.getParentFile().getAbsolutePath()+"/"; 
                        String attachmentName=a.getName();
                        api.putPageAttachment(wikiName, spaceName, pageName, filePath, attachmentName);
                    }else{
                        Log.e(TAG, "cant upload attachment. file not set");  
                    }               
                    
                }
                
                
                
                return null; // TODO RET CREATED DOC. NEED TO ADD CUSTOMIZATION CODE WETHER TO RET OR NOT.
            }
        } catch (RestException e) {
            Log.d(TAG, "couldn't create page" + e.getCode());
            e.printStackTrace();
            throw new RaoException(e);
        }
    }

    @Override
    public boolean exists(Document doc) throws RestConnectionException, RaoException
    {
        String wikiName = doc.getWikiName();
        String spaceName = doc.getSpaceName();
        String pageName = doc.getPageName();
        boolean exists;
        try {
            exists = api.existsPage(wikiName, spaceName, pageName);
            return exists;
        } catch (RestException e) {
            throw new RaoException(e);
        }
    }

    @Override
    public boolean exists(DocumentReference dref) throws RestConnectionException, RaoException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Document retreive(Document doc) throws RestConnectionException, RaoException
    {
        return retStr.retreive(doc);
    }
    
    @Override
    public Document retreive(Document doc, FetchConfig lazyConfig)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document retreive(DocumentReference dref) throws RestConnectionException, RaoException
    {
        return retStr.retreive(dref);
    }

    @Override
    public Document retreive(DocumentReference dref, FetchConfig lazyConfig)
    {
        return retStr.retreive(dref, lazyConfig);
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

/** 
 * old algo for ordering comments. Which was done because earlier the doc pad returned Objects res as edited comment. This went stupidly long.
 * 
 * 
 * int svrCmntId=-1;
                int used = 0; // how many new comments used
                int avail = newCmnts.size();
                int lastId = -1;
                
                if(edCmnts.size()>0){
                    RestModelTransformer transformer=new RestModelTransformer();
                    Comparator<Comment> comparator=new Comparator<Comment>()
                        {
                            @Override
                            public int compare(Comment c1, Comment c2)
                            {                        
                                return c1.getId()-c2.getId();
                            }
                        };
                        Collections.sort(edCmnts, comparator);
                        // start order comment upload. Try create the setComment(cmnt with id 1) to match the same id in server.
                        
                        for (int i = 0; i < edCmnts.size(); i++) {
                            Comment ec = edCmnts.get(i);
                            int thisId = ec.getId();
                            if (thisId > lastId + 1) {
                                int needed = thisId - lastId-1;
                                lastId = thisId;
                                if (used < avail) {
                                    int start = used;
                                    int end = Math.min(avail, used + needed);
                                    for (int j = used; j < end; j++) {
                                        Comment cnw=newCmnts.get(j);
                                        int tmpId=cnw.getId();
                                        cnw.setId(++svrCmntId);
                                        api.addPageComment(wikiName, spaceName, pageName, cnw);                                
                                        for(Comment c:newCmnts){
                                            if(c.getReplyTo()!=null && c.getReplyTo()==tmpId){
                                                c.setReplyTo(svrCmntId);
                                            }
                                        }
                                        for(Comment c:edCmnts){
                                            if(c.getReplyTo()!=null && c.getReplyTo()==tmpId){
                                                c.setReplyTo(svrCmntId);
                                            }
                                        }
                                    }
                                    
                                    
                                } 
                                Object eo = transformer.toObject(ec);
                                api.addObject(wikiName, spaceName, pageName,eo );  
                                if(used+needed>avail){//if avail<used+needed edited comment id gets changed. That is cmnt set with id 5 may get put at cmnt 3 because no comments without an id to fill the gap.
                                    
                                }
                                svrCmntId++;
                                used += needed;
                            } else { 
                                lastId=thisId;
                                Object eo = transformer.toObject(ec);
                                api.addObject(wikiName, spaceName, pageName, eo); 
                                svrCmntId++;
                            }
                        }
                }
                
                
                if(used<avail){
                    for (int i = used; i < newCmnts.size(); i++){
                        Comment prnt=newCmnts.get(i);
                        int tmpId=prnt.getId();
                        prnt.setId(++svrCmntId);
                        api.addPageComment(wikiName, spaceName, pageName, prnt);                        
                        for(Comment child:newCmnts){
                            if(child.getReplyTo()!=null &&child.getReplyTo()==tmpId){
                                child.setReplyTo(svrCmntId);
                            }
                        }                       
                    }
                }
 **/
