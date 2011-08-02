package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;

public class TagResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

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

    // get all tags in page or wiki
    public Tags getTags()
    {

        String Uri;

        if (tagType == 0) {
            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags/";
        } else if (tagType == 1) {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags";
        } else {
            Uri = "";
        }

        return buildTags(super.getResponse(Uri));
    }

    public String addTag(Tag tag)
    {

        String Uri;

        if (tagType == 0) {

            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags";

            Tags temp_tags;

            temp_tags = buildTags(super.getResponse(Uri));

            temp_tags.getTags().add(tag);

            return super.putRequest(Uri, buildXml(temp_tags));

        } else if (tagType == 1) {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags";

            Tags temp_tags;

            temp_tags = buildTags(super.getResponse(Uri));

            temp_tags.getTags().add(tag);

            return super.putRequest(Uri, buildXml(temp_tags));
        } else {
            return null;
        }

    }

    // decode xml content to Tags element
    private Tags buildTags(String content)
    {
        Tags tags = new Tags();

        Serializer serializer = new Persister();

        try {
            tags = serializer.read(Tags.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return tags;
    }

    // build xml from Tags object
    private String buildXml(Tags tags)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(tags, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }
}
