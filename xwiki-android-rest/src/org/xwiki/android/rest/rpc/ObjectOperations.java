package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface ObjectOperations
{

    public abstract boolean exists(String objectClassname, String objectNumber) throws RestConnectionException,
        RestException;

    /**
     * @return list of all the Objects in a page
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Objects getAllObjects() throws RestConnectionException, RestException;

    /**
     * Adds object to the page
     * 
     * @param object Object object to be added to the page
     * @return status of th HTTP post request
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract String addObject(Object object) throws RestConnectionException, RestException;

    /**
     * @param objectClassname name of the class
     * @return list of objects in a specific class of a page
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Objects getObjectsInClassname(String objectClassname) throws RestConnectionException, RestException;

    /**
     * @param objectClassname name of the class in the page
     * @param objectNumber number of the object in the specified class
     * @return selected Object object in the page
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Object getObject(String objectClassname, String objectNumber) throws RestConnectionException,
        RestException;

    /**
     * Update object in a page
     * 
     * @param objectClassname name of the class of the object
     * @param objectNumber number of the object in the class
     * @param object Object object to be updated in the page
     * @return status of the HTTP put request
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract String updateObject(String objectClassname, String objectNumber, Object object)
        throws RestConnectionException, RestException;

    /**
     * Deletes the specified object in the page
     * 
     * @param objectClassname name of the class in the page
     * @param objectNumber number of the object in the class
     * @return status of HTTP resonce in delete request
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract String deleteObject(String objectClassname, String objectNumber) throws RestConnectionException,
        RestException;

    /**
     * @param objectClassname class name of the object in a page
     * @param objectNumber number of the object in the class
     * @return list of properties as a Properties object in a object
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Properties getObjectProperties(String objectClassname, String objectNumber)
        throws RestConnectionException, RestException;

    /**
     * Adds a property to a object in a page
     * 
     * @param objectClassname class name of the object
     * @param objectNumber number of the object in the class
     * @param property Property object to be added to the Object
     * @return status of the HTTP put request
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract String addObjectProperty(String objectClassname, String objectNumber, Property property)
        throws RestConnectionException, RestException;

    /**
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object in the class
     * @param propertyName name of the required property
     * @return Specific property of a object in a page
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Property getObjectProperty(String objectClassname, String objectNumber, String propertyName)
        throws RestConnectionException, RestException;

}
