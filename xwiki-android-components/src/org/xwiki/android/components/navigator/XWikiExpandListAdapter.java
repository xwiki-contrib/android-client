/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.components.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xwiki.android.components.R;
import org.xwiki.android.resources.PageSummary;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Wikis;
import org.xwiki.android.rest.Requests;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

/**
 * Data Handler of the 3 level Expandable List component
 * */
public class XWikiExpandListAdapter extends BaseExpandableListAdapter
{

    private Context context;

    private LayoutInflater inflater;

    private ExpandableListView topExpList;

    private ModifiedExpandableListView listViewCache[];

    private static final String KEY_COLORNAME = "colorName";

    private static final String KEY_SHADENAME = "shadeName";

    private static final String KEY_RGB = "rgb";

    private static final String LOG_TAG = "ColorExpListAdapter";

    private String wikiURL;

    private String username;

    private String password;

    private boolean isAuthenticated;

    private Wikis wikis;

    private boolean isSelected;

    public String wikiName, spaceName, pageName;

    public Handler handler;

    // For reducing requests
    private Spaces temp_spaces;

    private Pages temp_pages;

    private HashMap<String, Pages> cachePages;

    private String recentWikiName = "", recentSpaceName = "";

    public XWikiExpandListAdapter(Context context, ExpandableListView topExpList, String wikiURL)
    {
        cachePages = new HashMap<String, Pages>();
        isAuthenticated = false;
        isSelected = false;
        this.wikiURL = wikiURL;
        this.context = context;
        this.topExpList = topExpList;
        inflater = LayoutInflater.from(context);
        wikis = getWikiList();
        listViewCache = new ModifiedExpandableListView[wikis.wikis.size()];
    }

    public XWikiExpandListAdapter(Context context, ExpandableListView topExpList, String wikiURL, String username,
        String password)
    {
        cachePages = new HashMap<String, Pages>();
        this.wikiURL = wikiURL;
        this.username = username;
        this.password = password;
        isAuthenticated = true;
        isSelected = false;
        this.context = context;
        this.topExpList = topExpList;
        inflater = LayoutInflater.from(context);
        wikis = getWikiList();
        listViewCache = new ModifiedExpandableListView[wikis.wikis.size()];
    }

    public void setHandler(Handler hand)
    {
        handler = hand;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public Object getChild(int groupPosition, int childPosition)
    {
        String wikiname = wikis.getWikis().get(groupPosition).getName();
        String spacename = getSpacesList(wikiname).getSpaces().get(childPosition).getName();

        return getPagesList(wikiname, spacename);
    }

    public long getChildId(int groupPosition, int childPosition)
    {
        return (long) (groupPosition * 1024 + childPosition); // Max 1024 children per group
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
        ViewGroup parent)
    {
        Log.d(LOG_TAG, "getChildView: groupPositon: " + groupPosition + "; childPosition: " + childPosition);
        View v = null;
        if (listViewCache[groupPosition] != null)
            v = listViewCache[groupPosition];
        else {
            ModifiedExpandableListView dev = new ModifiedExpandableListView(context);
            dev.setRows(calculateRowCount(groupPosition, null));
            // groupData describes the first-level entries
            dev.setTag(groupPosition);
            dev.setAdapter(new ModifiedSimpleExpandableListAdapter(context, createGroupList(groupPosition),
                R.layout.child3_row, // Layout for the first-level entries
                new String[] {KEY_COLORNAME}, // Key in the groupData maps to display
                new int[] {R.id.childname}, // Data under "colorName" key goes into this TextView
                createChildList(groupPosition), // childData describes second-level entries
                R.layout.child3_row, // Layout for second-level entries
                new String[] {KEY_SHADENAME}, // Keys in childData maps to display
                new int[] {R.id.childname} // Data under the keys above go into these TextViews
            ));
            dev.setOnGroupClickListener(new Level2GroupExpandListener(groupPosition));
            dev.setOnChildClickListener(new OnChildClickListener()
            {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                    long id)
                {

                    Log.d("child click", "child click: " + "group position:" + String.valueOf(groupPosition)
                        + " child position:" + String.valueOf(childPosition));

                    wikiName = wikis.wikis.get(Integer.parseInt(parent.getTag().toString())).getName();
                    spaceName = getGroupName((Integer) parent.getTag(), groupPosition);
                    pageName = getChildName((Integer) parent.getTag(), groupPosition, childPosition);
                    isSelected = true;

                    Message ms = new Message();
                    ms.arg1 = 0;
                    handler.sendMessage(ms);

                    return false;
                }
            });
            listViewCache[groupPosition] = dev;
            v = dev;
        }
        return v;
    }

