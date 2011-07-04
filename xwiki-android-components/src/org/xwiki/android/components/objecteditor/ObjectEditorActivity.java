package org.xwiki.android.components.objecteditor;

import org.xwiki.android.resources.Property;

import android.app.Activity;
import android.os.Bundle;

import org.xwiki.android.resources.Object;
import org.xwiki.android.rest.Requests;

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
        Requests request = new Requests("10.0.2.2:8080");
        Object o = request.requestObject("xwiki", "Blog", "test", "Blog.BlogPostClass", "0");
        
        return o;
    }
}
