package org.xwiki.android.xmodel.xobjects;

import java.util.Map;

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
	protected int number;
	protected String headline;
	
    @Override
    /**
     * same as get className
     */
	public String getType(){
    	return className;
    }
    
    /**
     * 
     * @return  the map of XProperties keyed by property name.
     */
    public Map<String,XProperty> getProperties(){
    	return fields;
    }

	public String getId() {
		return id;
	}

	public String getGuid() {
		return guid;
	}

	public String getPageid() {
		return pageid;
	}

	public String getSpace() {
		return space;
	}

	public String getWiki() {
		return wiki;
	}

	public String getPageName() {
		return pageName;
	}

	public String getClassName() {
		return className;
	}

	public int getNumber() {
		return number;
	}

	public String getHeadline() {
		return headline;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}
    
    
}
