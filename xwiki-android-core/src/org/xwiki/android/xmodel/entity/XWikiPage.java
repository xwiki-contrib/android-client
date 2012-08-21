package org.xwiki.android.xmodel.entity;

import java.util.Date;
import org.xwiki.android.rest.reference.DocumentReference;

/**
 * Contains the properties of a Page +Additional methods for Document.
 * 
 * @author xwiki dev: not saved to DB. Only the DocumentReference is saved.
 */

public abstract class XWikiPage extends XWikiPageSummary
{

    protected String language;

    protected String version;

    protected int majorVersion;

    protected int minorVersion;

    protected Date created; // Date created. From date string in page.

    protected String creator; // user string

    protected Date modified; // Date string

    protected String modifier; // user string

    protected String content;

    
    

   
    /**
     * Create page with given params.Also page 'Title' is set to pageName by default.
     * @param wikiName
     * @param spaceName
     * @param pageName
     */
    public XWikiPage(String wikiName, String spaceName, String pageName)
    {       
        this.name = pageName;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.title=pageName;
    }

    /**
     * @return
     */
    public DocumentReference getDocumentReference()
    {
        DocumentReference docRef = new DocumentReference(wikiName, spaceName, name);
        docRef.setVersion(version);
        docRef.setLanguage(language);
        return docRef;
    }

    

   

    // /**
    // * valid for only Documents retreived from server.
    // * @return
    // */
    // public boolean isCachedCopy(){
    // return offlineMode;
    // }

   

    // resource value getter setters
    // -----------------------------

   
    
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
    public Date getCreated()
    {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCreated(Date date)
    {
        this.created = date;
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
    public Date getModified()
    {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setModified(Date d)
    {
        this.modified = d;
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
