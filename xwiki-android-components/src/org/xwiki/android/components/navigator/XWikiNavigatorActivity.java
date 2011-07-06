package org.xwiki.android.components.navigator;

import org.xwiki.android.components.R;

import android.app.ExpandableListActivity;
import android.os.Bundle;

public class XWikiNavigatorActivity extends ExpandableListActivity
{
    private XWikiExpandListAdapter xwikiExpandListAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.xwikinavigator);
        // xwikiExpandListAdapter =
        // new XWikiExpandListAdapter(this, getExpandableListView(),"www.xwiki.org");

        if (getIntent().getExtras().getString("username") != null
            && getIntent().getExtras().getString("password") != null) {
            xwikiExpandListAdapter =
                new XWikiExpandListAdapter(this, getExpandableListView(), getIntent().getExtras().getString("url"),
                    getIntent().getExtras().getString("username"), getIntent().getExtras().getString("password"));
        }

        setListAdapter(xwikiExpandListAdapter);

    }

}
