/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.test.utils.xmlrpc;

import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;

import java.io.IOException;
import java.io.StringWriter;

/**
 * XWiki Android REST Object related source. Can get the objects as a Object(s) objects of Simple-xml object model
 */
public class ObjectOperations 
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring objects
     */
    private String wikiName;

    /**
     * Name of space for acquiring objects
     */
    private String spaceName;

    /**
     * Name of page for acquiring objects
     */
    private String pageName;
    private RestClient rpc;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     * @param rpc
     */
    ObjectOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    public boolean exists(String objectClassname, String objectNumber) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects" + objectClassname + "/" + objectNumber;

        int code = rpc.headRequest(Uri);
        if (code == 200) {
            return true;
        }
        if (code == 404) {
            return false;
        } else {
            throw new RestException(code);
        }
    }

    /**
     * @return list of all the Objects in a page
     * @throws IOException
     * @throws RestException
     */
    public Objects getAllObjects() throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects";

        return buildObjects(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));

    }

    /**
     * Adds object to the page
     * 
     * @param object Object object to be added to the page
     * @return status of th HTTP post request
     * @throws IOException
     * @throws RestException
     */
    public String addObject(Object object) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects";

        return rpc.postRequest(Uri, buildXmlObject(object)).getStatusLine().toString();

    }

    /**
     * @param objectClassname name of the class
     * @return list of objects in a specific class of a page
     * @throws IOException
     * @throws RestException
     */
    public Objects getObjectsInClassname(String objectClassname) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname;

        return buildObjects(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));

    }

    /**
     * @param objectClassname name of the class in the page
     * @param objectNumber number of the object in the specified class
     * @return selected Object object in the page
     * @throws IOException
     * @throws RestException
     */
    public Object getObject(String objectClassname, String objectNumber) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return buildObject(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));

    }

    /**
     * Update object in a page
     * 
     * @param objectClassname name of the class of the object
     * @param objectNumber number of the object in the class
     * @param object Object object to be updated in the page
     * @return status of the HTTP put request
     * @throws IOException
     * @throws RestException
     */
    public String updateObject(String objectClassname, int objectNumber, Object object)
        throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return rpc.putRequest(Uri, buildXmlObject(object)).getStatusLine().toString();

    }

    /**
     * Deletes the specified object in the page
     * 
     * @param objectClassname name of the class in the page
     * @param objectNumber number of the object in the class
     * @return status of HTTP resonce in delete request
     * @throws IOException
     * @throws RestException
     */
    public String deleteObject(String objectClassname, String objectNumber) throws IOException,
        RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber;

        return rpc.deleteRequest(Uri).getStatusLine().toString();

    }

    /**
     * @param objectClassname class name of the object in a page
     * @param objectNumber number of the object in the class
     * @return list of properties as a Properties object in a object
     * @throws IOException
     * @throws RestException
     */
    public Properties getObjectProperties(String objectClassname, String objectNumber) throws IOException,
        RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties";

        return buildProperties(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));

    }

    /**
     * Adds a property to a object in a page
     * 
     * @param objectClassname class name of the object
     * @param objectNumber number of the object in the class
     * @param property Property object to be added to the Object
     * @return status of the HTTP put request
     * @throws IOException
     * @throws RestException
     */
    public String addObjectProperty(String objectClassname, String objectNumber, Property property)
        throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties/" + property.getName();

        return rpc.putRequest(Uri, buildXmlProperty(property)).getStatusLine().toString();
    }

    /**
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object in the class
     * @param propertyName name of the required property
     * @return Specific property of a object in a page
     * @throws IOException
     * @throws RestException
     */
    public Property getObjectProperty(String objectClassname, String objectNumber, String propertyName)
        throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects/" + objectClassname + "/" + objectNumber + "/properties/" + propertyName;

        return buildProperty(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));

    }

    /**
     * Parse xml into a Objects object
     * 
     * @param content XML content
     * @return Objects object deserialized from the xml content
     */
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

    /**
     * Generate XML content from the Objects object
     * 
     * @param objects Objects object to be serialized into XML
     * @return XML content of the provided Objects object
     */
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

    /**
     * Generate XML content from the Object object
     * 
     * @param object Object object to be serialized into XML
     * @return XML content of the provided Object object
     */
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

    /**
     * Parse xml into a Object object
     * 
     * @param content XML content
     * @return Object object deserialized from the xml content
     */
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

    /**
     * Parse xml into a Properties object
     * 
     * @param content XML content
     * @return Properties object deserialized from the xml content
     */
    private Properties buildProperties(String content)
    {
        Properties properties = new Properties();

        Serializer serializer = new Persister();

        try {
            properties = serializer.read(Properties.class, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Generate XML content from the Property object
     * 
     * @param property Property object to be serialized into XML
     * @return XML content of the provided Property object
     */
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

    /**
     * Parse xml into a Property object
     * 
     * @param content XML content
     * @return Property object deserialized from the xml content
     */
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
