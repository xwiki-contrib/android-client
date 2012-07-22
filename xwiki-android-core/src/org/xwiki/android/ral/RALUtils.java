package org.xwiki.android.ral;

public class RALUtils
{
   public static int getStatusCode(String statusLine){
       // can use split(" ") and parse. More robust.
       return  Integer.parseInt(statusLine.substring(9, 12));
   }
}
