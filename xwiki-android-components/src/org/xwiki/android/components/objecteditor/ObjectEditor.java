package org.xwiki.android.components.objecteditor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.xwiki.android.resources.Object;

public class ObjectEditor extends LinearLayout
{
    Context context;

    Object object;

    private Integer tag;

    public ObjectEditor(Context context, AttributeSet attrs, Object object)
    {
        super(context, attrs);
        this.context = context;
        this.object = object;
        initObjectEditor();
    }

    public ObjectEditor(Context context, Object object)
    {
        super(context);
        this.context = context;
        this.object = object;
        initObjectEditor();
    }

    private void initObjectEditor()
    {
        setOrientation(LinearLayout.VERTICAL);
        setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);
        
        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setScrollBarStyle(LinearLayout.SCROLLBARS_INSIDE_OVERLAY);
        
        ScrollView scrollView = new ScrollView(context);
        

        for (int i = 0; i < object.properties.size(); i++) {

            tag = i;

            TextView textViewName = new TextView(context);
            textViewName.setText(object.properties.get(i).getName());
            textViewName.setTextSize(textViewName.getTextSize() + 2.0f);
            innerLayout.addView(textViewName);

            final EditText editTextValue = new EditText(context);
            editTextValue.setTag(tag);
            editTextValue.setText(object.properties.get(i).getValue());
            editTextValue.addTextChangedListener(new TextWatcher()
            {

                public void afterTextChanged(Editable s)
                {
                    int currentTag = (Integer)editTextValue.getTag();
                    object.properties.get(currentTag).setValue(s.toString());
                    Log.d("current Tag", String.valueOf(currentTag));
                    Log.d("value changed", object.properties.get(currentTag).getValue());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                }
            });
            innerLayout.addView(editTextValue);
        }
        
        scrollView.addView(innerLayout);
        addView(scrollView);
        
    }

}
