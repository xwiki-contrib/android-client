package org.xwiki.android.components.propertyeditor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xwiki.android.resources.Property;

public class PropertyEditor extends LinearLayout
{
    Context context;

    Property property;

    public PropertyEditor(Context context, Property property)
    {
        super(context);
        this.context = context;
        this.property = property;
        initPropertyView();
    }

    public PropertyEditor(Context context, AttributeSet attrs, Property property)
    {
        super(context, attrs);
        this.context = context;
        this.property = property;

        initPropertyView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initPropertyView()
    {
        // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflater.inflate(R.layout.xwikiproperty, this);

        setOrientation(LinearLayout.VERTICAL);

        TextView textViewName = new TextView(context);
        textViewName.setText(property.getName());
        textViewName.setTextSize(textViewName.getTextSize() + 5.0f);
        addView(textViewName);

        EditText editTextValue = new EditText(context);
        editTextValue.setText(property.getValue());
        editTextValue.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
                property.setValue(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
        });
        addView(editTextValue);

        TextView textViewType = new TextView(context);
        textViewType.setText("Type : " + property.getType());
        addView(textViewType);

        for (int i = 0; i < property.getAttributes().size(); i++) {
            TextView textViewAttribute = new TextView(context);
            textViewAttribute.setTag(i);

            textViewAttribute.setText(property.getAttributes().get(i).getName() + " : "
                + property.getAttributes().get(i).getValue());
            addView(textViewAttribute);
        }

    }

}
