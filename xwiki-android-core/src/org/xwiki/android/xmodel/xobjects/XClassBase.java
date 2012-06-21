package org.xwiki.android.xmodel.xobjects;

import java.util.Hashtable;
import java.util.Map;

public abstract class XClassBase {
	//has the properties stored in fields list in XElement
	protected Map<String, XObject> objects;	
	public XClassBase() {
		objects=new Hashtable<String, XObject>();
	}
}
