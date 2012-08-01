package org.xwiki.android.client.blog;

import org.xwiki.android.client.R;
import org.xwiki.android.client.R.layout;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.svcx.blog.BlogDocument;
import org.xwiki.android.svc.xmodel.DocumentRemoteSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentSvc;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.entity.Document;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Blogger extends Activity implements OnClickListener
{

    private static final int REQUEST_CODE_EDITPOST = 0;

    private Button btn_newpost,btn_retreive;
    private EditText etPageName;

    private Dialog ret_dialog;
    private ProgressDialog progDialog;
    
    private DocumentSvc dsvc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogmain);// show loading screen
        // startActivity(new Intent(this,BlogPrefernces.class));
        btn_newpost = (Button) findViewById(R.id.btn_newPost);
        btn_newpost.setOnClickListener(this);
        btn_retreive = (Button) findViewById(R.id.blg_btn_retreive);
        btn_retreive.setOnClickListener(this);
        
        dsvc=new DocumentSvcImpl(); 
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_newPost) {
            Intent intent = new Intent(this, EditPostActivity.class);
            startActivityForResult(intent, REQUEST_CODE_EDITPOST);
        } else if (v.getId() == R.id.blg_btn_retreive) {
            showRetreiveDialog();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE_EDITPOST) {

        }
    }

    private void retreiveAndLaunchEditDoc()
    {
    	
    }

    private void showRetreiveDialog()
    {
        ret_dialog = new Dialog(this);
        ret_dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        ret_dialog.setTitle("Page to retreive");
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.blog_retreive_dialog, null);
        ret_dialog.setContentView(dialogView);
        ret_dialog.show();

        Button okBtn = (Button) dialogView.findViewById(R.id.blg_ret_btn_ok);
        etPageName = (EditText) dialogView.findViewById(R.id.blg_ret_et_pageName);

        okBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DocumentReference dref=new DocumentReference("xwiki","Blog",etPageName.getText().toString());
                DocumentRemoteSvcCallbacks clbk=new DocumentRemoteSvcCallbacks()
                {
                    @Override
                    public void onFullyRetreived(Document res, boolean success, RaoException ex)
                    {
                       if(success){
                           ret_dialog.dismiss();
                           progDialog.dismiss();
                           Intent i=new Intent(Blogger.this,EditPostActivity.class); 
                           i.putExtra(EditPostActivity.ARG_UPDATE_DOC, res);
                           startActivity(i);
                       }else{
                           progDialog.dismiss();  
                       }
                                              
                    }
                };
                progDialog=ProgressDialog.show(Blogger.this, "Processing", "Fetching document from server");
                dsvc.retreive(dref, clbk); 
            }
        });
    }
}
