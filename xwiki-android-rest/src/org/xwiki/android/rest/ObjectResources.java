package org.xwiki.android.rest;

public class ObjectResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    

    public ObjectResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }
    
    public String getAllObjects(){
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/objects" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
        
    }
}
