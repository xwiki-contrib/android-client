package org.xwiki.android.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Search {

	private final String PAGE_SEARCH_REQUEST = "/xwiki/rest/wikis/xwiki/search?scope=name&number=10&media=json&q=";

	private String domain;
	private String port;
	private String keyword;

	public Search(String domain, int port) {
		this.domain = domain;
		this.port = Integer.toString(port);
	}

	public void setKeyword(String keyword) {

		try {
			this.keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String doPageSearch() {

		BufferedReader in = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		String responseText = new String();
		HttpResponse response;

		try {
			// request.setURI(new URI(
			// "http://10.0.2.2:8080/xwiki/rest/wikis/xwiki/search?scope=name&number=10&media=xml&q=is%20great"));

			request.setURI(new URI("http://" + domain + ":" + port
					+ PAGE_SEARCH_REQUEST + keyword));

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			responseText = sb.toString();
			Log.d("Response", "response: " + responseText);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseText;
	}
}
