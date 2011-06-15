package org.xwiki.android.rest;

public class CommentResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public CommentResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public String getPageComments()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageComments(String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments/" + commentId + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageCommentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageCommentsInHistory(String version, String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments/" + commentId + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

}
