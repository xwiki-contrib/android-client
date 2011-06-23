package org.xwiki.android.components.navigator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleExpandableListAdapter;
import java.util.List;
import java.util.Map;

public class ModifiedSimpleExpandableListAdapter extends SimpleExpandableListAdapter
{

    public ModifiedSimpleExpandableListAdapter(Context context, List< ? extends Map<String, ? >> groupData,
        int groupLayout, String[] groupFrom, int[] groupTo,
        List< ? extends List< ? extends Map<String, ? >>> childData, int childLayout, String[] childFrom, int[] childTo)
    {
        super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo);
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
        ViewGroup parent)
    {
        View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        Log.d(LOG_TAG, "getChildView: groupPosition: " + groupPosition + "; childPosition: " + childPosition + "; v: "
            + v);
        return v;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View v = super.getGroupView(groupPosition, isExpanded, convertView, parent);
        Log.d(LOG_TAG, "getGroupView: groupPosition: " + groupPosition + "; isExpanded: " + isExpanded + "; v: " + v);
        return v;
    }

    private static final String LOG_TAG = "DebugSimpleExpandableListAdapter";
}
