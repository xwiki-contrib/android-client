package org.xwiki.android.xmodel.xobjects;

/**
 * 
 * @author xwiki
 * 
 *         attribute imple special: value general: size,picker
 */
public class XStringProperty extends XPropertyBase<String> {
	String value;

	public XStringProperty() {
		type = "com.xpn.xwiki.objects.classes.StringClass";
		
	}

	// special attr
	
	public void setValue(String val) {
		value = val;
	}

	
	public String getValue() {
		return value;
	}

	// general attr

	public Integer setSize(int size) {
		return (Integer) fields.put("size", size);
	}

	public Boolean setPicker(boolean picker) {
		return (Boolean) fields.put("picker", picker);
	}
	
	public Integer getSize() {
		return (Integer) fields.get("size");
	}

	public Boolean isPicker() {
		return (Boolean) fields.get("picker");
	}

	

}
