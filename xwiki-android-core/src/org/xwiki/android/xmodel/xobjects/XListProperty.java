package org.xwiki.android.xmodel.xobjects;

import java.util.List;

/**
 * @author xwiki gsoc 2012 attributes: special:  general:
 *         separators,displayType,sort,size,cache,multiSelect,relationalStorage,picker,separator attributes inherited
 *         from {@link XPropertyBase} spcial: name, type general: prettyName, number, tooltip, customDisplay, disabled,
 *         unmodifiable,validationMessage, validationRegExp
 */
public abstract class XListProperty<E> extends XPropertyBase<List<E>>
{

    public XListProperty(String type)
    {
        super(type);
    }
    
    public String getSeparators()
    {
        String separators = (String) fields.get("separators");
        if (separators == null || separators.equals("")) {
            separators = "|,";
        }
        return separators;
    }

    public String setSeparators(String separators)
    {
        return (String) fields.put("separators", separators);
    }

    public String getDisplayType()
    {
        return (String) fields.get("displayType");
    }

    public String setDisplayType(String type)
    {
        return (String) fields.put("displayType", type);
    }

    public String getSort()
    {
        return (String) fields.get("sort");
    }

    public String setSort(String sort)
    {
        return (String) fields.put("sort", sort);
    }

    public boolean isCache()
    {
        return (Boolean) fields.get("cache");
    }

    public boolean setCache(boolean cache)
    {
        return (Boolean) fields.put("cache", cache);
    }

    public boolean isMultiSelect()
    {
        return (Boolean) fields.get("multiSelect");
    }

    public void setMultiSelect(boolean multiSelect)
    {
        fields.put("multiSelect", multiSelect);
    }

    public boolean isRelationalStorage()
    {
        return (Boolean) fields.get("relationalStorage");
    }

    public void setRelationalStorage(boolean storage)
    {
        fields.put("relationalStorage", storage);
    }

    public boolean isPicker()
    {
        return (Boolean) fields.get("picker");
    }

    public void setPicker(boolean picker)
    {
        fields.put("picker", picker);
    }

    public String getSeparator()
    {
        return (String) fields.get("separator");
    }

    public void setSeparator(String separator)
    {
        fields.put("separator", separator);
    }

    protected int getSize()
    {
        return (Integer) fields.get("size");
    }

    protected int setSize(int size)
    {
        return (Integer) fields.put("size", size);
    }
    
    @Override
    public void setAttribute(String name, Object val) throws IllegalArgumentException
    {       
        super.setAttribute(name, val);
        //if type convertion needed , convert and put again. Check for this class's atr only. super.setAtt.. will convert known atr types by base class.
        boolean overridePut=false;
        
        if(name.equals("cache")){
            overridePut=true;
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'cache' attribute to boolean");
                }  
            }
        }else if(name.equals("multiSelect")){
            overridePut=true;
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'multiSelect' attribute to boolean");
                }  
            }
        }else if(name.equals("relationalStorage")){
            overridePut=true;
            if(!(val instanceof Boolean)){
                try{
                    val=Boolean.parseBoolean((String)val);
                }catch(ClassCastException e){
                    throw new IllegalArgumentException("cannot convert 'relationalStorage' attribute to boolean");
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
        }else if(name.equals("size")){
            overridePut=true;
            if(!(val instanceof Integer)){
              //now it should be string. Other wise class  cast exception.                
              try{
                  val=Integer.parseInt((String)val);
              }catch(ClassCastException e){
                  throw new IllegalArgumentException("cannot convert 'size' attribute to int");
              }
            }           
       }        
        
        if(overridePut==true){
            fields.put(name, val);
        }
    }

}
