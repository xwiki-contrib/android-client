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
        super("com.xpn.xwiki.objects.classes.BooleanClass");
    }
    

    public XBooleanProperty(Boolean value)
    {
        this();
        this.value = value;
    }


    @Override
    public void setValue(Boolean val)
    {
        value = val;

    }

    @Override
    public Boolean getValue()
    {
        return new Boolean(value);
    }
    
    @Override
    public void setValueFromString(String val) throws IllegalArgumentException
    {
       value=XUtils.toBoolean(val);             
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