    private String getGroupName(int wikiPosition, int groupPosition)
    {

        Spaces spaces = new Spaces();
        Requests requests = new Requests(wikiURL);
        if (isAuthenticated) {
            requests.setAuthentication(username, password);
        }
        spaces = requests.requestSpaces(wikis.wikis.get(wikiPosition).getName());

        return spaces.spaces.get(groupPosition).getName();
    }

    private String getChildName(int wikiPosition, int groupPosition, int childPosition)
    {

        String wikiname = wikis.getWikis().get(wikiPosition).getName();
        List<Space> spacesList = getSpacesList(wikiname).getSpaces();

        List<PageSummary> pagesList =
            getPagesList(wikiname, spacesList.get(groupPosition).getName()).getPageSummaries();

        return pagesList.get(childPosition).getName();

    }

    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }

    public Object getGroup(int groupPosition)
    {
        String wikiname = wikis.getWikis().get(groupPosition).getName();
        return wikiname;
    }

    public int getGroupCount()
    {
        return wikis.wikis.size();
    }

    public long getGroupId(int groupPosition)
    {
        return (long) (groupPosition * 1024); // To be consistent with getChildId
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Log.d(LOG_TAG, "getGroupView: groupPositon: " + groupPosition + "; isExpanded: " + isExpanded);
        View v = null;
        if (convertView != null)
            v = convertView;
        else
            v = inflater.inflate(R.layout.group_row, parent, false);
        String gt = (String) getGroup(groupPosition);
        TextView colorGroup = (TextView) v.findViewById(R.id.groupname);
        if (gt != null) {
            colorGroup.setText(gt);
            v.setTag(gt);
        }

        return v;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    public void onGroupCollapsed(int groupPosition)
    {
    }

    public void onGroupExpanded(int groupPosition)
    {
    }

    private Wikis getWikiList()
    {

        Wikis temp_wikis;
        Requests requests = new Requests(wikiURL);
        if (isAuthenticated) {
            requests.setAuthentication(username, password);
        }
        temp_wikis = requests.requestWikis();

        return temp_wikis;
    }

    private Spaces getSpacesList(String wikiName)
    {
        Log.d("request", "request spaces from:" + wikiName);

        if (recentWikiName.equals(wikiName)) {
            Log.d("optimize", "optimized spaces");
            return temp_spaces;
        } else {
            Log.d("Loading", "pages loading started");
            Requests requests = new Requests(wikiURL);
            if (isAuthenticated) {
                requests.setAuthentication(username, password);
            }
            temp_spaces = requests.requestSpaces(wikiName);
            recentWikiName = wikiName;
            Log.d("Loading", "pages loading stopped");
            return temp_spaces;
        }

    }

    private Pages getPagesList(String wikiName, String spaceName)
    {
        String key = wikiName + "," + spaceName;

        Log.d("request", "request pages from:" + wikiName + "," + spaceName);

        if (recentWikiName.equals(wikiName) && recentSpaceName.equals(spaceName)) {
            Log.d("optimize", "optimized pages");
            return temp_pages;
        } else {

            if (cachePages.containsKey(key)) {
                Log.d("optimize", "optimized level 2 pages");
                return cachePages.get(key);
            } else {
                Log.d("Loading", "pages loading started");
                Requests requests = new Requests(wikiURL);
                if (isAuthenticated) {
                    requests.setAuthentication(username, password);
                }

                temp_pages = requests.requestAllPages(wikiName, spaceName);
                recentWikiName = wikiName;
                recentSpaceName = spaceName;
                cachePages.put(key, temp_pages);
                Log.d("Loading", "pages loading started");
                return temp_pages;
            }

        }
    }

    /**
     * Creates a level2 group list out of the listdesc array according to the structure required by
     * SimpleExpandableListAdapter. The resulting List contains Maps. Each Map contains one entry with key "colorName"
     * and value of an entry in the listdesc array.
     * 
     * @param level1 Index of the level1 group whose level2 subgroups are listed.
     */
    private List createGroupList(int level1)
    {
        Log.d("Group", "Group list created");
        ArrayList result = new ArrayList();

        result = createGroupLlistFromXwiki(wikis.wikis.get(level1).getName());
        // for (int i = 0; i < listdesc[level1].length; ++i) {
        // HashMap m = new HashMap();
        // //m.put(KEY_COLORNAME, listdesc[level1][i][0][1]);
        // result.add(m);
        // }
        return (List) result;
    }

    private ArrayList createGroupLlistFromXwiki(String wikiName)
    {
        Log.d("data", "URL=" + wikiURL + " wiki=" + wikiName);
        ArrayList result = new ArrayList();
        Spaces spaces = new Spaces();
        Requests requests = new Requests(wikiURL);
        if (isAuthenticated) {
            requests.setAuthentication(username, password);
        }
        spaces = requests.requestSpaces(wikiName);

        for (int i = 0; i < spaces.getSpaces().size(); ++i) {
            HashMap m = new HashMap();
            m.put(KEY_COLORNAME, spaces.getSpaces().get(i).getId());
            result.add(m);
        }

        return result;
    }

    /**
     * Creates the child list out of the listdesc array according to the structure required by
     * SimpleExpandableListAdapter. The resulting List contains one list for each group. Each such second-level group
     * contains Maps. Each such Map contains two keys: "shadeName" is the name of the shade and "rgb" is the RGB value
     * for the shade.
     * 
     * @param level1 Index of the level1 group whose level2 subgroups are included in the child list.
     */
    private List createChildList(int wikiPosition)
    {
        Log.d("Child group", "Child group created");
        ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();

        String wikiname = wikis.getWikis().get(wikiPosition).getName();

        List<Space> spacesList = getSpacesList(wikiname).getSpaces();

        for (int i = 0; i < spacesList.size(); i++) {
            List<PageSummary> pagesList = getPagesList(wikiname, spacesList.get(i).getName()).getPageSummaries();
            ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();

            for (int j = 0; j < pagesList.size(); j++) {
                HashMap<String, String> child = new HashMap<String, String>();
                child.put(KEY_SHADENAME, pagesList.get(j).getName());
                child.put(KEY_RGB, pagesList.get(j).getId());
                secList.add(child);
            }
            result.add(secList);
        }
        return result;
    }

    // Calculates the row count for a level1 expandable list adapter. Each level2 group counts 1 row (group row) plus
    // any child row that
    // belongs to the group Modified by Chamika
    private int calculateRowCount(int wikiPosition, ExpandableListView level2view)
    {
        int rowCtr = 0;
        int level2GroupCount = 0;

        String wikiname = wikis.getWikis().get(wikiPosition).getName();
        List<Space> spacesList = getSpacesList(wikiname).getSpaces();

        for (int i = 0; i < spacesList.size(); i++) {
            List<PageSummary> pagesList = getPagesList(wikiname, spacesList.get(i).getName()).getPageSummaries();

            level2GroupCount += pagesList.size();

        }

        for (int i = 0; i < level2GroupCount; ++i) {
            ++rowCtr; // for the group row
            if ((level2view != null) && (level2view.isGroupExpanded(i))) {
                List<PageSummary> pagesList = getPagesList(wikiname, spacesList.get(i).getName()).getPageSummaries();
                rowCtr += pagesList.size();
            }
        }
        return rowCtr;
    }

    class Level2GroupExpandListener implements ExpandableListView.OnGroupClickListener
    {
        public Level2GroupExpandListener(int level1GroupPosition)
        {
            this.level1GroupPosition = level1GroupPosition;
        }

        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
        {
            if (parent.isGroupExpanded(groupPosition))
                parent.collapseGroup(groupPosition);
            else
                parent.expandGroup(groupPosition);
            if (parent instanceof ModifiedExpandableListView) {
                ModifiedExpandableListView dev = (ModifiedExpandableListView) parent;
                dev.setRows(calculateRowCount(level1GroupPosition, parent));
            }
            Log.d(LOG_TAG, "onGroupClick");
            topExpList.requestLayout();
            return true;
        }

        private int level1GroupPosition;
    }

}
