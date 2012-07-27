package org.xwiki.android.client.blog;

import java.util.List;
import java.util.Map;
import java.util.Set;




import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.svc.blog.BlogDocument;

import org.xwiki.android.svc.xmodel.DocumentLocalSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcs;
import org.xwiki.android.svc.xmodel.DocumentSvc;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Document;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class LoadSavedPostsActivity extends ListActivity
{    
    public static final String RET_BLOGDOC="RET_VALUE_DOC";
    
    private DocumentLocalSvcCallbacks clbk;

    private BlogDocument rsltDoc;

    private List<FSDocumentReference> refList;

    private ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DocumentSvc svc = new DocumentSvcImpl();
        init();
        svc.listBySpace("Blog", clbk);
        // svc.listByTag("SavedBlogPost",clbk);     
       
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        FSDocumentReference fsref = refList.get(position);
        myProgressDialog = ProgressDialog.show(this, "loading", "Please wait...", true);
        DocumentLocalSvcs svc=new DocumentSvcImpl();
        svc.load(fsref, clbk);
    }

    private void init()
    {
        clbk = new DocumentLocalSvcCallbacks()
        {
           
            @Override
            public void onListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby)
            {
                refList = rslts;
                String[] list = new String[rslts.size()];
                int i = 0;
                for (FSDocumentReference rslt : rslts) {
                    list[i++] = rslt.getPageName();
                }

                ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoadSavedPostsActivity.this, android.R.layout.simple_list_item_1, list);
                setListAdapter(adapter);
            }

            @Override
            public void onLoadComplete(Document entity)
            {
                myProgressDialog.dismiss();
                setResult(RESULT_OK);
                rsltDoc=new BlogDocument(entity);
                XWikiApplicationContextAPI ctx=XWikiApplicationContext.getInstance();
                ctx.put(RET_BLOGDOC, rsltDoc);
                finish();
            }
        };

    }
}


