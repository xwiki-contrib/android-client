package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Spaces;

public class SpaceResources extends HttpConnector
{

    private final String SPACE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String SPACE_URL_SUFFIX = "/spaces";

    private String URLprefix;

    private String wikiName;

    public SpaceResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
    }

    public Spaces getSpaces()
    {

        String Uri = "http://" + URLprefix + SPACE_URL_PREFIX + wikiName + SPACE_URL_SUFFIX;

        return buildSpaces(super.getResponse(Uri));
    }
    
    // decode xml content to Spaces element
    private Spaces buildSpaces(String content)
    {
        Spaces spaces = new Spaces();

        Serializer serializer = new Persister();

        try {
            spaces = serializer.read(Spaces.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return spaces;
    }

    // build xml from Spaces object
    private String buildXmlSpaces(Spaces spaces)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(spaces, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

}
