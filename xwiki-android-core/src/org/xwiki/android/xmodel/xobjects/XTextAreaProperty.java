package org.xwiki.android.xmodel.xobjects;

//TODO: change to value=StringBuilder if needed.The UI component will have a StringBuilder so no need maybe.

/**
 * @author sasinda attribute implimentation: special: value general: size,rows,picker,contenttype,editor
 */
public class XTextAreaProperty extends XPropertyBase<String>
{

    String value;

    public XTextAreaProperty()
    {
        super("com.xpn.xwiki.objects.classes.TextAreaClass");
    }

    @Override
    public String toString()
    {
        return value;
    }

    // special attr

    public void setValue(String val)
    {
        //TODO: change to StringBuffer, or a StringBuilder?
        value = val;
    }

    public String getValue()
    {
        return value;
    }
    
    @Override
    public void setValueFromString(String val)
    {
       value=val;        
    }
    //TODO: toString() if val type is changed

    // general attr
    public Integer setSize(int size)
    {
        return (Integer) fields.put("size", size);
    }

    public Integer setRows(int numrows)
    {
        return (Integer) fields.put("rows", numrows);
    }

    public Boolean setPicker(boolean picker)
    {
        return (Boolean) fields.put("picker", picker);
    }

    public String setContenttype(String cntype)
    {
        return (String) fields.put("contenttype", cntype);
    }

    public String setEditor(int numrows)
    {
        return (String) fields.put("editor", numrows);
    }

    // ------getters

    public Integer getSize()
    {
        return (Integer) fields.get("size");
    }

    public Integer getRows()
    {
        return (Integer) fields.get("rows");
    }

    public Boolean isPicker()
    {
        return (Boolean) fields.get("picker");
    }

    public String getContenttype()
    {
        return (String) fields.get("contenttype");
    }

    public String getEditor()
    {
        return (String) fields.get("editor");
    }

    

}
