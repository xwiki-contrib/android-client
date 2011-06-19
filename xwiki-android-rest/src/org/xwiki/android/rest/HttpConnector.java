package org.xwiki.android.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpConnector {

	private URI requestUri;

	public String getResponse(String Uri) {
		BufferedReader in = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		String responseText = new String();
		HttpResponse response;

		try {
			requestUri = new URI(Uri);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setURI(requestUri);
		Log.d("Request URL", Uri);
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
	
	
	public String deleteRequest(String Uri) {
		BufferedReader in = null;
		HttpClient client = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete();
		String responseText = new String();
		HttpResponse response;

		try {
			requestUri = new URI(Uri);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		delete.setURI(requestUri);

		try {
			response = client.execute(delete);
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
