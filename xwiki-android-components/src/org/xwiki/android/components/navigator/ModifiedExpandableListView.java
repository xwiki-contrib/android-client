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
import android.widget.ExpandableListView;

public class ModifiedExpandableListView extends ExpandableListView
{

    private static final int ROW_HEIGHT = 20;

    private static final String LOG_TAG = "ModifiedExpandableListView";

    private int rows;

    public ModifiedExpandableListView(Context context)
    {
        super(context);
    }

    public void setRows(int rows)
    {
        this.rows = rows;
        Log.d(LOG_TAG, "rows set: " + rows);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), rows * ROW_HEIGHT);
        Log.d(LOG_TAG, "onMeasure " + this + ": width: " + decodeMeasureSpec(widthMeasureSpec) + "; height: "
            + decodeMeasureSpec(heightMeasureSpec) + "; measuredHeight: " + getMeasuredHeight() + "; measuredWidth: "
            + getMeasuredWidth());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(LOG_TAG, "onLayout " + this + ": changed: " + changed + "; left: " + left + "; top: " + top + "; right: "
            + right + "; bottom: " + bottom);
    }

    private String decodeMeasureSpec(int measureSpec)
    {
        int mode = View.MeasureSpec.getMode(measureSpec);
        String modeString = "<> ";
        switch (mode) {
            case View.MeasureSpec.UNSPECIFIED:
                modeString = "UNSPECIFIED ";
                break;

            case View.MeasureSpec.EXACTLY:
                modeString = "EXACTLY ";
                break;

            case View.MeasureSpec.AT_MOST:
                modeString = "AT_MOST ";
                break;
        }
        return modeString + Integer.toString(View.MeasureSpec.getSize(measureSpec));
    }
    
    

}
