package org.xwiki.android.xmodel.xobjects;
/**
 * 
 * @author xwiki
 * 
 * attribute implimentation: special: value
 * general: displayFormType, displayType, defaultValue
 */
public class XBooleanProperty extends XPropertyBase<Boolean> {

	
	public void setValue(Boolean val) {
		// TODO Auto-generated method stub
		
	}

	
	public Boolean getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
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
