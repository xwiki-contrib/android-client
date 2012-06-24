package org.xwiki.android.client.nav;

import org.xwiki.android.components.listnavigator.XWikiListNavigatorActivity;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.context.UserSession;
import org.xwiki.android.context.XWikiApplicationContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class XwikiNavigator extends Activity {
	 private String username;
	 private String password;
	 private  String url;
	 private static final int REQUEST_CODE_XWIKI_NAVIGATOR=10;
	 private static final int REQUEST_CODE_XWIKI_PAGEVIEWER=20;
	/**
	 * just start the ListNavigatorActivity in components module 
	 * Get username, pwd ,url from context.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		 super.onCreate(savedInstanceState);
		 XWikiApplicationContext ctx=(XWikiApplicationContext)getApplication();
		 UserSession session=ctx.getUserSession();
		 username=session.getUserName();
		 password=session.getPassword();
		 url=session.getRealm();
		 startXWikiNavigator();
	}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data){
		 if (requestCode == REQUEST_CODE_XWIKI_NAVIGATOR) {
	            if (resultCode == Activity.RESULT_OK) {
	                String wikiName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_WIKI_NAME);
	                String  spaceName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_SPACE_NAME);
	                String pageName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_PAGE_NAME);
	                Intent intent = new Intent(this, PageViewActivity.class);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_USERNAME, username);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_PASSWORD, password);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_URL, url);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
	                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);
	                startActivityForResult(intent, REQUEST_CODE_XWIKI_PAGEVIEWER);
	            }
	        } else if (requestCode == REQUEST_CODE_XWIKI_PAGEVIEWER) {
	            startXWikiNavigator();
	        } 
	 }
	 
	 
	 private void startXWikiNavigator()
	 {
	        // Tree based XWiki Navigator
	        // Intent intent = new Intent(this, XWikiNavigatorActivity.class);
	        // intent.putExtra("username", username);
	        // intent.putExtra("password", password);
	        // intent.putExtra("url", url);
	        
	        // List based XWiki Navigator
	        Intent intent = new Intent(this, XWikiListNavigatorActivity.class);
	        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_USERNAME, username);
	        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
	        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_URL, url);
	        
	        Log.d(getClass().getSimpleName(),"start nav for:"+username+":"+password+":"+url);
	        startActivityForResult(intent, REQUEST_CODE_XWIKI_NAVIGATOR);
	    }
}
