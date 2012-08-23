package org.xwiki.android.howtos;

import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XBooleanProperty;


public class _02_AdvancedObjectEditing
{
    public void demo(){
        
        Document mydoc=new Document("wikiName", "spaceName", "pageName");//create empty document        
        XBlogPost blgpstObj=new XBlogPost(); 
        blgpstObj.setHidden(true);//simple way to edit the value of "hidden" property of XBlogPost.
        
        //say, now we want to do some advanced stuff with that property.
        XBooleanProperty hiddenP= blgpstObj.getpHidden();// use getp, setp methods to get the property.
        //lets change hidden again.
        hiddenP.setValue(false);
        //change an attribute of the property.
        hiddenP.setPrettyName("Hidden pretty name");
        
        //Since a reference is passed, you do not have to do a blgpstObj.setpHidden(hiddenP) to commit the effects.
        
    }
}
