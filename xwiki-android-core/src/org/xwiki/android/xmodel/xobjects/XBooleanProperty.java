package org.xwiki.android.xmodel.xobjects;

import java.util.Date;

/**
 * @author xwiki attribute implimentation: special: value general: displayFormType, displayType, defaultValue
 */
public class XBooleanProperty extends XPropertyBase<Boolean>
{

    Boolean value;

    public XBooleanProperty()
    {
        type = "com.xpn.xwiki.objects.classes.BooleanClass";
    }

    @Override
    public String toString()
    {
        if (value) {
            return "1";
        } else {
            return "0";
        }
    }

    public void setValue(Boolean val)
    {
        value = val;

    }

    public Boolean getValue()
    {
        return new Boolean(value);
    }

    public Integer setSize(int size)
    {
        return (Integer) fields.put("size", size);
    }

    public Boolean setPicker(boolean picker)
    {
        return (Boolean) fields.put("picker", picker);
    }

    public Integer getSize()
    {
        return (Integer) fields.get("size");
    }

    public Boolean isPicker()
    {
        return (Boolean) fields.get("picker");
    }

}
