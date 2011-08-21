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

package org.xwiki.android.components.pageviewer;

import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.rest.Requests;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Layout for the page viewing
 */
public class XWikiPageViewerLayout extends LinearLayout
{
    private Context context;

    private String wikiName, spaceName, pageName, username, password, url;

    private boolean isSecured;

    public XWikiPageViewerLayout(Context context, String[] data)
    {
        super(context);
        this.context = context;
        isSecured = false;
        initializeData(data);
        initPageView();
    }

    public XWikiPageViewerLayout(Context context, AttributeSet attrs, String[] data)
    {
        super(context, attrs);
        this.context = context;
        isSecured = false;
        initializeData(data);
        initPageView();
    }

    private void initializeData(String[] data)
    {
        if (data.length == 6) {
            username = data[4];
            password = data[5];
            isSecured = true;
        }

        wikiName = data[0];
        spaceName = data[1];
        pageName = data[2];
        url = data[3];
    }

    private void initPageView()
    {
        setOrientation(LinearLayout.VERTICAL);
        setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);

        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);

        ScrollView scrollView = new ScrollView(context);

        Requests request = new Requests(url);
        if (isSecured) {
            request.setAuthentication(username, password);
        }

        Objects objects = request.requestAllObjects(wikiName, spaceName, pageName);

        for (int i = 0; i < objects.getObjectSummaries().size(); i++) {
            Object object =
                request.requestObject(wikiName, spaceName, pageName, objects.objectSummaries.get(i).getClassName(),
                    String.valueOf(objects.objectSummaries.get(i).getNumber()));

            for (int j = 0; j < object.properties.size(); j++) {

                TextView textViewName = new TextView(context);
                textViewName.setText(object.properties.get(j).getName());
                textViewName.setTextColor(Color.WHITE);
                textViewName.setTextSize(textViewName.getTextSize() + 0.7f);

                innerLayout.addView(textViewName);

                TextView textViewValue = new TextView(context);
                textViewValue.setText(object.properties.get(j).getValue());
                textViewValue.setTextSize(textViewValue.getTextSize() + 0.5f);
                innerLayout.addView(textViewValue);
            }

        }

        scrollView.addView(innerLayout);
        addView(scrollView);
    }
}
