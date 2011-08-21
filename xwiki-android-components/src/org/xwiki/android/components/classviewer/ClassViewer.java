package org.xwiki.android.components.classviewer;

import org.xwiki.android.resources.Class;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
