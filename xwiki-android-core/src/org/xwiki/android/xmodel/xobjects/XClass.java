package org.xwiki.android.xmodel.xobjects;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author xwiki gsoc 2012 class representing the class of an XObject. Similar concept to java.lang.Class for
 *         java.lang.Object.
 */
// TODO: make similar to java.lang.Class<T>
public abstract class XClass extends XObject<XProperty>
{
    // has the properties stored in fields list in XElement
    public XClass()
    {
    }

    // getName()
    // getSimpleName()

    /**
	 * 
	 */
    public static String getType()
    {
        return "XWiki.lang.XClass";
    }
}
