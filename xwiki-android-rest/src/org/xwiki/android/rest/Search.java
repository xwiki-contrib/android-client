package org.xwiki.android.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Search extends HttpConnector
{

    private final String SEARCH_REQUEST_PREFIX = "/xwiki/rest/wikis/";

    private final String WIKI_SEARCH_REQUEST_SUFFIX = "/search?scope=name&number=10&media=json&q=";

    private final String JSON_ARRAY_IDENTIFIER = "searchResults";

    private String URLprefix;

    public Search(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    public String doSpacesSearch(String wikiName, String spaceName, String keyword)
    {
        String Uri =
            "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + "/spaces/" + spaceName
                + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return super.getResponse(Uri);
    }

    public String doWikiSearch(String wikiName, String keyword)
    {
        String Uri = "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return super.getResponse(Uri);
    }

    // Temporary method to decode JSON reply
    public String decodeSearchResponse(String response)
    {

        String searchResultText = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            JSONArray dataArray = jsonobject.getJSONArray(JSON_ARRAY_IDENTIFIER);

            Log.d("JSON", "JSON array built");

            Log.d("JSON", "Number of entrees in array: " + dataArray.length());

            for (int i = 0; i < dataArray.length(); i++) {
                if (!dataArray.isNull(i)) {
                    JSONObject item = dataArray.getJSONObject(i);

                    if (item.has("id")) {
                        String id = item.getString("id");
                        Log.d("JSON Array", "id= " + id);
                        searchResultText += ("\n" + id);
                    }
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("JSON", "Error in JSON object or Array");
        }

        return searchResultText;

    }
}
