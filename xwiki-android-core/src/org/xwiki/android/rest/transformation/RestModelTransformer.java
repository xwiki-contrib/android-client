package org.xwiki.android.rest.transformation;

import java.util.ArrayList;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Attribute;

import org.xwiki.android.xmodel.xobjects.XComment;

/**
 * transforms XModel Objects to XModel 1st class entities and vice versa.
 * 
 * @author xwiki gsoc 2012
 */
public class RestModelTransformer
{
    public Object toObject(Comment c)
    {
        Object o=new Object();        
        
        o.setClassName("XWiki.XWikiComments");
        
        Property id,author,date,comment,replyto = null;
        
        id=newProperty("id", c.id+"");        
        author=newProperty("author", c.author);
        date=newProperty("date", c.getDate());
        comment=newProperty("comment", c.text);        
        if(c.replyTo!=null){
            replyto=newProperty("replyto", c.replyTo.toString());
        }
        
        return o.withProperties(id,author,date,comment,replyto);         
    }

    public  Object toObject(Tags ts)
    {
        throw new UnsupportedOperationException("//TODO");
    }

    public  Tags toTags(Object o)
    {
        throw new UnsupportedOperationException("//TODO");
    }

    public  Comment toComment(Object o)
    {
        throw new UnsupportedOperationException("//TODO");
    }
    
    private Property newProperty(String name, String value){
        if(value==null)return null;
        Property p=new Property();
        p.setName(name);
        p.setValue(value);
        p.attributes=new ArrayList<Attribute>();
        return p;
    }

}
