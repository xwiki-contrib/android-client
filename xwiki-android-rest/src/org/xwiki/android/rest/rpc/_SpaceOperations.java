package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Spaces;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
;

class _SpaceOperations implements SpaceOperations
{
    
    /**
     * Path provided from XWiki RESTful API
     */
    private final String SPACE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * Path provided from XWiki RESTful API for spaces
     */
    private final String SPACE_URL_SUFFIX = "/spaces";

    /**
     * URL of the XWiki domain
     */
    private String uRLprefix;

    /**
     * Name of Wiki for acquiring pages
     */
    private String wikiName;
    private RestClient rpc;


    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param rpc 
     */
    _SpaceOperations(String URLprefix, String wikiName, RestClient rpc)
    {
        this.uRLprefix = URLprefix;
        this.wikiName = wikiName;
        this.rpc=rpc;
    }
    @Override
    public Spaces getSpaces() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
