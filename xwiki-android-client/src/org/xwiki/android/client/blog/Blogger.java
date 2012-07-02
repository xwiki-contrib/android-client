package org.xwiki.android.client.blog;
import org.xwiki.android.client.R;
import org.xwiki.android.client.R.layout;
import org.xwiki.android.components.login.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Blogger extends Activity implements OnClickListener {

	private static final int REQUEST_CODE_EDITPOST = 0;
	
	
	private Button btn_newpost;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {		
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.blogmain);//show loading screen
		 //startActivity(new Intent(this,BlogPrefernces.class));
		 btn_newpost=(Button) findViewById(R.id.btn_newPost);
		 btn_newpost.setOnClickListener(this);
	}

	public void onClick(View v) {
		if(v.getId()==R.id.btn_newPost){
			Intent intent=new Intent(this,EditPostActivity.class);
			startActivityForResult(intent, REQUEST_CODE_EDITPOST);
		}		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUEST_CODE_EDITPOST){
			
		}
	}
	
}
