package org.xwiki.android.components.objecteditor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xwiki.android.resources.Object;

public class ObjectEditor extends LinearLayout
{
    Context context;
    Object object;
    private int tag;

    public ObjectEditor(Context context, AttributeSet attrs, Object object)
    {
        super(context, attrs);
        this.context=context;
        this.object = object;
        initObjectEditor();
    }

    public ObjectEditor(Context context,Object object)
    {
        super(context);
        this.context=context;
        this.object = object;
        initObjectEditor();
    }

    private void initObjectEditor()
    {
        setOrientation(LinearLayout.VERTICAL);

        
        for(int i=0;i<object.properties.size();i++){
            
            tag=i;
            
            TextView textViewName = new TextView(context);
            textViewName.setText(object.properties.get(i).getName());
            textViewName.setTextSize(textViewName.getTextSize() + 2.0f);
            addView(textViewName);
            
            EditText editTextValue = new EditText(context);
            editTextValue.setText(object.properties.get(i).getValue());
            editTextValue.addTextChangedListener(new TextWatcher(){
                
                public void afterTextChanged(Editable s) {
                    object.properties.get(tag).setValue(s.toString());
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                public void onTextChanged(CharSequence s, int start, int before, int count){}
            });
            addView(editTextValue);
        }
        

    }

}
