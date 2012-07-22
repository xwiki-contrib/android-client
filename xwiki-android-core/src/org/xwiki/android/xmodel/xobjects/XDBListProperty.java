package org.xwiki.android.xmodel.xobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author xwiki gsoc 2012
 * @version 0.5
 */
// TODO:Currently the list of values are Strings(for performance.). The value list may be in need for Refactor to
// support other data types.

public class XDBListProperty extends XListProperty<String>
{
    private List<String> value;

    public XDBListProperty()
    {
        super("com.xpn.xwiki.objects.classes.DBListClass");
        init();
    }

    XDBListProperty(String type)
    {
        super(type);
        init();
    }

    private void init()
    {
        value = new ArrayList<String>()
        {// extending the value>ArrayList is redundant. We can use XDBListProperty.toString()
                @Override
                public String toString()
                {
                    String str = "";
                    String sep = getSeparator();
                    if (sep == null || sep.equals(""))
                        sep = "|";                        
                    Iterator<String> itr = this.iterator();
                    while (itr.hasNext()) {
                        str += sep + itr.next();
                    }
                    return str.substring(1);
                }
            };
    }

    /**
     * @return , seperated list of allowed values in this DBList
     */
    public String getAllowedValues()
    {
        return (String) fields.get("allowedValues");
    }

    /**
     * @param allowedValues "," separated list of allowed values.
     * @return earlier value of this param.
     */
    public String setAllowedValues(String allowedValues)
    {
        return (String) fields.put("allowedValues", allowedValues);
    }
    
    
   

    @Override
    public void setValueFromString(String val)
    {
       String sep= getSeparator();
//       if(sep==null){
//           sep=", ;:/";
//       }
       this.value=XUtils.toStringList(val, sep);       
    }
    
    public String toString()
    {
        String str = "";
        String sep = getSeparator();
        if (sep == null || sep.equals(""))
            sep = "|";
        for (String item : value) {
            str += sep + value.toString();
        }
        return str.substring(1);
    }

    // delegate methods.
    public void add(int location, String item)
    {
        value.add(location, item);
    }

    public boolean add(String item)
    {
        return value.add(item);
    }

    public boolean isEmpty()
    {
        return value.isEmpty();
    }

    public Iterator iterator()
    {
        return value.iterator();
    }

    public ListIterator listIterator()
    {
        return value.listIterator();
    }

    public String remove(int location)
    {
        return value.remove(location);
    }

    public String set(int location, String item)
    {
        return value.set(location, item);
    }

    public int getSize()
    {
        return value.size();
    }

    // public int size(){
    // return value.size();
    // }

    @Override
    /**
     * @Param list   the items in this list is set to an internal list which has toString() overridden.
     */
    public void setValue(List<String> list)
    {
        value.clear();
        value.addAll(list);
    }

    /**
     * return the list of values. Use toString() to marshel when sending as xml.
     */
    @Override
    public List<String> getValue()
    {
        return value;
    }

   
}

// TODO: Consider whether the Size of the list need to be updated in the attribute field.
