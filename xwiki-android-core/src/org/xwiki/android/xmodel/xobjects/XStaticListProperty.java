package org.xwiki.android.xmodel.xobjects;

import java.util.ArrayList;
import java.util.List;

public class XStaticListProperty<E> extends XListProperty<E>
{
    
    private List<E> value;
    
   
    public XStaticListProperty()
    {
        super("com.xpn.xwiki.objects.classes.StaticListClass");
        value=new ArrayList<E>();
    }

    @Override
    public void setValue(List<E> val)
    {
        value=val;
    }

  
    public List<E> getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    public void setValueFromString(String val)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
}
