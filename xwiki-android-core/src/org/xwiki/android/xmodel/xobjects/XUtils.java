package org.xwiki.android.xmodel.xobjects;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public static Date toDate(String ds) throws IllegalArgumentException
    {
       
        String orig=ds;
        String[] fmts = {"yyyy-MM-dd'T'HH:mm:ssZ","yyyy-mm-dd'T'HH:mm:ss.SSSZ","yyyy-mm-dd HH:mm:ss.SSSZ" ,"yyyy-mm-dd HH:mm:ss.SSS"}; //when adding format see ivr[], Input validation routine
        //ex:{2012-08-13T22:26:54+05:30, 2012-08-13T22:26:54.510+05:30 ,2012-08-13 22:26:54.377+05:30 ,2012-08-13 22:26:54.377
        int ivr[] = {0, 1, 1,-1};// input validation routine
        String[] tried=new String[fmts.length]; //for debug only
        
        for (int i = 0; i < fmts.length; i++) {
            ds=orig;
            String fmtStr=fmts[i];
            int key=ivr[i];
            // IVR s
            switch (key) {
                case 0: { //removes : in 05:30  to 0530 >> at the end 2012-08-13T22:26:54+05:30 ---> 2012-08-13T22:26:54+0530 
                    if(ds.length()>21){
                        String gmt = ds.substring(20);
                        gmt = gmt.substring(0, 2) + gmt.substring(3);
                        //System.out.println(gmt);
                        ds = ds.substring(0, 20) + gmt;
                    } 
                    break;
                }               
                case 1: { //same as in 0  :  2012-08-13 22:26:54.377+05:30  ---> 2012-08-13 22:26:54.377+0530 
                    if(ds.length()>25){
                        String gmt = ds.substring(24);
                        gmt = gmt.substring(0, 2) + gmt.substring(3);
                        //System.out.println(gmt);
                        ds = ds.substring(0, 24) + gmt;
                    }
                    break;
                }
                default:
                    break;
            }
            tried[i]=ds;
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
        
        throw new IllegalArgumentException("Unable to Parse"+orig+". After validation processing = "+Arrays.asList(tried)+" .Only supported format are [...] see"+XUtils.class.getSimpleName()+"#toDate(String)");  
    }

    /**
     * @param seperators :string of delimiters like ",.:;/ &+" if null, seperators:=",.:/ "
     * @param list ex: Item1,Item2,Item3,Item4/Item5/Item6:Item7
     * @return
     */
    public static List<String> toStringList(String listStr, String seperators)
    {
        if(listStr==null){
            return new ArrayList<String>();
        }
        seperators = "[" + seperators + "]";
        String[] items = listStr.split(seperators);
        return Arrays.asList(items);
    }

}
