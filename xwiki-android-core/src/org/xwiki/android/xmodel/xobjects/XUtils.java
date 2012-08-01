package org.xwiki.android.xmodel.xobjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xwiki gsoc 2012
 */

public class XUtils
{
    
    /**
     * 
     * @param val
     * @return false for null vals. true for=1,"true  false for=0,"false"
     * @throws IllegalArgumentException 
     */
    public static boolean toBoolean(String val) throws IllegalArgumentException
    {
        boolean value;
        if(val==null)return false;
        
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

    public static Date toDate(String ds)
    {
       

        String[] fmts = {"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-mm-dd HH:mm:ss.SSS"};
        int ivr[] = {0, -1};// input validation routine

        for (int i = 0; i < fmts.length; i++) {
            String fmtStr=fmts[i];
            int key=ivr[i];
            // IVR s
            switch (key) {
                case 0: {
                	if(ds.length()>21){
                		String gmt = ds.substring(20);
                        gmt = gmt.substring(0, 2) + gmt.substring(3);
                        System.out.println(gmt);
                        ds = ds.substring(0, 20) + gmt;
                	}                    
                }
                    break;
                default:
                    break;
            }
            
            //try to parse
            DateFormat fmt = new SimpleDateFormat(fmtStr); 
            Date d = null;
            try {
                d = fmt.parse(ds);
                return d;
            } catch (ParseException e) {               
            }
        }

        // end
        throw new IllegalArgumentException(
            "date format not supported. Only supported format are [...] see XUtils.toDate(String)");  
    }

    /**
     * @param seperators :string of delimiters like ",.:;/ &+" if null, seperators:=",.:/ "
     * @param list ex: Item1,Item2,Item3,Item4/Item5/Item6:Item7
     * @return
     */
    public static List<String> toStringList(String listStr, String seperators)
    {
        seperators = "[" + seperators + "]";
        String[] items = listStr.split(seperators);
        return Arrays.asList(items);
    }

}
