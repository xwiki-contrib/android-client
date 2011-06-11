package org.xwiki.android.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class WikiResources extends HttpConnector {

	private final String WIKI_URL = "/xwiki/rest/wikis?media=json"; 
	private final String JSON_ARRAY_IDENTIFIER = "wikis";
	
	private String URLprefix;
	
	public WikiResources(String URLprefix) {
		this.URLprefix = URLprefix;
	}
	
	public String getWikis(){
		
		String Uri = "http://" + URLprefix + WIKI_URL;

		return super.getResponse(Uri);
	}
	
	// Temporary method to decode JSON reply
	public String decodeWikiResponse(String response) {

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
