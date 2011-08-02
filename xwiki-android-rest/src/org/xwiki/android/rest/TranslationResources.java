package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Translations;

public class TranslationResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

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
                + "/translations";

        return buildTranslations(super.getResponse(Uri));
    }

    // decode xml content to Translations element
    private Translations buildTranslations(String content)
    {
        Translations translations = new Translations();

        Serializer serializer = new Persister();

        try {
            translations = serializer.read(Translations.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return translations;
    }

    // build xml from Translations object
    private String buildXmlTranslations(Translations translations)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(translations, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }
}
