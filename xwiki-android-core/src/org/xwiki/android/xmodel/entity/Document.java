package org.xwiki.android.xmodel.entity;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.ral.RestAPIUsageException;
import org.xwiki.android.xmodel.reference.DocumentReference;
import org.xwiki.android.xmodel.reference.Link;
import org.xwiki.android.xmodel.xobjects.XObject;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Contains the properties of a Page 
 * +Additional methods for Document.
 * @author xwiki
 *
 *dev: not saved to DB. Only the DocumentReference is saved.
 */

public abstract class Document extends Resource {
	
	
	//Resource fields	
	//
	protected List<Link> links;
	protected String remoteId;//id field in the resource representation. Mobile apps normally use ReSTful URL to identify a resource.Not this.
	protected String fullName;
	protected String wikiName;//wiki in page element
	protected String spaceName;//space in page element
	protected String name; // name in page element (same)
	protected String title;
	protected String parentFullName;//parent in resoruce repr.
	protected String parentId;
	protected String xwikiRelativeUrl;
	protected String xwikiAbsoluteUrl;
	protected List<String> translations;
	protected String defalutTranslation;
	protected String syntax;	
    protected String language;    
    protected String version;    
    protected int majorVersion;
    protected int minorVersion;    
    protected String created; //Date string  //TODO: convert to Type Date. Or introduce another var to hold Date obj. 
    protected String creator;  //user string  
    protected String modified;  //Date string  
    protected String modifier;  //user string  
    protected String content;
	
	
    //other fields
    protected DocumentReference docRef;
    protected boolean offlineMode;
    
    public Document(String wikiName, String spaceName, String pageName){
    	docRef=new DocumentReference(wikiName, spaceName, pageName);
    	this.name=pageName;
    	this.wikiName=wikiName;
    	this.spaceName=spaceName;
    }
    
    /**
	 * @return
	 */
	public DocumentReference getDocumentReference() {
		return docRef;
	}
	
	/**
	 * @param 
	 */
	public void setDocumentReference(DocumentReference docRef) {
		this.docRef = docRef;
	}




	/**
     * if in offline mode the create(), update ... methods will use the save method to save a local copy.
     * The delete method will mark the document for deletion.
     * The sync service will take the responsibilities after internet connection is available.     * 
     * @param val
     */
	public void setOfflineMode(boolean val){
    	this.offlineMode=val;
    }
	public boolean isOfflineMode() {
		return offlineMode;
	}
	
//	/**
//	 * valid for only  Documents retreived from server.
//	 * @return
//	 */
//    public boolean isCachedCopy(){
//    	return offlineMode;
//    }
	    
	
	/** moved away to SimpleDocument. If XWiki introduces CompoundDocuments with Compound objects defining them here might lead to probs
	public abstract XObject getObject(String key);
	public abstract void setObject(String key, XObject object);
	public abstract void addObject(XObject obj);
	public abstract void deleteObject(String key);
	**/
	//TODO: Implement similar methods for Comments...
	
	//TODO: public abstract InputStream getAttachmentData(Attachment a);
	//TODO: public abstract ... setAttachmentData(byte[]);
	
	//resource value getter setters
	//-----------------------------
	
	/**
	 * Gets the value of the id property.	  
	 * @return possible object is {@link String }
	 */
	public String getRemoteId() {
		return remoteId;		
	}

	/**
	 * Sets the value of the id property.	 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setRemoteId(String value) {
		this.remoteId = value;
	}

	/**
	 * Gets the value of the fullName property.	 * 
	 * @return possible object is {@link String }
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the value of the fullName property.	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setFullName(String value) {
		this.fullName = value;
	}

	/**
	 * Gets the value of the wiki property. 
	 * @return possible object is {@link String }
	 */
	public String getWikiName() {
		return wikiName;
	}

	/**
	 * Sets the value of the wiki property.
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setWikiName(String value) {
		this.wikiName = value;
	}

	/**
	 * Gets the value of the space property.
	 * @return possible object is {@link String }
	 */
	public String getSpaceName() {
		return spaceName;
	}

	/**
	 * Sets the value of the space property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setSpaceName(String value) {
		this.spaceName = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getSimpleName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setSimpleName(String value) {
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
	public String getParentFullName() {
		return parentFullName;
	}

	/**
	 * Sets the value of the parent property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setParentFullName(String value) {
		this.parentFullName = value;
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

	public String getDefalutTranslation() {
		return defalutTranslation;
	}
	public void setDefalutTranslation(String defalutTranslation) {
		this.defalutTranslation = defalutTranslation;
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
	
	public String getLanguage()
    {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setLanguage(String value)
    {
        this.language = value;

    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setVersion(String value)
    {
        this.version = value;
    }

    /**
     * Gets the value of the majorVersion property.
     */
    public int getMajorVersion()
    {
        return majorVersion;
    }

    /**
     * Sets the value of the majorVersion property.
     */
    public void setMajorVersion(int value)
    {
        this.majorVersion = value;
    }

    /**
     * Gets the value of the minorVersion property.
     */
    public int getMinorVersion()
    {
        return minorVersion;
    }

    /**
     * Sets the value of the minorVersion property.
     */
    public void setMinorVersion(int value)
    {
        this.minorVersion = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return possible object is {@link String }
     */
    public String getCreated()
    {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCreated(String value)
    {
        this.created = value;
    }

    /**
     * Gets the value of the creator property.
     * 
     * @return possible object is {@link String }
     */
    public String getCreator()
    {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCreator(String value)
    {
        this.creator = value;
    }

    /**
     * Gets the value of the modified property.
     * 
     * @return possible object is {@link String }
     */
    public String getModified()
    {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setModified(String value)
    {
        this.modified = value;
    }

    /**
     * Gets the value of the modifier property.
     * 
     * @return possible object is {@link String }
     */
    public String getModifier()
    {
        return modifier;
    }

    /**
     * Sets the value of the modifier property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setModifier(String value)
    {
        this.modifier = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return possible object is {@link String }
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setContent(String value)
    {
        this.content = value;
    }
	
}
