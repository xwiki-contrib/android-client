package org.xwiki.android.rest.ral;

import org.xwiki.android.xmodel.entity.Document;

import android.util.Log;

/**
 * 
 * Verifiere to check parameters passed to each ral method.
 *
 */
class Verifier
{
    
    

    private static final String TAG ="RAL Verifier";

    /** 
     * @param doc
     * @return verification result. success=true
     */
    static boolean verifyDocumentForCreate(Document doc){
        boolean title=false; //verify title set
        title=(doc.getTitle()!=null && !doc.getTitle().equals(""));
        if(!title){
            Log.w(TAG, "doc must have a title. For XWiki version 3.x and below.");
        }
        return title;
        
    }
}
