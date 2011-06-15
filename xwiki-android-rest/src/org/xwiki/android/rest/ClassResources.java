package org.xwiki.android.rest;

public class ClassResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    // wikis/{wikiName}/classes[?start=offset&number=n]

    public ClassResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
    }

    public String getWikiClasses()
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }
    
    public String getWikiClasses(String classname)
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes/" + classname + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }
}
