package org.xwiki.android.rest;

import org.xwiki.android.resources.Translations;

import com.google.gson.Gson;

public class TranslationResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public TranslationResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public Translations getAllTranslations()
    {

        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations" + JSON_URL_SUFFIX;

        return decodeTranslations(super.getResponse(Uri));
    }

    private Translations decodeTranslations(String content)
    {
        Gson gson = new Gson();

        Translations translations = new Translations();
        translations = gson.fromJson(content, Translations.class);
        return translations;
    }
}
