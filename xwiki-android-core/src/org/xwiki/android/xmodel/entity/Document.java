package org.xwiki.android.xmodel.entity;

import java.util.List;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Contains the properties of a Page 
 * +Additional methods for Document.
 * @author sasindap
 *
 */
@DatabaseTable(tableName = "C_Document")
public class Document {

	private String id;
	private String fullName;
	private String wiki;
	private String space;
	private String name;
	private String title;
	private String parent;
	private String parentId;
	private String xwikiRelativeUrl;
	private String xwikiAbsoluteUrl;
	private List<String> translations;
	private String syntax;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the fullName property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the value of the fullName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setFullName(String value) {
		this.fullName = value;
	}

	/**
	 * Gets the value of the wiki property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getWiki() {
		return wiki;
	}

	/**
	 * Sets the value of the wiki property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setWiki(String value) {
		this.wiki = value;
	}

	/**
	 * Gets the value of the space property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getSpace() {
		return space;
	}

	/**
	 * Sets the value of the space property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setSpace(String value) {
		this.space = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the parent property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Sets the value of the parent property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setParent(String value) {
		this.parent = value;
	}

	/**
	 * Gets the value of the parentId property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the value of the parentId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setParentId(String value) {
		this.parentId = value;
	}

	/**
	 * Gets the value of the xwikiRelativeUrl property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getXwikiRelativeUrl() {
		return xwikiRelativeUrl;
	}

	/**
	 * Sets the value of the xwikiRelativeUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setXwikiRelativeUrl(String value) {
		this.xwikiRelativeUrl = value;
	}

	/**
	 * Gets the value of the xwikiAbsoluteUrl property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getXwikiAbsoluteUrl() {
		return xwikiAbsoluteUrl;
	}

	/**
	 * Sets the value of the xwikiAbsoluteUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setXwikiAbsoluteUrl(String value) {
		this.xwikiAbsoluteUrl = value;
	}

	/**
	 * Gets the value of the translations property.
	 * 
	 * @return possible object is {@link Translations }
	 */
	public List<String> getTranslations() {
		return translations;
	}

	/**
	 * Sets the value of the translations property.
	 * 
	 * @param value
	 *            allowed object is {@link Translations }
	 */
	public void setTranslations(List<String> translations) {
		this.translations = translations;
	}

	/**
	 * Gets the value of the syntax property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getSyntax() {
		return syntax;
	}

	/**
	 * Sets the value of the syntax property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setSyntax(String value) {
		this.syntax = value;
	}
}
