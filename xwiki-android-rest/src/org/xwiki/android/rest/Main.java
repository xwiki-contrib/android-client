package org.xwiki.android.rest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main extends Activity {

	// IP of the development machine in Android emulator 10.0.2.2
	// These variables are uses for testing
	String domain = "10.0.2.2";
	int port = 8080;
	String searchKeyword = "test";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		


	}
	
	public void searchButtonClick(View v){
		
		TextView tv_result = (TextView) findViewById(R.id.textView1);
		TextView et_domain = (TextView)findViewById(R.id.editText_domain);
		TextView et_port = (TextView)findViewById(R.id.editText_port);
		TextView et_searchText = (TextView)findViewById(R.id.editText_SearchText);
		
		domain = et_domain.getText().toString();
		port = Integer.parseInt(et_port.getText().toString());
		searchKeyword = et_searchText.getText().toString();
		
		Log.d("Info", "domain="+ domain +" port=" + port + " text=" + searchKeyword);
		Requests searchRequest = new Requests(domain, port);
		String result = searchRequest.requestSearch(searchKeyword);

		Log.d("Final result:","result=" + result);
		tv_result.setText("Search Results\n" + searchRequest.decodeSearchResponse(result));
	
	}
}