package org.xwiki.android.xmodel.xobjects;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.xmodel.entity.Resource;
import org.xwiki.android.xmodel.reference.Link;
/**
 * 
 * @author sasindap
 *
 * @param <T> :The type of fields. i.e: XProperty, XObject
 */
public abstract class XObject<T>  extends Resource implements Serializable,Cloneable {
	protected Map<String, T> fields;
	protected List<Link> links;
	XObject() {
		fields = new Hashtable<String, T>(30);
		links = new ArrayList<Link>();		
	}
	
	public String getType(){
		return "xwiki.lang.XObject";
	}
}
