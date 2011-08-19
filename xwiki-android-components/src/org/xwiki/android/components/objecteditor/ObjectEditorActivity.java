package org.xwiki.android.components.objecteditor;

import java.util.ArrayList;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Property;
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

    private Object object, serverObject;

    private Requests request;

    private ObjectEditor objectEditor;

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

        object = setupObject();
        objectEditor = new ObjectEditor(this, object);

        setContentView(objectEditor);
    }

    private Object setupObject()
    {
        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        Object temp_object = request.requestObject(wikiName, spaceName, pageName, className, objectID);

        return temp_object;
    }

    @Override
    protected void onDestroy()
    {
        addObjectToXWiki();
        super.onDestroy();
    }

    private void addObjectToXWiki()
    {

        serverObject = setupObject();

        ArrayList<Property> propertyList = compareObjects(serverObject, object);

        
        for (int i = 0; i < propertyList.size(); i++) {
            String s = request.addProperty(wikiName, spaceName, pageName, className, objectID, propertyList.get(i));
            Log.d("Property result " + String.valueOf(i), s);
        }
    }

    private ArrayList<Property> compareObjects(Object oldObject, Object newObject)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for(int i=0; i<newObject.getProperties().size(); i++){
            
            if(newObject.getProperties().get(i).getValue() != null){
                if(!newObject.getProperties().get(i).getValue().equals(oldObject.getProperties().get(i).getValue())){
                    result.add(newObject.getProperties().get(i));
                }
            }
            
        }
        
        return result;
    }
}
