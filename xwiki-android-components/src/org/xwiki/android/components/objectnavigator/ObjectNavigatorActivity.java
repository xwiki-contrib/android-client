package org.xwiki.android.components.objectnavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.components.objecteditor.ObjectEditorActivity;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.rest.Requests;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class ObjectNavigatorActivity extends ExpandableListActivity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    private Objects objects;

    private String username, password, url, wikiName, spaceName, pageName;

    private boolean isSecured;

    ArrayList<String> allClassNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.objectnavigator);

        createObjects();

        SimpleExpandableListAdapter expListAdapter =
            new SimpleExpandableListAdapter(this, createGroupList(), R.layout.group_row, new String[] {"className"},
                new int[] {R.id.groupname}, createChildList(), R.layout.objectnavigator_child_row,
                new String[] {"objectName"}, new int[] {R.id.childname});
        setListAdapter(expListAdapter);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {

        // return super.onChildClick(parent, v, groupPosition, childPosition, id);
        
        String selectedClassname = allClassNames.get(groupPosition);
        String selectedObjectNo = String.valueOf(childPosition);
        Log.d("selection", "classname=" + selectedClassname + " object no=" + selectedObjectNo );
        
        Intent objectEditorIntent = new Intent(getApplicationContext(), ObjectEditorActivity.class);
        
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_URL, url);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_USERNAME, username);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_CLASS_NAME, selectedClassname);
        objectEditorIntent.putExtra(ObjectEditorActivity.INTENT_EXTRA_PUT_OBJECT_ID, selectedObjectNo);
        
        startActivity(objectEditorIntent);
        return true;
    }

    private List createGroupList()
    {
        ArrayList result = new ArrayList();
        allClassNames = new ArrayList<String>();

        for (int i = 0; i < objects.objectSummaries.size(); i++) {
            String className = objects.objectSummaries.get(i).className;
            if (!allClassNames.contains(className)) {
                HashMap m = new HashMap();
                m.put("className", className);
                result.add(m);
                allClassNames.add(className);
            }
        }
        return (List) result;
    }

    private List createChildList()
    {
        ArrayList result = new ArrayList();

        for (int i = 0; i < allClassNames.size(); i++) {
            ArrayList secList = new ArrayList();
            for (int j = 0; j < objects.objectSummaries.size(); j++) {
                if (allClassNames.get(i).equals(objects.objectSummaries.get(j).className)) {
                    HashMap child = new HashMap();
                    child.put("objectName", objects.objectSummaries.get(j).headline);
                    secList.add(child);
                }

            }
            result.add(secList);
        }

        return result;
    }

    private void createObjects()
    {

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        Requests request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        Objects os = request.requestAllObjects(wikiName, spaceName, pageName);
        objects = os;
    }
}
