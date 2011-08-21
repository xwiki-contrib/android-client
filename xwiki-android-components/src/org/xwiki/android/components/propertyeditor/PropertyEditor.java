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

package org.xwiki.android.components.propertyeditor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xwiki.android.resources.Property;

/**
 * Layout which set by the Property Editor Activity
 */
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
