package org.xwiki.android.rest.ral.wrappers;

import java.util.Map;

import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public class XSimpleObjectWrapper_RAL extends XSimpleObjectWrapper
{

    public XSimpleObjectWrapper_RAL(XSimpleObject xso)
    {
        super(xso);        
    }
    
//delegates
    
    @Override
    public void setNew(boolean val)
    {
        xso.setNew(val);
    }

    @Override
    public boolean isNew()
    {
        return xso.isNew();
    }
   

    @Override
    public boolean isEdited()
    {
        return xso.isEdited();
    }

    @Override
    public String getId()
    {
        return xso.getId();
    }

    @Override
    public void setEdited(boolean altered)
    {
        xso.setEdited(altered);
    }

    @Override
    public void setId(String id)
    {
        xso.setId(id);
    }

    @Override
    public String getGuid()
    {
        return xso.getGuid();
    }

    @Override
    public void setGuid(String guid)
    {
        xso.setGuid(guid);
    }

    @Override
    public String getPageid()
    {
        return xso.getPageid();
    }

    @Override
    public void setPageid(String pageid)
    {
        xso.setPageid(pageid);
    }

    @Override
    public String getSpace()
    {
        return xso.getSpace();
    }

    @Override
    public void setSpace(String space)
    {
        xso.setSpace(space);
    }

    @Override
    public String getWiki()
    {
        return xso.getWiki();
    }

    @Override
    public void setWiki(String wiki)
    {
        xso.setWiki(wiki);
    }

    @Override
    public String getPageName()
    {
        return xso.getPageName();
    }

    @Override
    public void setPageName(String pageName)
    {
        xso.setPageName(pageName);
    }

    @Override
    public String getClassName()
    {
        return xso.getClassName();
    }    

    @Override
    public int getNumber()
    {
        return xso.getNumber();
    }

    @Override
    public void setNumber(int number)
    {
        xso.setNumber(number);
    }

    @Override
    public String getHeadline()
    {
        return xso.getHeadline();
    }

    @Override
    public void setHeadline(String headline)
    {
        xso.setHeadline(headline);
    }

    @Override
    public Map<String, XProperty> getProperties()
    {
        return xso.getProperties();
    }

    @Override
    public void setProperties(Map<String, XProperty> props)
    {
        xso.setProperties(props);
    }

    @Override
    public void setProperty(XProperty p)
    {
        xso.setProperty(p);
    }

    @Override
    public void setProperty(String key, XProperty p)
    {
        xso.setProperty(key, p);
    }

    @Override
    public void removeProperty(String pname)
    {
        xso.removeProperty(pname);
    }

}
