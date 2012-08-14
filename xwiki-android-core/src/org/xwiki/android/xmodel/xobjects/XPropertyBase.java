package org.xwiki.android.xmodel.xobjects;

import java.util.Map;

import org.xwiki.android.resources.Link;
import org.xwiki.android.resources.LinkCollection;

/**
 * @author sasinda
 * @version 1.0
 * @param <T> :Type of the special attribute "value". implemented attributes Currently attributes does not support their
 *            {@link Link} elements in {@link LinkCollection}.
 *            spcial: name, type
 *            general:prettyName, number, tooltip,
 *            customDisplay, disabled, unmodifiable,validationMessage, validationRegExp,
 */
// TODO: Note: implement seperate map to hold Link(s) when needed. (for performance reasons)
public abstract class XPropertyBase<T> extends XObject<Object> implements XProperty<T>
{
    // super.fields is the map holding all attributes. (except type)
    protected String name;// stored here additionally as a first class attribute.

    protected String type;

    //
    // special fields
    //
    
    public XPropertyBase(String type)
    {
       this.type=type;
    }

    public void setName(String name)
    {
        this.name = name;
        setAttribute("name", name);
    }
    

    public String getName()
    {
        return name;
    }
    /**
     * @return type of the property.
     */
    public String getType()
    {     
        return null;
    }

    //
    // general attributes
    //

    // set methods

    public String setPrettyName(String prettyName)
    {
        return (String) fields.put("prettyName", prettyName);
    }

    public Integer setNumber(int number)
    {
        return (Integer) fields.put("number", number);
    }

    public String setTooltip(String tooltip)
    {
        return (String) fields.put("tooltip", tooltip);
    }

    public String setCustomDisplay(String value)
    {
        return (String) fields.put("customDisplay", value);
    }

    public Boolean setDisabled(boolean disabled)
    {
        return (Boolean) fields.put("disabled", disabled);

    }

    public Boolean setUnmodifiable(boolean unmodifialbe)
    {
        return (Boolean) fields.put("unmodifiable", unmodifialbe);
    }

    public String setValidationMessage(String validationMessage)
    {
        return (String) fields.put("validationMessage", validationMessage);
    }

    public String setValidationRegExp(String regex)
    {
        return (String) fields.put("validationRegExp", regex);
    }

    // get methods

    public String getPrettyName()
    {
        return (String) fields.get("prettyName");
    }

    public Integer getNumber()
    {
       try{
           return (Integer) fields.get("number");
       }catch(ClassCastException e){
           String val=(String)fields.get("number");
           return Integer.parseInt(val);
       }
    }

    public String getTooltip()
    {
        return (String) fields.get("tooltip");
    }

    public String getCustomDisplay()
    {
        return (String) fields.get("customDisplay");
    }

    public Boolean isDisabled()
    {
        return (Boolean) fields.get("disabled");
    }

    public Boolean isUnmodifiable()
    {
        return (Boolean) fields.get("unmodifiable");
    }

    public String getValidationMessage()
    {
        return (String) fields.get("validationMessage");
    }

    public String getValidationRegExp()
    {
        return (String) fields.get("validationRegExp");
    }

    
    @Override
    public void setAttribute(String name, Object val) throws IllegalArgumentException
    {                
    	if(val==null){
        	throw new IllegalArgumentException("cannot set null attribute");
        }
    	
    	if(name.equals("number")){
            if(!(val instanceof Integer)){
              //now it should be string. Other wise class  cast exception.                
              try{
                  val=Integer.parseInt((String)val);
              }catch(ClassCastException e){
                  throw new IllegalArgumentException("cannot convert 'number' attribute to int");
              }
            }           
        }else if(name.equals("disabled")){
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'disabled' attribute to boolean");
                }  
            }
        }else if(name.equals("unmodifiable")){
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'unmodifiable' attribute to boolean");
                }  
            }
        }        
        fields.put(name, val);                
    }
    

    @Override
    /**
     * @return attrValue
     */
    public Object getAttribute(String attrName)
    {
        return fields.get(attrName);
    }

    @Override
    public Map<String, Object> getAllAttributes()
    {
        return fields;
    }
}
