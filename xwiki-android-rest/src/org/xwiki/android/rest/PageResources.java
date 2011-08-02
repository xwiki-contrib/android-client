package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;

public class PageResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String PAGE_URL_SUFFIX = "/pages";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    public PageResources(String URLprefix, String wikiName, String spaceName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
    }

    // get all pages [ok]
    public Pages getAllPages()
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + PAGE_URL_SUFFIX;

        return buildPages(super.getResponse(Uri));
    }

    // get page [ok]
    public Page getPage(String pageName)
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName;

        return buildPage(super.getResponse(Uri));
    }

    // add or modify page
    public String addPage(Page page)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page.getName();

        
        return super.putRequest(Uri, buildXmlPage(page));
    }

    // Delete page
    public String deletePage(String pageName)
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName;

        if (super.getIsSecured()) {
            return super.deleteRequest(Uri);
        } else {
            return "No authenticaiton details found";
        }

    }

    // Get page history on version
    public Page getPageHistoryOnVersion(String pageName, String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version;

        return buildPage(super.getResponse(Uri));
    }

    // Get page children
    public Pages getPageChildren(String pageName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/children";

        return buildPages(super.getResponse(Uri));
    }

    // Get page translation
    public Page getPageTranslation(String pageName, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language;

        return buildPage(super.getResponse(Uri));
    }

    // add page translation
    public String addPageTranslation(Page page, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page
                + "/translations/" + language;

        String content = page.toString();

        return super.putRequest(Uri, content);
    }

    // Delete page translation
    public String deletePageTranslation(String pageName, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language;

        return super.deleteRequest(Uri);
    }

    // wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/translations/{lang}/history/{version}
    public Page getPageTranslation(String pageName, String language, String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + "/history/" + version;

        return buildPage(super.getResponse(Uri));
    }

    // decode xml content to Pages element
    private Pages buildPages(String content)
    {
        Pages pages = new Pages();

        Serializer serializer = new Persister();

        try {
            pages = serializer.read(Pages.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return pages;
    }

    // build xml from Comment object
    private String buildXmlPages(Pages pages)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(pages, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // decode xml content to Comment element
    private Page buildPage(String content)
    {
        Page page = new Page();

        Serializer serializer = new Persister();

        try {
            page = serializer.read(Page.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return page;
    }

    // build xml from Comment object
    private String buildXmlPage(Page page)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(page, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}
