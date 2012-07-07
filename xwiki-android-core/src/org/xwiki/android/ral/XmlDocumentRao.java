package org.xwiki.android.ral;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.context.UserSession;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.ral.reference.EntityReference;
import org.xwiki.android.ral.transformation.SimpleXmlDocpad;
import org.xwiki.android.ral.transformation.XMdlToSmplConverter;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.rest.PageResources;
import org.xwiki.android.rest.Requests;
import org.xwiki.android.rest.XWikiRestfulAPI;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.SimpleDocument;

/**
 * @author xwiki gsoc 2012
 * @version 0.8 alpha uses simplexml Rest model. Non public. Use XMLRestfulManager to create new. Very primitive
 *          implementation with single Http Connection for all work. *
 */
// TODO: current impl only support single http connection through Requests
// class. Implement for multiple parallel connections to reduce latency.
class XmlDocumentRao implements Rao<Document>
{
    private static int PAGE = XMdlToSmplConverter.PAGE;

    private static int OBJECTS = XMdlToSmplConverter.OBJECTS;

    private RaoCallbackForDocument mycallback;

    private XWikiRestfulAPI api;

    public XmlDocumentRao(RaoCallbackForDocument callback)
    {
        this.mycallback = callback;
        UserSession session = XWikiApplicationContext.getInstance().getUserSession();
        api = new Requests(session.getRealm(), session.getUserName(), session.getPassword());
    }

    /**
     * create the doc on server.
     */
    public void create(Document res)
    {// Only simpleDocument is supported yet.
        // TODO:multiple connections can be handled at HTTPClient also.Research needed.
        ConnectionThread con1 = new ConnectionThread((SimpleDocument) res)
        {
            public void run()
            {
                XMdlToSmplConverter con = new XMdlToSmplConverter(PAGE + OBJECTS);
                SimpleXmlDocpad pad = con.convertDocument(doc);

                Page page = pad.getPage();
                String wikiName = pad.getWikiName();
                String spaceName = pad.getSpaceName();
                String pageName = pad.getPageName();
                String statusLine = api.addPage(wikiName, spaceName, pageName, page);
                if (statusLine.contains("20")) { // TODO: knowledge of XWikiRestful API should be transfered to Request.
                                                 // Intuitively the throwing of RestAPIUsageException also should be
                                                 // moved.
                                                 // current approach will rslt in status line checking all over the
                                                 // code!
                                                 // suitable exception hierarchy should be implemented. checked
                                                 // exceptions for
                                                 // client side connection errors. Unchecked ones for bad api
                                                 // usage.(because they
                                                 // cannot be rectified even if caught)
                    for (Object object : pad.getNewObjects()) {// TODO: these can be done in parallel
                        if (statusLine.equals("error"))
                            break;
                        statusLine = api.addObject(wikiName, spaceName, pageName, object);
                    }
                }
                // there are no altered objects to be updated since the doc is new.
                // TODO:current impl of Request does not return response body. So null for doc.
                if (!statusLine.equals("error")) {
                    // 304 if there was error in page representation.
                    // 201 created.
                    if (statusLine.contains("201")) {
                        mycallback.invokeCreated(null);
                    } else {
                        throw new RestAPIUsageException("status line is:" + statusLine);
                    }
                } else {
                    mycallback.handleException(new IOException(
                        "Don't know why ;-)//TODO: refactor lower layer to return the exception"));
                }
            }
        };
        con1.start();
    }

    public void retreive(EntityReference<Document> docRef)
    {

    }

    public void querry()
    {

    }

    public void update(Document res)
    {

    }

    public void delete(EntityReference<Document> docRef)
    {
        ConnectionThread con1 = new ConnectionThread((DocumentReference) docRef)
        {
            public void run()
            {
                String wikiName = dref.getWikiName();
                String spaceName = dref.getSpaceName();
                String pageName = dref.getPageName();
                api.deletePage(wikiName, spaceName, pageName);
            };
        };
        con1.start();
    }

    private abstract class ConnectionThread extends Thread
    {
        SimpleDocument doc;

        DocumentReference dref;

        ConnectionThread(DocumentReference docref)
        {
            dref = docref;
        }

        ConnectionThread(SimpleDocument d)
        {
            doc = d;
        }

        public abstract void run();
    }

}
