package org.xwiki.android.ral;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.context.UserSession;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.ral.reference.EntityReference;
import org.xwiki.android.ral.transformation.DocLaunchPadForXML;
import org.xwiki.android.ral.transformation.DocumentDismantler_XML;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.rest.PageResources;
import org.xwiki.android.rest.Requests;
import org.xwiki.android.rest.IllegalRestUsageException;
import org.xwiki.android.rest.RestConnectorException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.XWikiRestfulAPI;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XUtils;

/**
 * @author xwiki gsoc 2012
 * @version 0.8 alpha uses simplexml Rest model. Non public. Use XMLRestfulManager to create new. Very primitive
 *          implementation with single Http Connection for all work. *
 */
// TODO: current impl only support single http connection through Requests
// class. Implement for multiple parallel connections to reduce latency.
class XmlDocumentRao implements DocumentRao
{
    private static int PAGE = DocumentDismantler_XML.PAGE;
    private static int OBJECTS = DocumentDismantler_XML.OBJECTS;
    private static int ALL = DocumentDismantler_XML.ALL;
    private XWikiRestfulAPI api;

    public XmlDocumentRao()
    {
        UserSession session = XWikiApplicationContext.getInstance().getUserSession();
        api = new XWikiRestConnector(session.getRealm(), session.getUserName(), session.getPassword());
        // consider IoC to app context
    }

    /**
     * create the doc on server.
     */
    @Override
    public Document create(Document doc) throws RestConnectorException, RaoException
    {
        DocumentDismantler_XML con = new DocumentDismantler_XML(ALL);
        DocLaunchPadForXML pad = con.convertDocument(doc);

        Page page = pad.getPage();
        String wikiName = pad.getWikiName();
        String spaceName = pad.getSpaceName();
        String pageName = pad.getPageName();
        String statusLine;

        statusLine = api.addPage(wikiName, spaceName, pageName, page);
        int statusCode = RALUtils.getStatusCode(statusLine);

        if (200 <= statusCode & statusCode < 300) {
            for (Object object : pad.getNewObjects()) {// TODO: these can be
                                                       // done in parallel
                if (statusLine.equals("error")) {// TODO:returning error should be totally removed from underlying
                                                 // layer.
                    break;
                }
                statusLine = api.addObject(wikiName, spaceName, pageName, object);
            }
        }
        // there are no altered objects to be updated since the doc is new.
        // TODO:current impl of Request does not return response body. So null
        // for doc.
        // 304 if there was error in page representation.
        // 201 created.
        int code = RALUtils.getStatusCode(statusLine);
        if (200 <= code & code < 300) {
            return null;
        } else {
            throw new IllegalRestUsageException("status line is:" + statusLine);
        }

    }

    @Override
    public Document retreive(Document doc) throws RestConnectorException, RaoException
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Document retreive(DocumentReference dref) throws RestConnectorException, RaoException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Document retreive(DocumentReference dref, int flags)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document retreive(DocumentReference dref, int flags, String... objTypeArgs)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Document> querry() throws RestConnectorException, RaoException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Document update(Document res) throws RestConnectorException, RaoException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(DocumentReference dRef) throws RestConnectorException, RaoException
    {
        DocumentReference docRef = dRef;
        String wikiName = docRef.getWikiName();
        String spaceName = docRef.getSpaceName();
        String pageName = docRef.getPageName();
        api.deletePage(wikiName, spaceName, pageName);

    }

    

    @Override
    public void delete(Document resrc) throws RestConnectorException, RaoException
    {
        delete(resrc.getDocumentReference());        
    }

   

    

}
