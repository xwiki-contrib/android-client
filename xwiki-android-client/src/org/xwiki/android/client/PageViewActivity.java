package org.xwiki.android.client;

import org.xwiki.android.components.objectnavigator.ObjectNavigatorActivity;
import org.xwiki.android.components.pageviewer.XWikiPageViewerActivity;
import org.xwiki.android.resources.Wiki;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PageViewActivity extends TabActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pageview);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabSpec TabSpecView = tabHost.newTabSpec("view");
        TabSpec TabSpecObjects = tabHost.newTabSpec("objects");
        // TabSpec TabSpecClasses = tabHost.newTabSpec("tid1");

        TabSpecView.setIndicator("View").setContent(setupPageViewerIntent());
        TabSpecObjects.setIndicator("Objects").setContent(setupObjectsViewerIntent());
        // TabSpecObjects.setIndicator("Classes").setContent(new Intent(this,XWikiPageViewerActivity.class));

        tabHost.addTab(TabSpecView);
        tabHost.addTab(TabSpecObjects);
        // tabHost.addTab(TabSpecClasses);
    }

    private Intent setupObjectsViewerIntent()
    {
        Intent intent = new Intent(this, ObjectNavigatorActivity.class);

        intent.putExtra("username", getIntent().getExtras().getString("username"));
        intent.putExtra("password", getIntent().getExtras().getString("password"));
        intent.putExtra("url", getIntent().getExtras().getString("url"));
        intent.putExtra("wikiname", getIntent().getExtras().getString("wikiname"));
        intent.putExtra("spacename", getIntent().getExtras().getString("spacename"));
        intent.putExtra("pagename", getIntent().getExtras().getString("pagename"));

        return intent;
    }

    private Intent setupPageViewerIntent()
    {
        Intent intent = new Intent(this, XWikiPageViewerActivity.class);

        intent.putExtra("username", getIntent().getExtras().getString("username"));
        intent.putExtra("password", getIntent().getExtras().getString("password"));
        intent.putExtra("url", getIntent().getExtras().getString("url"));
        intent.putExtra("wikiname", getIntent().getExtras().getString("wikiname"));
        intent.putExtra("spacename", getIntent().getExtras().getString("spacename"));
        intent.putExtra("pagename", getIntent().getExtras().getString("pagename"));

        return intent;
    }

}
