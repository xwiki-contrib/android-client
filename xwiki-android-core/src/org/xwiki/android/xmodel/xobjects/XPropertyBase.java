package org.xwiki.android.xmodel.xobjects;

import java.util.Map;
/**
 * 
 * @author sasinda
 *
 * @param <T> :Type of the special attribute "value". 
 * implemented attributes
 * spcial:
 * name, type
 * general:prettyName, number, tooltip, customDisplay, disabled, unmodifiable,validationMessage, validationRegExp,
 * 
 */
public abstract class XPropertyBase<T> extends XObject<Object> implements XProperty<T> {

	protected String name;
	protected String type;	

	//
	// special fields
	//

	public void setName(String name) {
		this.name = name;
		setAttribute("name", name);
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	

	//
	// general attributes
	//

	// set methods

	public String setPrettyName(String prettyName) {
		return (String) fields.put("prettyName", prettyName);
	}

	public Integer setNumber(int number) {
		return (Integer) fields.put("number", number);
	}

	public String setTooltip(String tooltip) {
		return (String) fields.put("tooltip", tooltip);
	}

	public String setCustomDisplay(String value) {
		return (String) fields.put("customDisplay", value);
	}

	public Boolean setDisabled(boolean disabled) {
		return (Boolean) fields.put("disabled", disabled);

	}
	
	public Boolean setUnmodifiable(boolean unmodifialbe) {
		return (Boolean) fields.put("unmodifiable",unmodifialbe);
	}

	public String setValidationMessage(String validationMessage) {
		return (String) fields.put("validationMessage", validationMessage);
	}

	public String setValidationRegExp(String regex) {
		return (String) fields.put("validationRegExp", regex);
	}

	// get methods

	public String getPrettyName() {
		return (String) fields.get("prettyName");
	}

	public Integer getNumber() {
		return (Integer) fields.get("number");
	}

	public String getTooltip() {
		return (String) fields.get("tooltip");
	}

	public String getCustomDisplay() {
		return (String) fields.get("customDisplay");
	}

	public Boolean isDisabled() {
		return (Boolean) fields.get("disabled");
	}
	public Boolean isUnmodifiable() {
		return (Boolean) fields.get("unmodifiable");
	}

	public String getValidationMessage() {
		return (String) fields.get("validationMessage");
	}

	public String getValidationRegExp() {
		return (String) fields.get("validationRegExp");
	}

	/**
	 * puts the toString() value of the attrValue to fields Map.
	 * 
	 * @param attrName
	 * @param attrValue
	 * @return the toString value of the attrValue
	 */
	protected Object setAttribute(String attrName, Object attrValue) {
		return fields.put(attrName, attrValue);
	}

	/**
	 * 
	 * @return attrValue
	 */
	protected Object getAttribue(String attrName) {
		return fields.get(attrName);
	}

}
