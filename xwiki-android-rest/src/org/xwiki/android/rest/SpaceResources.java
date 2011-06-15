package org.xwiki.android.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SpaceResources extends HttpConnector
{

    private final String SPACE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String SPACE_URL_SUFFIX = "/spaces?media=json";

    private final String JSON_ARRAY_IDENTIFIER = "spaces";

    private String URLprefix;

    private String wikiName;

    public SpaceResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
    }

    public String getSpaces()
    {

        String Uri = "http://" + URLprefix + SPACE_URL_PREFIX + wikiName + SPACE_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // Temporary method to decode JSON reply
    public String decodeSpaceResponse(String response)
    {

        String wikiResultText = "";
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
                        wikiResultText += ("\n" + id);
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("JSON", "Error in JSON object or Array");
        }

        return wikiResultText;

    }
}
