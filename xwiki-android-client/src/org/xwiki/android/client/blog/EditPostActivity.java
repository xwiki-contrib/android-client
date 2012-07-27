package org.xwiki.android.client.blog;

import org.xwiki.android.svc.blog.BlogDocument;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPostActivity extends Activity implements OnClickListener {
	
    public static final String ARG_UPDATE_DOC="UPDATE_DOC";
    
	private static final int REQUEST_CODE_LOADSAVEDPOSTS=0;
	
	private final String TAG=this.getClass().getSimpleName();
		
	private Button btnSave,btnLoad,btnPost,btnPublish;
	private BlogDocument mydoc;
	private BlogDocument.BlogDocumentRemoteCallbacks myRmtClbks;
	private EditText etPost;
	private String title;
	private String category;
	ProgressDialog progressDialog;
	Dialog dialog;
	EditText etTitle;
	AutoCompleteTextView actvCategory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blogeditor);
		
		etPost=(EditText) findViewById(R.id.etBlogPost);
		btnSave=(Button) findViewById(R.id.btnSave);
		btnLoad=(Button) findViewById(R.id.btnLoad);
		btnPost=(Button) findViewById(R.id.btnPost);
		btnPublish=(Button) findViewById(R.id.btnPublish);
		btnSave.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		btnPublish.setOnClickListener(this);
		
		//show dialog to get page name, category
		
		Document d=(Document) getIntent().getSerializableExtra("UPDATE_DOC");
        if(d!=null){            
            mydoc=new BlogDocument(d);
            etPost.setText(mydoc.getContent());
            Log.d(TAG,mydoc.getContent());
        }else{
            showDialog();
        }
		
		
		
		
		
	}
	
	
	private void showDialog(){
	    dialog = new Dialog(this); 
	    dialog.getWindow().setFlags( 
	    WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
	    WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
	    dialog.setTitle("New Blog Post"); 
	    LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	    View dialogView = li.inflate(R.layout.blog_editor_dialog, null); 
	    dialog.setContentView(dialogView); 
	    dialog.show(); 

	    Button okBtn = (Button) dialogView.findViewById(R.id.btn_blog_ok); 
	    etTitle=(EditText) dialogView.findViewById(R.id.et_blog_title);
	    actvCategory=(AutoCompleteTextView) dialogView.findViewById(R.id.actv_blog_category);
	    
	    String[] categories={"Blog.Personal","Blog.News","Blog.Other"};
	    ArrayAdapter<String> adapterCts=new ArrayAdapter<String>(this, R.layout.searchresults_list_item, categories);
	    actvCategory.setAdapter(adapterCts);
	    
	    okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {           
                title=etTitle.getText().toString();
                category=actvCategory.getText().toString();
                if(title==null || category==null || title.equals("") || category.equals("")){
                    Toast.makeText(getBaseContext(), "Please fill data", 
                        Toast.LENGTH_LONG).show(); 
                }else{
                  mydoc=new BlogDocument("xwiki", "Blog", title, category);
                  dialog.dismiss();  
                }
            }   
        }); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==RESULT_OK){
			if(requestCode==REQUEST_CODE_LOADSAVEDPOSTS){
			    XWikiApplicationContextAPI ctx=XWikiApplicationContext.getInstance();
			    mydoc=(BlogDocument) ctx.pop(LoadSavedPostsActivity.RET_BLOGDOC);
				etPost.setText(mydoc.getContent());			    
			}
		}
		
	}

	@Override
    public void onClick(View v) {
		
	    if (v.getId()==R.id.btnSave) {
		    mydoc.setContent(etPost.getText().toString());
		    mydoc.save();
		    
		}else if(v.getId()==R.id.btnLoad){
			Intent i=new Intent(this,LoadSavedPostsActivity.class);
			startActivityForResult(i,REQUEST_CODE_LOADSAVEDPOSTS);
		
		}else if(v.getId()==R.id.btnPost){			
            progressDialog =progressDialog.show(this, "Posting", "Please wait...", true);
			myRmtClbks=mydoc.new BlogDocumentRemoteCallbacks() {			
				@Override
				public void onBlogPostSent(boolean success) {
					progressDialog.dismiss();
					finish();
				}
			};
			mydoc.setContent(etPost.getText().toString());
			mydoc.post(myRmtClbks);
			
		}else if(v.getId()==R.id.btnPublish){			
            progressDialog =progressDialog.show(this, "Publishing", "Please wait...", true);
			myRmtClbks=mydoc.new BlogDocumentRemoteCallbacks(){			
				@Override
				public void onBlogPostSent(boolean success) {
					progressDialog.dismiss();
					finish();
				}
			};
			mydoc.setContent(etPost.getText().toString());
			mydoc.publish(myRmtClbks);
		}
		
	}
	
	
	
}
