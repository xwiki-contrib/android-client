package org.xwiki.android.client.blog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.client.R;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.svcx.blog.BlogDocument;

import org.xwiki.android.svc.xmodel.DocumentLocalSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcs;
import org.xwiki.android.svc.xmodel.DocumentSvc;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Document;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadSavedPostsActivity extends ListActivity implements OnClickListener
{
    public static final String RET_BLOGDOC = "RET_VALUE_DOC";

    private DocumentLocalSvcCallbacks clbk;
    private BlogDocument rsltDoc;
    private List<FSDocumentReference> refList;
    private ArrayAdapter<String> adapter;
    // UI
    private ProgressDialog myProgressDialog;
    private Button btnLoad, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DocumentSvc svc = new DocumentSvcImpl();
        init();
        svc.listBySpace("Blog", clbk);
        setContentView(R.layout.blog_loadsaved_list);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnLoad.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        refList = new LinkedList<FSDocumentReference>();
        // svc.listByTag("SavedBlogPost",clbk);

    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnLoad) {
            ListView lv = getListView();
            SparseBooleanArray a = lv.getCheckedItemPositions();
            int fstSelIndex = -1;

            for (int i = 0; i < a.size(); i++) {
                int key = a.keyAt(i);
                if (a.get(key)) {
                    fstSelIndex = key;
                    break;
                }
            }

            if (fstSelIndex < 0) {
                Toast t = Toast.makeText(LoadSavedPostsActivity.this, "select item to load", 1000);
                t.show();
            } else {
                FSDocumentReference fsref = refList.get(fstSelIndex);
                myProgressDialog = ProgressDialog.show(this, "loading", "Please wait...", true);
                DocumentLocalSvcs svc = new DocumentSvcImpl();
                svc.load(fsref, clbk);
            }

        } else if (v.getId() == R.id.btnDel) {

            ListView lv = getListView();
            SparseBooleanArray a = lv.getCheckedItemPositions();
            int fstSelIndex = -1;

            if (a.get(a.keyAt(0))) {// check at leas one slected
                int[] toDelete = new int[a.size()];
                for (int i = 0; i < a.size(); i++) {
                    int key = a.keyAt(i);
                    if (a.get(key)) {
                        toDelete[i] = key;
                    }
                }
                doDeleteAll(toDelete);
            } else {
                Toast t = Toast.makeText(LoadSavedPostsActivity.this, "select item to delete", 1000);
                t.show();
            }

        }

    }

    private void doDeleteAll(int[] delIndices)
    {
        myProgressDialog = ProgressDialog.show(this, "Deleting", "Please wait...", true);

        List<FSDocumentReference> col = new ArrayList<FSDocumentReference>();
        for (int i : delIndices) {
            col.add(refList.get(i));
        }
        DocumentLocalSvcs svc = new DocumentSvcImpl();
        DocumentLocalSvcCallbacks delClbk = new DocumentLocalSvcCallbacks(delIndices)
        {
            @Override
            public void onDeleteAllComplete(List<FSDocumentReference> failures)
            {
                int[] deletedIndices = (int[]) args[0];
                int shift=0;
                for (int i : deletedIndices) {
                    FSDocumentReference ref = refList.remove(i-shift);
                    shift++;
                    adapter.remove(ref.getPageName());
                }
                if (failures != null && !failures.isEmpty() ) {// very improbable to happen
                    Toast t = Toast.makeText(LoadSavedPostsActivity.this, "some were not deleted", 3000);
                    t.show();
                    refList.addAll(failures);
                    for (FSDocumentReference ref : failures) {
                        adapter.add(ref.getPageName());
                    }
                }

                adapter.notifyDataSetChanged();
                myProgressDialog.dismiss();
            }

        };
        svc.deleteAll(col, delClbk);

    }

    // @Override
    // protected void onListItemClick(ListView l, View v, int position, long id)
    // {
    // FSDocumentReference fsref = refList.get(position);
    // myProgressDialog = ProgressDialog.show(this, "loading", "Please wait...", true);
    // DocumentLocalSvcs svc=new DocumentSvcImpl();
    // svc.load(fsref, clbk);
    // }

    private void init()
    {
        clbk = new DocumentLocalSvcCallbacks()
        {

            @Override
            public void onListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby)
            {

                refList.addAll(rslts);
                ArrayList<String> list = new ArrayList<String>();
                int i = 0;
                for (FSDocumentReference rslt : rslts) {
                    list.add(rslt.getPageName());
                }

                adapter =
                    new ArrayAdapter<String>(LoadSavedPostsActivity.this,
                        android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, list);
                setListAdapter(adapter);
                getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            }

            @Override
            public void onLoadComplete(Document entity)
            {
                myProgressDialog.dismiss();
                setResult(RESULT_OK);
                rsltDoc = new BlogDocument(entity);
                XWikiApplicationContextAPI ctx = XWikiApplicationContext.getInstance();
                ctx.put(RET_BLOGDOC, rsltDoc);
                finish();
            }

        };

    }

}
