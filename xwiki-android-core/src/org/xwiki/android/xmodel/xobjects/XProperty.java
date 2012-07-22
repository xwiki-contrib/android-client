package org.xwiki.android.xmodel.xobjects;

import java.util.Map;

public interface XProperty<T>
{
    void setName(String name);

    String getName();

    //void setType(String type); Type should be implicit. New X___Property should be created for every type.

    String getType();

    void setValue(T val);

    /**
     * @return the value. Can be of any type. String, Number ... Value's toString() method does not always return the
     *         Xwiki ReSTful representation of the XProperty value. Use the XProperty.toString() method to get the
     *         string representation of the val.
     */
    T getValue();

    void setValueFromString(String val);

    /**
     * @return XWiki ReSTful representation of the value of this property.
     */
    String toString();

    /**
     * @param name  attribute name
     * @param val if String , the val should be parse-able to the original type of the attribute. ex: attribute size.
     *            setAttribute("size", "2")
     * @throws IllegalArgumentException
     */
    void setAttribute(String name, Object val) throws IllegalArgumentException;

    Object getAttribute(String name);

    

    /**
     * @return map of attributes of the XProperty.
     */
    Map<String, Object> getAllAttributes();

}
