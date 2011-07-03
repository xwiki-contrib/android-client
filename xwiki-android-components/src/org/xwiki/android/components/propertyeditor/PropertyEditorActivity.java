package org.xwiki.android.components.propertyeditor;

import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Property;

import android.app.Activity;
import android.os.Bundle;

public class PropertyEditorActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new PropertyEditor(this, testProperty()));
    }

    private Property testProperty()
    {
        Property prop = new Property();
        prop.setName("content");
        prop.setValue("This is a sample property value");
        prop.setType("com.xpn.xwiki.objects.classes.TextAreaClass");

        Attribute att = new Attribute();
        att.name = "contenttype";
        att.value = "FullyRenderedText";

        Attribute att1 = new Attribute();
        att1.name = "editor";
        att1.value = "---";

        prop.getAttributes().add(att);
        prop.getAttributes().add(att1);

        return prop;
    }

}
