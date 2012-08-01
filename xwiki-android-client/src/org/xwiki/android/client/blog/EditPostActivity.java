package org.xwiki.android.client.blog;

import org.xwiki.android.svcx.blog.BlogDocument;
import org.xwiki.android.svc.cmn.SyncService;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.client.R;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class EditPostActivity extends Activity implements OnClickListener
{

    public static final String ARG_UPDATE_DOC = "UPDATE_DOC";

    private static final int REQUEST_CODE_LOADSAVEDPOSTS = 0;

    private final String TAG = this.getClass().getSimpleName();

    private BlogDocument mydoc;
    private BlogDocument.BlogDocumentRemoteCallbacks myRmtClbks;
    private EditText etPost;
    private String title;
    private String category;
    private boolean update;

    // View widgets
    private Button btnSave, btnLoad, btnPost, btnPublish, btnMore;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private EditText etTitle;
    private AutoCompleteTextView actvCategory;
    private PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogeditor);

        etPost = (EditText) findViewById(R.id.etBlogPost);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnPost = (Button) findViewById(R.id.btnPost);
        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnMore = (Button) findViewById(R.id.btnMore);
        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnPublish.setOnClickListener(this);
        btnMore.setOnClickListener(this);

        registerForContextMenu(btnMore);
        // show dialog to get page name, category

        Document d = (Document) getIntent().getSerializableExtra(ARG_UPDATE_DOC);
        if (d != null) {
            mydoc = new BlogDocument(d);
            etPost.setText(mydoc.getContent());
            Log.d(TAG, mydoc.getContent());
            update = true;
        } else {
            showDialog();
        }

    }

    private void showDialog()
    {
        dialog = new Dialog(this);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setTitle("New Blog Post");
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.blog_editor_dialog, null);
        dialog.setContentView(dialogView);
        dialog.show();

        Button okBtn = (Button) dialogView.findViewById(R.id.btn_blog_ok);
        etTitle = (EditText) dialogView.findViewById(R.id.et_blog_title);
        actvCategory = (AutoCompleteTextView) dialogView.findViewById(R.id.actv_blog_category);

        String[] categories = {"Blog.Personal", "Blog.News", "Blog.Other"};
        ArrayAdapter<String> adapterCts = new ArrayAdapter<String>(this, R.layout.searchresults_list_item, categories);
        actvCategory.setAdapter(adapterCts);

        okBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                title = etTitle.getText().toString();
                category = actvCategory.getText().toString();
                if (title == null || category == null || title.equals("") || category.equals("")) {
                    Toast.makeText(getBaseContext(), "Please fill data", Toast.LENGTH_LONG).show();
                } else {
                    mydoc = new BlogDocument("xwiki", "Blog", title, category);
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_LOADSAVEDPOSTS) {
                XWikiApplicationContextAPI ctx = XWikiApplicationContext.getInstance();
                mydoc = (BlogDocument) ctx.pop(LoadSavedPostsActivity.RET_BLOGDOC);
                etPost.setText(mydoc.getContent());
            }
        }

    }

    @Override
    public void onClick(View v)
    {

        if (v.getId() == R.id.btnSave) {
            mydoc.setContent(etPost.getText().toString());
            mydoc.save();

        } else if (v.getId() == R.id.btnLoad) {
            Intent i = new Intent(this, LoadSavedPostsActivity.class);
            startActivityForResult(i, REQUEST_CODE_LOADSAVEDPOSTS);

        } else if (v.getId() == R.id.btnPost) {
            progressDialog = progressDialog.show(this, "Posting", "Please wait...", true);
            myRmtClbks = mydoc.new BlogDocumentRemoteCallbacks()
            {
                @Override
                public void onBlogPostSent(boolean success)
                {
                    progressDialog.dismiss();
                    finish();
                }
            };
            if (update) {
                mydoc.setContent(etPost.getText().toString());
                mydoc.postUpdate(myRmtClbks);
            } else {
                mydoc.setContent(etPost.getText().toString());
                mydoc.post(myRmtClbks);
            }

        } else if (v.getId() == R.id.btnPublish) {
            progressDialog = progressDialog.show(this, "Publishing", "Please wait...", true);
            myRmtClbks = mydoc.new BlogDocumentRemoteCallbacks()
            {
                @Override
                public void onBlogPostSent(boolean success)
                {
                    progressDialog.dismiss();
                    finish();
                }
            };
            mydoc.setContent(etPost.getText().toString());
            if (update) {
                mydoc.publishUpdate(myRmtClbks);
            } else {
                mydoc.publish(myRmtClbks);
            }
            mydoc.setContent(etPost.getText().toString());
            mydoc.publish(myRmtClbks);
        } else if (v.getId() == R.id.btnMore) {
            openContextMenu(v);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {// Context Menu that appears when long clicked. Here invoked programmatically onClic.
        
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blog_editor_more, menu);
       
       // menu.add(Menu.NONE, Menu.FIRST, 0, "sync");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.miSyncDaemon:
                toSyncDaemon(mydoc.unwrap());
                return true;           
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    private void toSyncDaemon(Document d){
        XBlogPost bobj=new XBlogPost();
        bobj.setContent(etPost.getText().toString());
        d.addObject(bobj); //if doc not created this added obj will be added to the newly created page on the server. An update will add this blog object to the current set of objects.
        d.setObject(bobj.getClassName()+"/0", bobj); //if doc is already in server. Seting blogpost obj 0, will make an update operation to update the BlogPostClass/0 object.
        //TODO:Update , syncEntry should be further specified. Update needs more finer control (i.e wether to create or not the new objects in an update op)
        SyncService svc=new SyncService();
        svc.SyncOutLater(d);
    }

}
