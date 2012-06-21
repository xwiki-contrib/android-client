package org.xwiki.android.xmodel.xobjects;


public abstract class XObjectBase extends XElement<XProperty> implements XObject {
	//general properties are stored in XElement Base class.
	
	//
	//special attributes for an Object
	//
	
	protected String id;
	protected String guid;
	protected String pageid;
	protected String space;
	protected String wiki;
	protected String pageName;
	protected String className;
	protected String number;
	protected String headline;
	
}
