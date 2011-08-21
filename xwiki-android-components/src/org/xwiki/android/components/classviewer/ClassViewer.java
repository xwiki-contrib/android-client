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

package org.xwiki.android.components.classviewer;

import org.xwiki.android.resources.Class;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Layout of the class viewing user interface
 * */
public class ClassViewer extends LinearLayout
{
    Context context;

    Class classObject;

    public ClassViewer(Context context, AttributeSet attrs, Class classobj)
    {
        super(context, attrs);
        this.context = context;
        this.classObject = classobj;
        initClassEditor();
    }

    public ClassViewer(Context context, Class classobj)
    {
        super(context);
        this.context = context;
        this.classObject = classobj;
        initClassEditor();
    }

    private void initClassEditor()
    {
        setOrientation(LinearLayout.VERTICAL);
        setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);

        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);

        ScrollView scrollView = new ScrollView(context);

        for (int i = 0; i < classObject.properties.size(); i++) {

            TextView textViewName = new TextView(context);
            textViewName.setText(classObject.properties.get(i).getName());
            textViewName.setTextSize(textViewName.getTextSize() + 2.0f);
            textViewName.setTextColor(Color.WHITE);
            innerLayout.addView(textViewName);

            TextView textViewValue = new TextView(context);
            textViewValue.setText(classObject.properties.get(i).getType());
            innerLayout.addView(textViewValue);
        }

        scrollView.addView(innerLayout);
        addView(scrollView);

    }

}
