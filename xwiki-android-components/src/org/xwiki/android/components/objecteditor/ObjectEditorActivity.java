package org.xwiki.android.components.objecteditor;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.resources.Object;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ObjectEditorActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    public static final String INTENT_EXTRA_PUT_OBJECT_ID = IntentExtra.OBJECT_ID;

    public static final String INTENT_EXTRA_PUT_CLASS_NAME = IntentExtra.CLASS_NAME;

    private String url, wikiName, spaceName, pageName, username, password, className, objectID;

    private boolean isSecured;
    
    private Object object;
    
    private Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
        className = getIntent().getExtras().getString(INTENT_EXTRA_PUT_CLASS_NAME);
        objectID = getIntent().getExtras().getString(INTENT_EXTRA_PUT_OBJECT_ID);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        setContentView(new ObjectEditor(this, setupObject()));
    }

    private Object setupObject()
    {
        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        object = request.requestObject(wikiName, spaceName, pageName, className, objectID);

        return object;
    }
    
    @Override
    protected void onDestroy()
    {
        addObjectToXWiki();
        super.onDestroy();
    }
    
    private void addObjectToXWiki(){
        String s = request.addObject(wikiName, spaceName, pageName, object);
        Log.d("Object edit", s);
    }
}
