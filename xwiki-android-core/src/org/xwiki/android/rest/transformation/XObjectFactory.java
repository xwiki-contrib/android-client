package org.xwiki.android.rest.transformation;

import java.util.Hashtable;

import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.blog.XCategory;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

/**
 * Maps Objects in ReSTFul model to XSimple Objects. Please Register new XSimple Objects in this class
 * 
 * @author xwiki gsoc 2012
 */
public class XObjectFactory
{
    private static Hashtable<String, Class<? extends XSimpleObject>> xobjMap;
    static {
        xobjMap = new Hashtable<String, Class<? extends XSimpleObject>>(20);
        // Include code here to map XSimple Objects to the xmlrpc model objects by className. (keep alphabetic ordering
        // for clarity)

        // xobj in app within minutes

        // xobj in blog
        xobjMap.put("Blog.BlogPostClass", XBlogPost.class);
        xobjMap.put("Blog.CategoryClass", XCategory.class);

    }
    
    /**
     * 
     * @param restClassName
     * @return XSimpleObject if no special extentions of XSimpleObject is regestered for the restClassName.Else an extension of XSimpleObject
     */
    public XSimpleObject newXSimpleObject(String restClassName){
        XSimpleObject product=null;
        
        Class<? extends XSimpleObject> c=xobjMap.get(restClassName);
        if(c!=null){
            try {
                product=c.newInstance();
            } catch (IllegalAccessException e) {            
                e.printStackTrace();
            } catch (InstantiationException e) {           
                e.printStackTrace();
            }
        }else{
            product=new XSimpleObject(restClassName)
            {
            };
        }        
        return product;
    }
    

}
