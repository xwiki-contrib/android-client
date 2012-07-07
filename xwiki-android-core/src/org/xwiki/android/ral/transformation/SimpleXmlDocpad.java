package org.xwiki.android.ral.transformation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Object;

/**
 * @author xwiki gsoc 2012 A scratch pad for converting. !!! Public but for ral package use only.
 */
public class SimpleXmlDocpad
{
    Page page;

    String wikiName, spaceName, pageName;

    List<Object> newObjects; // new local objs

    Map<String, Object> editedObjects; // locally

    List<String> deletedObjects; // locally

    public SimpleXmlDocpad()
    {
        newObjects = new ArrayList<Object>();
        editedObjects = new Hashtable<String, Object>();
        deletedObjects = new ArrayList<String>();
    }

    /**
     * @return the page
     */
    public Page getPage()
    {
        return page;
    }

    /**
     * @return the wikiName
     */
    public String getWikiName()
    {
        return wikiName;
    }

    /**
     * @return the spaceName
     */
    public String getSpaceName()
    {
        return spaceName;
    }

    /**
     * @return the pageName
     */
    public String getPageName()
    {
        return pageName;
    }

    /**
     * @return the newObjects
     */
    public List<Object> getNewObjects()
    {
        return newObjects;
    }

    /**
     * @return the editedObjects
     */
    public Map<String, Object> getEditedObjects()
    {
        return editedObjects;
    }

    /**
     * @return the deletedObjects
     */
    public List<String> getDeletedObjects()
    {
        return deletedObjects;
    }

    void addNewOjbect(Object obj)
    {
        newObjects.add(obj);
    }

    boolean markDeletedObject(String objkey)
    {
        return deletedObjects.add(objkey);
    }

    Object putEditedObject(String key, Object obj)
    {
        return editedObjects.put(key, obj);
    }

    // TODO: implement fr comments...
    // TODO: implement XSimpleObjectWrappers, DocumentWrapper for lazy fetching. Also maps to hold wrapped objs.

}
