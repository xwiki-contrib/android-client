package org.xwiki.android.ral.transformation;

import java.util.Hashtable;

import org.xwiki.android.blog.xobj.XBlogPost;
import org.xwiki.android.blog.xobj.XCategory;
import org.xwiki.android.xmodel.xobjects.XBooleanProperty;
import org.xwiki.android.xmodel.xobjects.XDBListProperty;
import org.xwiki.android.xmodel.xobjects.XDBTreeListProperty;
import org.xwiki.android.xmodel.xobjects.XDateProperty;
import org.xwiki.android.xmodel.xobjects.XDoubleProperty;
import org.xwiki.android.xmodel.xobjects.XFloatProperty;
import org.xwiki.android.xmodel.xobjects.XIntegerProperty;
import org.xwiki.android.xmodel.xobjects.XLongProperty;
import org.xwiki.android.xmodel.xobjects.XNumberProperty;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XPropertyBase;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;
import org.xwiki.android.xmodel.xobjects.XStaticListProperty;
import org.xwiki.android.xmodel.xobjects.XStringProperty;
import org.xwiki.android.xmodel.xobjects.XTextAreaProperty;

/**
 * @author xwiki gsoc 2012
 */
public class XPropertyFactory
{
    private static Hashtable<String, Class< ? extends XProperty>> xPMap;
    static {
        xPMap = new Hashtable<String, Class< ? extends XProperty>>(20);

        xPMap.put("com.xpn.xwiki.objects.classes.BooleanClass", XBooleanProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.DateClass", XDateProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.DBListClass", XDBListProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.DBTreeListClass", XDBTreeListProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.NumberClass", XNumberProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.StaticListClass", XStaticListProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.StringClass", XStringProperty.class);
        xPMap.put("com.xpn.xwiki.objects.classes.TextAreaClass", XTextAreaProperty.class);
    }

    /**
     * Use this.newNumberProperty(String numberType) for getting Xproperty of type
     * com.xpn.xwiki.objects.classes.NumberClass
     * 
     * @param type of the property.
     * @return
     * @throws IllegalArgumentException : if there is no XProperty for the given type.
     */
    public XProperty newXProperty(String type) throws IllegalArgumentException
    {
        XProperty p = null;
        Class< ? extends XProperty> cls = xPMap.get(type);
        if (cls == null)
            throw new IllegalArgumentException("class Name not identified");
        try {
            p=cls.newInstance();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return p;        
    }

    public XNumberProperty newNumberProperty(String numberType) throws IllegalArgumentException
    {
        // double,float,integer,long
        XNumberProperty prod = null;
        char c = numberType.charAt(0);
        if (c == 'i') {// inte
            prod = new XIntegerProperty();
        } else if (c == 'l') {// long
            prod = new XLongProperty();
        } else if (c == 'f') {// float
            prod = new XFloatProperty();
        } else if (c == 'd') {// double
            prod = new XDoubleProperty();
        }else{
            throw new IllegalArgumentException("number Type not recognized");
        }
        return prod;
    }
}
