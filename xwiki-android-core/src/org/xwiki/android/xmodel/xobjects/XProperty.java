package org.xwiki.android.xmodel.xobjects;

import java.util.Map;

public interface XProperty<T> {	
	void setName(String name);
	void setType(String type);
	void setValue(T val);
	String getName();
	String getType();
	/**
	 * 
	 * @return the value. Can be of any type. String, Number ...
	 * 			Value's toString() method returns the Xwiki ReSTful representation of the XProperty value.
	 */
	T getValue();
	
	/**
	 * 
	 * @return map of attributes of the XProperty.
	 */
	Map<String,Object> getAllAttributes();
}
