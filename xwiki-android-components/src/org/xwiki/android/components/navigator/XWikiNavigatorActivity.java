package org.xwiki.android.components.navigator;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class XWikiNavigatorActivity extends ExpandableListActivity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_GET_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_GET_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_GET_PAGE_NAME = IntentExtra.PAGE_NAME;

    private XWikiExpandListAdapter xwikiExpandListAdapter;

    private Handler handler;

    private String wikiName, spaceName, pageName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.xwikinavigator);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null) {
            xwikiExpandListAdapter =
                new XWikiExpandListAdapter(this, getExpandableListView(), getIntent().getExtras().getString(
                    INTENT_EXTRA_PUT_URL), getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME), getIntent()
                    .getExtras().getString(INTENT_EXTRA_PUT_PASSWORD));
        }

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);

                if (msg.arg1 == 0) {
                    if (xwikiExpandListAdapter.getIsSelected()) {
                        wikiName = xwikiExpandListAdapter.wikiName;
                        spaceName = xwikiExpandListAdapter.spaceName;
                        pageName = xwikiExpandListAdapter.pageName;

                        getIntent().putExtra(INTENT_EXTRA_GET_WIKI_NAME, wikiName);
                        getIntent().putExtra(INTENT_EXTRA_GET_SPACE_NAME, spaceName);
                        getIntent().putExtra(INTENT_EXTRA_GET_PAGE_NAME, pageName);
                        setResult(Activity.RESULT_OK, getIntent());
                        finish();
                    }
                }
            }

        };

        xwikiExpandListAdapter.setHandler(handler);

        setListAdapter(xwikiExpandListAdapter);
    }

}
