package org.xwiki.android.rest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Wikis;

public class WikiResources extends HttpConnector
{

    private final String WIKI_URL = "/xwiki/rest/wikis";

    private String URLprefix;

    public WikiResources(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    public Wikis getWikis()
    {

        String Uri = "http://" + URLprefix + WIKI_URL;

        return buildWikis(super.getResponse(Uri));
    }

    // decode xml content to Wikis element
    private Wikis buildWikis(String content)
    {
        Wikis wikis = new Wikis();

        Serializer serializer = new Persister();

        try {
            wikis = serializer.read(Wikis.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return wikis;
    }
}
