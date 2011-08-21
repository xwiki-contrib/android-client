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
