package org.xwiki.android.components.objecteditor;

import org.xwiki.android.resources.Property;

import android.app.Activity;
import android.os.Bundle;

import org.xwiki.android.resources.Object;

public class ObjectEditorActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(new ObjectEditor(this, testProperty()));
    }

    private Object testProperty()
    {
        Object obj = new Object();
        obj.setPageName("Page 1");

        Property prop1 = new Property();
        prop1.setName("category");
        prop1.setValue("Blog.Other");

        Property prop2 = new Property();
        prop2.setName("content");
        prop2.setValue("This is a perfect test");

        obj.getProperties().add(prop1);
        obj.getProperties().add(prop2);

        return obj;
    }
}
