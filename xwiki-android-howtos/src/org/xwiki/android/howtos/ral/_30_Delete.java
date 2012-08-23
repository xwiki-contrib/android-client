package org.xwiki.android.howtos.ral;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import android.app.Activity;

public class _30_Delete extends Activity
{
 
    public void delete()
    {

        // init code for getting a rao.
        XWikiApplicationContext ctx = (XWikiApplicationContext) getApplication();
        RESTfulManager rm = ctx.newRESTfulManager();
        DocumentRao rao = rm.getDocumentRao();

        
        try {
            DocumentReference dref=new DocumentReference("WikiName", "spaceName", "pageName");
            rao.delete(dref);
        } catch (RestConnectionException e) {
            // if IO/connection failure
        } catch (RaoException e) {
            // thrown when non existing.
        }

    }
}
