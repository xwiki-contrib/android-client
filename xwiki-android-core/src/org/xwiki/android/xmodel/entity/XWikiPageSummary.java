package org.xwiki.android.xmodel.entity;

import java.util.List;

import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.reference.Link;

public class XWikiPageSummary extends XWikiResource
{

    protected List<Link> links;
    protected String id;
    protected String fullName;
    protected String wikiName;
    protected String spaceName;
    protected String name;
    protected String title;
    protected String parentFullName;
    protected String parentId;
    protected String xwikiRelativeUrl;
    protected String xwikiAbsoluteUrl;
    protected List<String> translations;
    protected String defalutTranslation;
    protected String syntax;

    public XWikiPageSummary()
    {
        super();
    }
    
    /**
     * 
     * @return ref filled with wiki,space,name coordinates
     */
    public DocumentReference getDocumentReference()
    {
        DocumentReference docRef = new DocumentReference(wikiName, spaceName, name);        
        return docRef;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link String }
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setId(String value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the fullName property. *
     * 
     * @return possible object is {@link String }
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Sets the value of the fullName property. *
     * 
     * @param value allowed object is {@link String }
     */
    public void setFullName(String value)
    {
        this.fullName = value;
    }

    /**
     * Gets the value of the wiki property.
     * 
     * @return possible object is {@link String }
     */
    public String getWikiName()
    {
        return wikiName;
    }

    /**
     * Sets the value of the wiki property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setWikiName(String value)
    {
        this.wikiName = value;
    }

    /**
     * Gets the value of the space property.
     * 
     * @return possible object is {@link String }
     */
    public String getSpaceName()
    {
        return spaceName;
    }

    /**
     * Sets the value of the space property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setSpaceName(String value)
    {
        this.spaceName = value;
    }

    /**
     * get Page Name. 
     * @return the value of the name property in the Rest model "Page" element..
     */
    public String getPageName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setPageName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return possible object is {@link String }
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return possible object is {@link String }
     */
    public String getParentFullName()
    {
        return parentFullName;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setParentFullName(String parent)
    {
        this.parentFullName = parent;
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return possible object is {@link String }
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setParentId(String value)
    {
        this.parentId = value;
    }

    /**
     * Gets the value of the xwikiRelativeUrl property.
     * 
     * @return possible object is {@link String }
     */
    public String getXwikiRelativeUrl()
    {
        return xwikiRelativeUrl;
    }

    /**
     * Sets the value of the xwikiRelativeUrl property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setXwikiRelativeUrl(String value)
    {
        this.xwikiRelativeUrl = value;
    }

    /**
     * Gets the value of the xwikiAbsoluteUrl property.
     * 
     * @return possible object is {@link String }
     */
    public String getXwikiAbsoluteUrl()
    {
        return xwikiAbsoluteUrl;
    }

    /**
     * Sets the value of the xwikiAbsoluteUrl property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setXwikiAbsoluteUrl(String value)
    {
        this.xwikiAbsoluteUrl = value;
    }

    /**
     * Gets the value of the translations property.
     * 
     * @return possible object is {@link Translations }
     */
    public List<String> getTranslations()
    {
        return translations;
    }

    /**
     * Sets the value of the translations property.
     * 
     * @param value allowed object is {@link Translations }
     */
    public void setTranslations(List<String> translations)
    {
        this.translations = translations;
    }

    public String getDefalutTranslation()
    {
        return defalutTranslation;
    }

    public void setDefalutTranslation(String defalutTranslation)
    {
        this.defalutTranslation = defalutTranslation;
    }

    /**
     * Gets the value of the syntax property.
     * 
     * @return possible object is {@link String }
     */
    public String getSyntax()
    {
        return syntax;
    }

    /**
     * Sets the value of the syntax property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setSyntax(String value)
    {
        this.syntax = value;
    }

}
