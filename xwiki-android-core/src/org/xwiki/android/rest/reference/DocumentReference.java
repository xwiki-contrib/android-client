package org.xwiki.android.rest.reference;

import org.xwiki.android.xmodel.entity.Document;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class DocumentReference extends EntityReference<Document>
{
    @DatabaseField
    protected String wikiName;

    @DatabaseField
    protected String spaceName;

    @DatabaseField
    protected String pageName;

    public DocumentReference()
    {
        // for ORMLite
    }

    public DocumentReference(String wikiName, String spaceName, String pageName)
    {
        super();
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    /**
     * @return the wikiName
     */
    public String getWikiName()
    {
        return wikiName;
    }

    /**
     * @return the spaceName
     */
    public String getSpaceName()
    {
        return spaceName;
    }

    /**
     * @return the pageName
     */
    public String getPageName()
    {
        return pageName;
    }

    /**
     * @param wikiName the wikiName to set
     */
    public void setWikiName(String wikiName)
    {
        this.wikiName = wikiName;
    }

    /**
     * @param spaceName the spaceName to set
     */
    public void setSpaceName(String spaceName)
    {
        this.spaceName = spaceName;
    }

    /**
     * @param pageName the pageName to set
     */
    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    @Override
    public String getURL()
    {
        return getAuthority() + "/xwiki/xmlrpc/wikis/" + wikiName + "/spaces" + spaceName + "/pages" + pageName;
    }

}
