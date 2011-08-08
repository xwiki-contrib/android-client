package org.xwiki.android.components.pageviewer;

import org.xwiki.android.components.IntentExtra;

import android.app.Activity;
import android.os.Bundle;

public class XWikiPageViewerActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            data = new String[6];
            data[4] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            data[5] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
        } else {
            data = new String[4];
        }

        data[0] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        data[1] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        data[2] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
        data[3] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);

        setContentView(new XWikiPageViewerLayout(this, data));

    }

}
