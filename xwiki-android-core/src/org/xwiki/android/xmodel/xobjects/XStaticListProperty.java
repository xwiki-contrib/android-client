package org.xwiki.android.xmodel.xobjects;

import java.util.List;

public class XStaticListProperty extends XListProperty
{
    public XStaticListProperty()
    {
        type = "com.xpn.xwiki.objects.classes.StaticListClass";
    }

    @Override
    public void setValue(List<String> val)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> getValue()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
