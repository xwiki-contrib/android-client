package org.xwiki.android.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PageResources extends HttpConnector {

	private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";
	private final String PAGE_URL_SUFFIX = "/pages?media=json";
	private final String JSON_URL_SUFFIX = "?media=json";
	private final String JSON_ARRAY_IDENTIFIER = "pageSummaries";

	private String URLprefix;
	private String wikiName;
	private String spaceName;

	public PageResources(String URLprefix, String wikiName, String spaceName) {
		this.URLprefix = URLprefix;
		this.wikiName = wikiName;
		this.spaceName = spaceName;
	}

	//get all pages
	public String getAllPages() {
		String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName
				+ "/spaces/" + spaceName + PAGE_URL_SUFFIX;

		return super.getResponse(Uri);
	}
	
	//get page
	public String getPage(String pageName) {
		String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName
				+ "/spaces/" + spaceName + "/pages/" + pageName + JSON_URL_SUFFIX ;
		
		return super.getResponse(Uri);
	}
	
	//Delete page
	public String deletePage(String pageName){
		String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName
		+ "/spaces/" + spaceName + "/pages/" + pageName + JSON_URL_SUFFIX ;
		
		return super.deleteRequest(Uri); 
	}
	
	//Get page history on version
	public String getPageHistoryOnVersion(String pageName, String version) {
		String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName
				+ "/spaces/" + spaceName + "/pages/" + pageName + "/history/" + version + JSON_URL_SUFFIX;
		
		return super.getResponse(Uri);
	}
	
	//Get page children
	public String getPageChildren(String pageName) {
		String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName
				+ "/spaces/" + spaceName + "/pages/" + pageName + "/children"  + JSON_URL_SUFFIX;
		
		return super.getResponse(Uri);
	}

	// Temporary method to decode JSON reply
	public String decodeAllPagesResponse(String response) {

		String wikiResultText = "";
		try {
			JSONObject jsonobject = new JSONObject(response);
			JSONArray dataArray = jsonobject
					.getJSONArray(JSON_ARRAY_IDENTIFIER);

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
	
	// Temporary method to decode JSON reply
	public String decodePageResponse(String response,String key ) {

		String wikiResultText = "";
		try {
			JSONObject jsonobject = new JSONObject(response);
			JSONArray dataArray = jsonobject
					.getJSONArray(key);

			Log.d("JSON", "JSON array built");

			Log.d("JSON", "Number of entrees in array: " + dataArray.length());

			for (int i = 0; i < dataArray.length(); i++) {
				if (!dataArray.isNull(i)) {
					JSONObject item = dataArray.getJSONObject(i);

					if (item.has("pageId")) {
						String id = item.getString("pageId");
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
