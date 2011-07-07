package org.xwiki.android.components.objectnavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.xwiki.android.components.R;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.widget.SimpleExpandableListAdapter;

public class ObjectNavigatorActivity extends ExpandableListActivity
{
    Objects objects;
    
    String username, password, url, wikiName, spaceName, pageName;

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
        String username, password, url;

        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        url = getIntent().getExtras().getString("url");

        wikiName = getIntent().getExtras().getString("wikiname");
        spaceName = getIntent().getExtras().getString("spacename");
        pageName = getIntent().getExtras().getString("pagename");
      
        Requests request = new Requests(url);
        request.setAuthentication(username, password);
        Objects os = request.requestAllObjects(wikiName, spaceName, pageName);
        objects = os;
    }
}
