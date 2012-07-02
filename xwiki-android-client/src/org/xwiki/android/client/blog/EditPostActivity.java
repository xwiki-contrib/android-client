package org.xwiki.android.client.blog;

import org.xwiki.android.client.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditPostActivity extends Activity implements OnClickListener {
	
	private Button btnSave,btnLoad,btnPost,btnPublish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blogeditor);
		
		btnSave=(Button) findViewById(R.id.btnSave);
		btnLoad=(Button) findViewById(R.id.btnLoad);
		btnPost=(Button) findViewById(R.id.btnPost);
		btnPublish=(Button) findViewById(R.id.btnPublish);
		btnSave.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		btnPublish.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId()==R.id.btnSave) {
			
		}else if(v.getId()==R.id.btnLoad){

		}else if(v.getId()==R.id.btnPost){

		}else if(v.getId()==R.id.btnPublish){

		}
		
	}
}
