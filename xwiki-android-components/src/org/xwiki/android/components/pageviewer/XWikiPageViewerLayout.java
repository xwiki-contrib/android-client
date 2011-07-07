package org.xwiki.android.components.pageviewer;

import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Object;
import org.xwiki.android.rest.Requests;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                addView(textViewName);
                
                TextView textViewValue = new TextView(context);
                textViewValue.setText(object.properties.get(j).getValue());
                textViewValue.setTextSize(textViewValue.getTextSize() + 0.5f);
                addView(textViewValue);
            }

        }

    }
}
