package org.xwiki.android.rest;

import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Wikis;

import com.google.gson.Gson;

public class HistoryResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public HistoryResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public History getPageHistory()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history" + JSON_URL_SUFFIX;

        return decodeHistory(super.getResponse(Uri));
    }

    public History getPageHistory(String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + "/history" + JSON_URL_SUFFIX;

        return decodeHistory(super.getResponse(Uri));
    }

    // decode json content to History element
    private History decodeHistory(String content)
    {
        Gson gson = new Gson();

        History history = new History();
        history = gson.fromJson(content, History.class);
        return history;
    }

}
