package org.xwiki.android.rest;

public class TagResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    private int tagType;

    public TagResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        tagType = 0;
    }

    public TagResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        tagType = 1;
    }

    // get all tags in page
    public String getTags()
    {

        String Uri;

        if (tagType == 0) {
            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags/" + JSON_URL_SUFFIX;
        } else if (tagType == 1) {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags" + JSON_URL_SUFFIX;
        } else {
            Uri = "";
        }

        return super.getResponse(Uri);
    }
}
