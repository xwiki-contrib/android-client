package org.xwiki.android.rest.transformation;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.xmodel.xobjects.XComment;

/**
 * transforms XModel Objects to XModel 1st class entities and vice versa.
 * @author xwiki gsoc 2012
 *
 */
public class RestModelTransformer
{
    public static Object toObject(Comment c){
        XComment xc=new XComment();
        xc.setAuthor(c.getAuthor());
        xc.setComment(c.getText());
        //xc.setDate(c.getDate());
        //TODO        
        return new XModelTranslator_XML().toObject(xc);
    }
    
    public static Object toObject(Tags ts){
        throw new UnsupportedOperationException("//TODO");
    }
    
    public static Tags toTags(Object o){
        throw new UnsupportedOperationException("//TODO");        
    }
    
    public static Comment toComment(Object o){
        throw new UnsupportedOperationException("//TODO");        
    }

}
