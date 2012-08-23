package org.xwiki.android.howtos.ral;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import android.app.Activity;

public class _10_Retreive extends Activity
{
    public void retreive()
    {

        // init code for getting a rao.
        XWikiApplicationContext ctx = (XWikiApplicationContext) getApplication();
        RESTfulManager rm = ctx.newRESTfulManager();
        DocumentRao rao = rm.getDocumentRao();

        // we then simply call the retreive method.
        Document retDoc;
        try {
            Document doc=new Document("WikiName", "spaceName", "pageName");            
            retDoc=rao.retreive(doc);
            /*
             * following is also valid
             */
            DocumentReference docref=new DocumentReference("WikiName", "spaceName", "pageName");//creating a ref is faster than a doc.
            retDoc=rao.retreive(docref); 
        } catch (RestConnectionException e) {
            // if IO/connection failure
        } catch (RaoException e) {
            // thrown when non existing.
        }

    }
}
