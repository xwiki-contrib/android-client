package org.xwiki.android.xmodel.xobjects;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.rest.reference.Link;
import org.xwiki.android.xmodel.entity.XWikiResource;

/**
 * @author sasindap
 * @param <T> :The type of fields. i.e: XProperty, XObject
 */
public abstract class XObject<T> extends XWikiResource implements Serializable, Cloneable
{
    protected Map<String, T> fields;

    protected List<Link> links;

    XObject()
    {
        fields = new Hashtable<String, T>(30);
        links = new ArrayList<Link>();
    }
    
}
