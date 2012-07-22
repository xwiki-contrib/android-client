package org.xwiki.android.xmodel.xobjects;

/**
 * @author xwiki gsoc 2012 
 * Attributes inherited by {@link XPropertyBase} 
 * spcial: name, type 
 * general:prettyName, number, tooltip, customDisplay, disabled, unmodifiable,validationMessage, validationRegExp,
 * 
 * Attributes
 * general:size
 * 
 * 
 * @param <T> number type: Integer, Long, Float, Double
 */
public abstract class XNumberProperty<T> extends XPropertyBase<T>
{
    public XNumberProperty()
    {
       super("com.xpn.xwiki.objects.classes.NumberClass");
    }
    
    public Integer setSize(int size)
    {
       return(Integer) fields.put("size", size);
    }    
    public int getSize()
    {
        return (Integer) fields.get("size");
    }
    
   //gen attr get/set
    
    
    public String getNumberType()
    {
        return (String) fields.get("numberType");
    }

    public String setNumberType(String numberType)
    {
        return (String) fields.put("numberType","integer");
    }
}
