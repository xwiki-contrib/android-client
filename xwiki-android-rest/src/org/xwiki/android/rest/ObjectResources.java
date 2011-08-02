package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;


public class ObjectResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

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
                + "/objects";

        return buildObjects(super.getResponse(Uri));

    }

    // Add POST method
    public String addObject(Object object)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects";

        return super.postRequest(Uri, buildXmlObject(object));

    }

    public Objects getObjectsInClassname(String objectClassname)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname;

        return buildObjects(super.getResponse(Uri));

    }

    // /wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/objects/{className}/{objectNumber}
    public Object getObject(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return buildObject(super.getResponse(Uri));

    }

    // Add PUT method
    public String updateObject(String objectClassname, String objectNumber, Object object)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return super.putRequest(Uri, buildXmlObject(object));

    }

    // Delete object
    public String deleteObject(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return super.deleteRequest(Uri);

    }

    public Properties getObjectProperties(String objectClassname, String objectNumber)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties";

        return buildProperties(super.getResponse(Uri));

    }

    // add property
    public String addObjectProperty(String objectClassname, String objectNumber, Property property)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties";

        return super.putRequest(Uri, buildXmlProperty(property));
    }

    // wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/objects/{className}/{objectNumber}/properties/{propertyName}
    public Property getObjectProperty(String objectClassname, String objectNumber, String propertyName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties/" + propertyName;

        return buildProperty(super.getResponse(Uri));

    }


    // decode xml content to Objects element
    private Objects buildObjects(String content)
    {
        Objects objects = new Objects();

        Serializer serializer = new Persister();

        try {
            objects = serializer.read(Objects.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return objects;
    }

    // build xml from Objects object
    private String buildXmlObjects(Objects objects)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(objects, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // build xml from Objects object
    private String buildXmlObject(Object object)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(object, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // decode xml content to Object element
    private Object buildObject(String content)
    {
        Object object = new Object();

        Serializer serializer = new Persister();

        try {
            object = serializer.read(Object.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return object;
    }

    // decode xml content to Properties elements
    private Properties buildProperties(String content)
    {
        Properties properties = new Properties();

        Serializer serializer = new Persister();

        try {
            properties = serializer.read(Properties.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return properties;
    }

    // build xml from Property object
    private String buildXmlProperty(Property property)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(property, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // decode xml content to Property elements
    private Property buildProperty(String content)
    {
        Property property = new Property();

        Serializer serializer = new Persister();

        try {
            property = serializer.read(Property.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return property;
    }
}
