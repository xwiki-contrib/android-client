package org.xwiki.android.xmodel.xobjects;
/**
 * 
 * @author xwiki gsoc 2012
 *
 *Represents an XObject that can have only objects of Type {@link XProperty} as property fields.
 *XSimple Objects have primitive fields referred to as attributes.
 */

public abstract class XSimpleObject extends XObject<XProperty>{
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
	
    @Override
	public String getType(){
    	return "xwiki.lang.XSimpleObject";
    }
}
