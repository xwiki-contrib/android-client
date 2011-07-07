package org.xwiki.android.client;

import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.components.navigator.XWikiNavigatorActivity;
import org.xwiki.android.components.objectnavigator.ObjectNavigatorActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity
{

    private final int REQUEST_CODE_LOGINACTIVITY = 100;

    private final int REQUEST_CODE_XWIKI_NAVIGATOR = 101;

    private final int REQUEST_CODE_XWIKI_PAGEVIEWER = 102;

    private final int REQUEST_CODE_OBJECTNAVIGATOR = 110;

    private String username;

    private String password;

    private String url;

    private String wikiName;

    private String spaceName;

    private String pageName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //startActivity(new Intent(this, PageViewActivity.class));
         startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_CODE_LOGINACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGINACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Activity Result", "OK");
                username = data.getExtras().getString("username");
                password = data.getExtras().getString("password");
                url = data.getExtras().getString("url");
                Log.d("data", "username=" + username + " password=" + password + " url=" + url);

                startXWikiNavigator();
            } else {
                finish();
            }
        } else if (requestCode == REQUEST_CODE_XWIKI_NAVIGATOR) {
            if (resultCode == Activity.RESULT_OK) {
                wikiName = data.getExtras().getString("wikiname");
                spaceName = data.getExtras().getString("spacename");
                pageName = data.getExtras().getString("pagename");

                Intent intent = new Intent(this, PageViewActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("url", url);
                intent.putExtra("wikiname", wikiName);
                intent.putExtra("spacename", spaceName);
                intent.putExtra("pagename", pageName);

                startActivityForResult(intent, REQUEST_CODE_XWIKI_PAGEVIEWER);
            }
            else{
                startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_CODE_LOGINACTIVITY);
            }

        } else if (requestCode == REQUEST_CODE_XWIKI_PAGEVIEWER) {
            startXWikiNavigator();
        } else if (requestCode == REQUEST_CODE_OBJECTNAVIGATOR) {

        }
    }

    private void startXWikiNavigator()
    {
        Intent intent = new Intent(this, XWikiNavigatorActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("url", url);

        startActivityForResult(intent, REQUEST_CODE_XWIKI_NAVIGATOR);
    }
}
