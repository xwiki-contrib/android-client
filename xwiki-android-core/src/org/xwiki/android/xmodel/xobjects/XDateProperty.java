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
        super("com.xpn.xwiki.objects.classes.DateClass");
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
    
    @Override
    public void setValueFromString(String val)
    {
        Date d=new Date(val);
        value=d;
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

    
    @Override
    public void setAttribute(String name, Object val) throws IllegalArgumentException
    {       
        super.setAttribute(name, val);
        //if type convertion needed , convert and put again. Check for this class's atr only. super.setAtt.. will convert known atr types by base class.
        boolean overridePut=false;
        
        if(name.equals("size")){
            overridePut=true;
            if(!(val instanceof Integer)){
              //now it should be string. Other wise class  cast exception.                
              try{
                  val=Integer.parseInt((String)val);
              }catch(ClassCastException e){
                  throw new IllegalArgumentException("cannot convert 'size' attribute to int");
              }
            }           
        }else if(name.equals("emptyIsToday")){
            overridePut=true;
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'emptyIsToday' attribute to boolean");
                }  
            }
        }else if(name.equals("picker")){
            overridePut=true;
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'picker' attribute to boolean");
                }  
            }
        }
        
        
        if(overridePut==true){
            fields.put(name, val);
        }
    }
    

}
