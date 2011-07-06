package org.xwiki.android.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class HttpConnector {

	private URI requestUri;
	
	private String username,password;
	
	private boolean isSecured= false;
	
	public void setAuthenticaion(String username, String password){
		this.username = username;
		this.password = password;
		isSecured = true;
	}
	
	public String getResponse(String Uri){
		if(isSecured){
			return getResponseWithAuth(Uri, username, password);
		}
		else{
			return getResponseWithoutAuth(Uri);
		}
	}

	private String getResponseWithAuth(String Uri, String username, String password) {
		BufferedReader in = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		String responseText = new String();
		HttpResponse response;

		try {		
			requestUri = new URI(Uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

			@Override
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				AuthState authState = (AuthState) context
						.getAttribute(ClientContext.TARGET_AUTH_STATE);
				CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context
						.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

				if (authState.getAuthScheme() == null) {
					AuthScope authScope = new AuthScope(
							targetHost.getHostName(), targetHost.getPort());
					Credentials creds = credsProvider.getCredentials(authScope);
					if (creds != null) {
						authState.setAuthScheme(new BasicScheme());
						authState.setCredentials(creds);
					}
				}
			}

		};

		client.addRequestInterceptor(preemptiveAuth, 0);

		Credentials defaultcreds = new UsernamePasswordCredentials(username,
				password);
		client.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1, AuthScope.ANY_REALM), defaultcreds);

		request.setURI(requestUri);
		Log.d("Request URL", Uri);
		try {

			response = client.execute(request);
			Log.d("Response status", response.getStatusLine().toString());
			
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

	private String getResponseWithoutAuth(String Uri) {
		BufferedReader in = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		String responseText = new String();
		HttpResponse response;

		try {
			requestUri = new URI(Uri);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		request.setURI(requestUri);
		Log.d("Request URL", Uri);
		try {
			response = client.execute(request);
			Log.d("Response status", response.getStatusLine().toString());

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

	public int checkLogin(String username, String password, String Url) {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		HttpResponse response;
		String Uri;
		int responseCode = 0;

		Uri = "http://" + Url + "/xwiki/rest/";

		try {
			requestUri = new URI(Uri);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// Setting preemtiveAuth manually since org.apache.http does not support it
		HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

			@Override
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				AuthState authState = (AuthState) context
						.getAttribute(ClientContext.TARGET_AUTH_STATE);
				CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context
						.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

				if (authState.getAuthScheme() == null) {
					AuthScope authScope = new AuthScope(
							targetHost.getHostName(), targetHost.getPort());
					Credentials creds = credsProvider.getCredentials(authScope);
					if (creds != null) {
						authState.setAuthScheme(new BasicScheme());
						authState.setCredentials(creds);
					}
				}
			}
		};

		client.addRequestInterceptor(preemptiveAuth, 0);

		Credentials defaultcreds = new UsernamePasswordCredentials(username,
				password);
		client.getCredentialsProvider().setCredentials(
				new AuthScope(null, -1, AuthScope.ANY_REALM), defaultcreds);

		request.setURI(requestUri);
		Log.d("Request URL", Uri);
		try {

			response = client.execute(request);
			Log.d("Response status", response.getStatusLine().toString());
			
			String[] responseParts = response.getStatusLine().toString().split(" ");
			responseCode = Integer.parseInt(responseParts[1]);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.d("response code", String.valueOf(responseCode));
		return responseCode;
	}
}
