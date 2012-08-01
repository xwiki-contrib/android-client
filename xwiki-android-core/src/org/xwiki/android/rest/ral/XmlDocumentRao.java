package org.xwiki.android.rest.ral;

import java.util.List;

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
import android.util.Log;

/**
 * @author xwiki gsoc 2012
 * @version 0.8 alpha uses simplexml Rest model. Non public. Use XMLRestfulManager to create new. Very primitive
 *          implementation with single Http Connection for all work. *
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

    public XmlDocumentRao(String serverUrl,String username,String password)    {       

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
        //TODO: make advanced algo.Put it in  ral.algo package. Here we use simple non parellal way to create doc.
        DocumentDismantler_XML con = new DocumentDismantler_XML(ALL);
        DocLaunchPadForXML pad = con.convertDocument(doc);

        Page page = pad.getPage();
        String wikiName = pad.getWikiName();
        String spaceName = pad.getSpaceName();
        String pageName = pad.getPageName();
       

        try {         
            if(api.existsPage(wikiName, spaceName, pageName)){
                throw new RaoException("document already exists: try updating the doucment");
            }else{
                api.addPage(wikiName, spaceName, pageName, page); 
                for (Object object : pad.getNewObjects()) {                
                    api.addObject(wikiName, spaceName, pageName, object);
                }            
                return null; //TODO RET CREATED DOC. NEED TO ADD CUSTOMIZATION CODE WETHER TO RET OR NOT.
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
            throw new RaoException("Could not dletete",e);
            //TODO: Identify reason for unable to delete. Use e.getCode to identify.
        }

    }

    @Override
    public void delete(Document resrc) throws RestConnectionException, RaoException
    {
        delete(resrc.getDocumentReference());
    }

}
