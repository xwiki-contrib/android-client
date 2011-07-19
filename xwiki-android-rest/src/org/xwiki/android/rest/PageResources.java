package org.xwiki.android.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PageResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String PAGE_URL_SUFFIX = "/pages?media=json";

    private final String JSON_URL_SUFFIX = "?media=json";

    private final String JSON_ARRAY_IDENTIFIER = "pageSummaries";

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

        return decodePages(super.getResponse(Uri));
    }

    // get page [ok]
    public Page getPage(String pageName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + JSON_URL_SUFFIX;

        return decodePage(super.getResponse(Uri));
    }
    
    // add or modify page 
    public String addPage(Page page)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page.getName()
                + JSON_URL_SUFFIX;

        String content = page.toString();
        
        return super.putRequest(Uri, content);
    }

    // Delete page
    public String deletePage(String pageName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + JSON_URL_SUFFIX;

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
                + "/history/" + version + JSON_URL_SUFFIX;

        return decodePage(super.getResponse(Uri));
    }

    // Get page children
    public Pages getPageChildren(String pageName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/children" + JSON_URL_SUFFIX;

        return decodePages(super.getResponse(Uri));
    }

    // Get page translation
    public Page getPageTranslation(String pageName, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + JSON_URL_SUFFIX;

        return decodePage(super.getResponse(Uri));
    }
    
    // add page translation
    public String addPageTranslation(Page page, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page
                + "/translations/" + language + JSON_URL_SUFFIX;
        
        String content = page.toString();

        return super.putRequest(Uri, content);
    }
    
    // Delete page translation
    public String deletePageTranslation(String pageName, String language)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + JSON_URL_SUFFIX;

        return super.deleteRequest(Uri);
    }

    // wikis/{wikiName}/spaces/{spaceName}/pages/{pageName}/translations/{lang}/history/{version}
    public Page getPageTranslation(String pageName, String language, String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + "/history/" + version + JSON_URL_SUFFIX;

        return decodePage(super.getResponse(Uri));
    }

    // decode json content to Pages element
    private Pages decodePages(String content)
    {
        Gson gson = new Gson();

        Pages pages = new Pages();
        pages = gson.fromJson(content, Pages.class);
        return pages;
    }

    // decode json content to Page element
    private Page decodePage(String content)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // gsonBuilder.registerTypeAdapter(sun.util.calendar.ZoneInfo.class, new TimeZoneDeserializer());
        // gsonBuilder.registerTypeAdapter(ZoneInfo.class, new TimeZoneDeserializer());
        // Gson gson = gsonBuilder.create();

        Gson gson = new Gson();
        Page page = new Page();
        page = gson.fromJson(content, Page.class);

        // Manually deserialize Calendar

        try {
            JSONObject jsonobject = new JSONObject(content);
            String created = jsonobject.getString("created");
            page.created1 = convertToCalendar(created);
            String modified = jsonobject.getString("modified");
            page.modified1 = convertToCalendar(modified);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return page;
    }

    private GregorianCalendar convertToCalendar(String jsonText)
    {

        GregorianCalendar greg = new GregorianCalendar();
        ArrayList<String> allData = new ArrayList<String>();

        String first = jsonText.substring(28, 98);
        int x = jsonText.indexOf("]");
        first += jsonText.substring(x + 1, jsonText.length() - 1);
        String[] firstdata = first.split(",");
        for (int i = 0; i < firstdata.length; i++) {
            String[] seperate = firstdata[i].split("=");
            allData.add(seperate[1]);
        }

        // Get ZoneInfo
        int w = jsonText.indexOf("id");
        String second = jsonText.substring(w, x);
        String[] seconddata = second.split(",");
        for (int i = 0; i < seconddata.length; i++) {
            String[] seperate = seconddata[i].split("=");
            allData.add(seperate[1]);
        }
        Log.d("allData", allData.toString());

        // ZoneInfo zi;
        // Log.d("zi info", allData.get(23) + " and " + allData.get(24));
        // zi= new ZoneInfo();
        // zi.setID(allData.get(23));
        // zi.setRawOffset(Integer.parseInt(allData.get(24)));
        // zi = new ZoneInfo(allData.get(23), Integer.parseInt(allData.get(24)));
        // greg.setTimeZone(zi);
        greg.setTimeInMillis(Long.parseLong(allData.get(0)));
        greg.setFirstDayOfWeek(Integer.parseInt(allData.get(4)));
        greg.setMinimalDaysInFirstWeek(Integer.parseInt(allData.get(5)));
        return greg;
    }

}
