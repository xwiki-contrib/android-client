package org.xwiki.android.rest.ral.wrappers;

import java.util.Map;

import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public abstract class XSimpleObjectWrapper extends XSimpleObject
{
    protected XSimpleObject xso;    
    public XSimpleObjectWrapper(XSimpleObject xso)
    {
        super("XSimpleObjectWrapper");
        this.xso=xso;
    }
   
    /**
     * 
     * @return wrapped up object unboxed.
     */
    public XSimpleObject unwrap(){
        return xso;
    }
    
    /**
     * Using wrappers in get set ops for the core XSimpleObject is not efficient.You may unwrap and do the get/set and wrap again.
     * @param box an object in this wrapper.
     */
    public void wrap(XSimpleObject o){
        this.xso=o;
    }


    
    
    @Override
    public abstract void setNew(boolean val);
    @Override
    public abstract boolean isNew();
    @Override
    public abstract boolean isEdited();
    @Override
    public abstract String getId();
    @Override
    public abstract void setEdited(boolean altered);
    @Override
    public abstract void setId(String id);
    @Override
    public abstract String getGuid();    
    @Override
    public abstract void setGuid(String guid);
    @Override
    public abstract String getPageid();
    @Override
    public abstract void setPageid(String pageid);
    @Override
    public abstract String getSpace();
    @Override
    public abstract void setSpace(String space);
    @Override
    public abstract String getWiki();
    @Override
    public abstract void setWiki(String wiki);
    @Override
    public abstract String getPageName();
    @Override
    public abstract void setPageName(String pageName);
    @Override
    public abstract String getClassName();

    @Override
    public abstract int getNumber();
    @Override
    public abstract void setNumber(int number);
    @Override
    public abstract String getHeadline();
    @Override
    public abstract void setHeadline(String headline);
    @Override
    public abstract Map<String, XProperty> getProperties();
    @Override
    public abstract void setProperties(Map<String, XProperty> props);
    @Override
    public abstract void setProperty(XProperty p);
    @Override
    public abstract void setProperty(String key, XProperty p);
    @Override
    public abstract void removeProperty(String pname);
//    @Override
//    public abstract boolean equals(Object o);  
    
    
    
}
