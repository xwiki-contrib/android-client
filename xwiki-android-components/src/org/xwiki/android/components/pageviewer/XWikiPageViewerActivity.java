package org.xwiki.android.components.pageviewer;

import android.app.Activity;
import android.os.Bundle;

public class XWikiPageViewerActivity extends Activity
{
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras().getString("password") != null
            && getIntent().getExtras().getString("username") != null) {
            data = new String[6];
            data[4] = getIntent().getExtras().getString("username");
            data[5] = getIntent().getExtras().getString("password");
        } else {
            data = new String[4];
        }

        data[0] = getIntent().getExtras().getString("wikiname");
        data[1] = getIntent().getExtras().getString("spacename");
        data[2] = getIntent().getExtras().getString("pagename");
        data[3] = getIntent().getExtras().getString("url");

        setContentView(new XWikiPageViewerLayout(this, data));

    }

}
