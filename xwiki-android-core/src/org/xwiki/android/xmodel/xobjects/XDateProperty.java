package org.xwiki.android.xmodel.xobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xwiki gsoc 2012 Attributes size,dateFormat,emptyIsToday,picker Inherited from {@link XPropertyBase} spcial:
 *         name, type general:prettyName, number, tooltip, customDisplay, disabled, unmodifiable,validationMessage,
 *         validationRegExp,
 */
public class XDateProperty extends XPropertyBase<Date>
{
    Date value;

    public XDateProperty()
    {
        type = "com.xpn.xwiki.objects.classes.StaticListClass";
    }

    public String toString()
    {
        String fmt = getDateFormat();
        if (fmt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.format(value);
        }
        return value.toGMTString();
    }

    @Override
    public void setValue(Date date)
    {
        value = date;
    }

    @Override
    public Date getValue()
    {
        return value;
    }

    public int getSize()
    {
        return (Integer) fields.get("size");
    }

    public int setSize(int size)
    {
        return (Integer) fields.put("size", size);
    }

    public String getDateFormat()
    {
        return (String) fields.get("dateFormat");
    }

    public String setDateFormat(String dateFormat)
    {
        return (String) fields.put("dateFormat", dateFormat);
    }

    public boolean isEmptyIsToday()
    {
        return (Boolean) fields.get("emptyIsToday");
    }

    public boolean setEmptyIsToday(boolean emptyIsToday)
    {
        return (Boolean) fields.put("emptyIsToday", emptyIsToday);
    }

    public boolean isPicker()
    {
        return (Boolean) fields.get("picker");
    }

    public boolean setPicker(boolean picker)
    {
        return (Boolean) fields.put("picker", picker);
    }

}
