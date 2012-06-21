package org.xwiki.android.xmodel.xobjects;

public interface XPoperty<T> {	
	void setName(String name);
	void setType(String type);
	String getName();
	String getType();
	void setValue(T val);
	T getValue();
}
