package org.xwiki.android.rest;

import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;

import com.google.gson.Gson;

public class ObjectResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public ObjectResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public Objects getAllObjects()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects" + JSON_URL_SUFFIX;

        return decodeObjects(super.getResponse(Uri));

    }

    // Add POST method
    public String addObject(Object object)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects" + JSON_URL_SUFFIX;

        // For generating xml
        String content = object.toString();

        return super.postRequest(Uri, content);

    }

    public Objects getObjectsInClassname(String objectClassname)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + JSON_URL_SUFFIX;

        return decodeObjects(super.getResponse(Uri));

    }

    // /wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/objects/{className}/{objectNumber}
    public Object getObject(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + JSON_URL_SUFFIX;

        return decodeObject(super.getResponse(Uri));

    }

    // Add PUT method
    public String updateObject(String objectClassname, String objectNumber, Object object)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + JSON_URL_SUFFIX;

        // For generating xml
        String content = object.toString();

        return super.putRequest(Uri, content);

    }

    // Delete object
    public String deleteObject(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + JSON_URL_SUFFIX;

        return super.deleteRequest(Uri);

    }

    public Properties getObjectProperties(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties" + JSON_URL_SUFFIX;

        return decodeProperties(super.getResponse(Uri));

    }
    
    //add property
    public String addObjectProperty(String objectClassname, String objectNumber, Property property)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties" + JSON_URL_SUFFIX;

        String content= property.toString();
        
        return super.putRequest(Uri, content);

    }

    // wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/objects/{className}/{objectNumber}/properties/{propertyName}
    public Property getObjectProperty(String objectClassname, String objectNumber, String propertyName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties/" + propertyName + JSON_URL_SUFFIX;

        return decodeProperty(super.getResponse(Uri));

    }

    // Add PUT method

    // decode json content to Objects element
    private Objects decodeObjects(String content)
    {
        Gson gson = new Gson();

        Objects objects = new Objects();
        objects = gson.fromJson(content, Objects.class);
        return objects;
    }

    // decode json content to Object element
    private Object decodeObject(String content)
    {
        Gson gson = new Gson();

        Object object = new Object();
        object = gson.fromJson(content, Object.class);
        return object;
    }

    // decode json content to Properties element
    private Properties decodeProperties(String content)
    {
        Gson gson = new Gson();

        Properties properties = new Properties();
        properties = gson.fromJson(content, Properties.class);
        return properties;
    }

    // decode json content to Properties element
    private Property decodeProperty(String content)
    {
        Gson gson = new Gson();

        Property property = new Property();
        property = gson.fromJson(content, Property.class);
        return property;
    }
}
