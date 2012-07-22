package org.xwiki.android.xmodel.xobjects;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xwiki gsoc 2012
 * 
 */

public class XUtils
{
    static boolean toBoolean(String val) throws IllegalArgumentException
    {
        boolean value;
        if (val.equals("1")) {
            value = true;
        } else if (val.equals("0")) {
            value = false;
        } else if (val.equals("true")) {
            value = true;
        } else if (val.equals("false")) {
            value = false;
        } else {
            throw new IllegalArgumentException("valid args=0,1,true,false");
        }
        return value;
    }
    
    /**
     * 
     * @param seperators :string of delimiters like ",.:;/ &+"
     *          if null, seperators:=",.:/ "
     * @param list ex: Item1,Item2,Item3,Item4/Item5/Item6:Item7
     * @return 
     */
    static List<String> toStringList(String listStr, String seperators){        
        seperators="["+seperators+"]";
        String[] items=listStr.split(seperators);
        return Arrays.asList(items);
    }
    
}
