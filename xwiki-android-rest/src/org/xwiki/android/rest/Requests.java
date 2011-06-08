package org.xwiki.android.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Requests {

	private String domain;
	private int port;

	public Requests(String domain, int port) {
		this.domain = domain;
		this.port = port;
	}

	// Method for search
	public String requestSearch(String keyword) {

		Search search = new Search(domain, port);
		search.setKeyword(keyword);

		return search.doPageSearch();
	}

	// Temporary method to decode JSON reply
	public String decodeSearchResponse(String response) {

		String searchResultText="";
		try {
			JSONObject jsonobject = new JSONObject(response);
			JSONArray dataArray = jsonobject.getJSONArray("searchResults");

			Log.d("JSON", "JSON array built");

			Log.d("JSON", "Number of entrees in array: " + dataArray.length());

			for (int i = 0; i < dataArray.length(); i++) {
				if (!dataArray.isNull(i)) {
					JSONObject item = dataArray.getJSONObject(i);

					if (item.has("id")) {
						String id = item.getString("id");
						Log.d("JSON Array", "id= " + id);
						searchResultText += ("\n" + id );
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

	// Method for requesting pages (to be implemented)
	public void requestPage() {

	}

}
