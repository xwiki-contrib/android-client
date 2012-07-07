package org.xwiki.android.client.blog;

import java.util.List;

import org.xwiki.android.blog.svc.BlogDocument;
import org.xwiki.android.blog.svc.BlogDocument.BlogDocumentLocalCallbacks;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.xmodel.svc.DocumentSvc;
import org.xwiki.android.xmodel.svc.DocumentSvcImpl;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadSavedPostsActivity extends ListActivity
{
    private LoadSavedPostsActivity activityCtx;

    private BlogDocumentLocalCallbacks clbk;

    private BlogDocument doc;

    private List<FSDocumentReference> refList;

    private ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DocumentSvc svc = new DocumentSvcImpl();
        activityCtx = this;
        XWikiApplicationContextAPI ctx=(XWikiApplicationContext)getApplicationContext();
        doc = (BlogDocument) ctx.pop("blgDoc");
        init();
        doc.listBlogDocuments(clbk);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        FSDocumentReference fsref = refList.get(position);
        myProgressDialog = ProgressDialog.show(this, "loading", "Please wait...", true);
        doc.load(fsref, clbk);
    }

    private void init()
    {
        clbk = doc.new BlogDocumentLocalCallbacks()
        {

            @Override
            public void onListingComplete(List<FSDocumentReference> rslts)
            {
                refList = rslts;
                String[] list = new String[rslts.size()];
                int i = 0;
                for (FSDocumentReference rslt : rslts) {
                    list[i++] = rslt.getPageName();
                }

                ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(activityCtx, android.R.layout.simple_list_item_1, list);
                setListAdapter(adapter);
            }

            @Override
            public void onBlogPostLoaded()
            {
                myProgressDialog.dismiss();
                setResult(RESULT_OK);
                finish();
            }
        };

    }
}
