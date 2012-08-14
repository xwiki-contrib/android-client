package org.xwiki.android.xmodel.xobjects;

import java.util.Map;

/**
 * @author xwiki gsoc 2012 Represents an XObject that can have only objects of Type {@link XProperty} as property
 *         fields. XSimple Objects have primitive fields referred to as attributes.
 */

public abstract class XSimpleObject extends XObject<XProperty>
{
    // general properties are stored in XElement Base class.

    //
    // special attributes for an Object
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

   
    public XSimpleObject(String className)
    {
        this.className=className;
    }
    
    
    
    
//get set pairs.    

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getPageid()
    {
        return pageid;
    }

    public void setPageid(String pageid)
    {
        this.pageid = pageid;
    }

    public String getSpace()
    {
        return space;
    }

    public void setSpace(String space)
    {
        this.space = space;
    }

    public String getWiki()
    {
        return wiki;
    }

    public void setWiki(String wiki)
    {
        this.wiki = wiki;
    }

    public String getPageName()
    {
        return pageName;
    }

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    public String getClassName()
    {
        return className;
    }
   

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getHeadline()
    {
        return headline;
    }

    public void setHeadline(String headline)
    {
        this.headline = headline;
    }
    
    /**
     * @return the map of XProperties keyed by property name.
     */
    public Map<String, XProperty> getProperties()
    {
        return fields;
    }
    
    public void setProperties(Map<String, XProperty> props){
        fields=props;
    }
    /**
     * 
     * @param p
     *  property to set. Key will be p.getName.
     */
    public void setProperty(XProperty p){
        if(p.getName()==null)throw new NullPointerException();//no need for HashTable. But if we change to HashMap or ...
        fields.put(p.getName(), p);
    }
    public void setProperty(String key,XProperty p){        
        fields.put(key,p);
    }
    
    public void removeProperty(String pname){
        fields.remove(pname);
    }

}
