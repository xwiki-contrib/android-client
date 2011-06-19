package org.xwiki.android.rest;

import org.xwiki.android.resources.Wikis;

import com.google.gson.Gson;

public class WikiResources extends HttpConnector
{

    private final String WIKI_URL = "/xwiki/rest/wikis?media=json";

    private String URLprefix;

    public WikiResources(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    public Wikis getWikis()
    {

        String Uri = "http://" + URLprefix + WIKI_URL;

        return decode(super.getResponse(Uri));
    }

    // decode json content to Wikis
    private Wikis decode(String content)
    {
        Gson gson = new Gson();

        Wikis wikis = new Wikis();
        wikis = gson.fromJson(content, Wikis.class);
        return wikis;
    }
}
