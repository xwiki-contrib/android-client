package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

class _TagOperations implements TagOperations
{

    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring tags
     */
    private String wikiName;

    /**
     * Name of space for acquiring tags
     */
    private String spaceName;

    /**
     * Name of page for acquiring tags
     */
    private String pageName;

    /**
     * Type of the tag.
     * <p>
     * 0= Wiki tag 1=Page tag </>
     */
    private int tagType;

    private RestClient rc;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     */
    _TagOperations(String URLprefix, String wikiName, RestClient rc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        tagType = 0;
        this.rc = rc;
    }

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     */
    _TagOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        tagType = 1;
        this.rc = rc;
    }

    @Override
    public Tags getTags() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addTag(Tag tag) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setTags(Tags tags) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
