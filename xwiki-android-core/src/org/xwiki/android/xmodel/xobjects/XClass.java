package org.xwiki.android.xmodel.xobjects;

import java.util.Hashtable;
import java.util.Map;
//TODO: make similar to java.lang.Class<T>
public abstract class XClass extends XObject<XProperty> {
	// has the properties stored in fields list in XElement
	public XClass() {
	}
	
	//getName()
	//getSimpleName()
	
	/**
	 * 
	 */	
	@Override
	public String getType(){
		return "XWiki.lang.XClass";
	}
}
