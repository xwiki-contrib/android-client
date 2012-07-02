/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

/**
 * Parent class of other XWiki Android REST classes. This class initialize a HTTP connection with XWiki server via
 * Android device connection and returns response status, response content according to the request. Since
 * org.apache.http does not support preemtiveAuth, it is provided with HttpRequestInterceptor.
 */
public class HttpConnector
{
	/*
	 * public static constants
	 */
	/*
	 * custom response code for client connection timeout
	 */
	 public static final int RESP_CODE_CLIENT_CON_TIMEOUT=21408;
	 
    /**
     * URI of the Remote XWiki instance
     */
    private URI requestUri;

    /**
     * User credential details
     */
    private String username, password;

    /**
     * value for storing whether the http connection should or should not have user credentials
     */
    private boolean isSecured = false;

    /**
     * For creating manual preemptive auth with the XWiki instance
     */
    private HttpRequestInterceptor preemptiveAuth;

    /**
     * For executing http connection
     */
    private DefaultHttpClient client;

    /**
     * For capturing response of the HTTP execution
     */
    private HttpResponse response;

    /**
     * Sets user credentials of the XWiki user
     * 
     * @param username username of the XWiki user account
     * @param password password of the XWiki user account
     */
    public void setAuthenticaion(String username, String password)
    {
        this.username = username;
        this.password = password;
        isSecured = true;
    }

    /**
     * @return gets whether connection should add user credentials
     */
    public boolean getIsSecured()
    {
        return isSecured;
    }

    /**
     * Perform HTTP Get method execution and return the response as a String
     * 
     * @param Uri URL of XWiki RESTful API
     * @return Response data of the HTTP connection as a String
     */
    public String getResponse(String Uri)
    {
        initialize();

        BufferedReader in = null;
        HttpGet request = new HttpGet();
        String responseText = new String();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);
        try {

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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

    /**
     * Perform HTTP Delete method execution and return its status
     * 
     * @param Uri URL of XWiki RESTful API
     * @return status of the HTTP method execution
     */
    public String deleteRequest(String Uri)
    {
        initialize();

        HttpDelete request = new HttpDelete();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);

        try {

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());
            return response.getStatusLine().toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * Checks whether user credentials are valid in the provided remote XWiki
     * 
     * @param username username of the XWiki user account
     * @param password password of the XWiki user account
     * @param Url URL of the XWiki instance
     * @return HTTP response code of the connection
     * 		   or 
     * 		   21408(RESP_CODE_CLIENT_CON_TIMEOUT) when client connection timed out, 
     *  	   
     */
   
    public int checkLogin(String username, String password, String Url)
    {

        initialize();

        HttpGet request = new HttpGet();

        String Uri;
        int responseCode = 0;

        Uri = "http://" + Url + "/xwiki/rest/";

        try {
            requestUri = new URI(Uri);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(username!=""&&username!=null&&password!=""&&password!=null){//we cannot have empty password in xwiki.So empty pwd means login as guest
        	setAuthenticaion(username, password); //call set authentication otherwise logins without username pwd.
        }
        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);
        try {

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());

            String[] responseParts = response.getStatusLine().toString().split(" ");
            responseCode = Integer.parseInt(responseParts[1]);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (SocketTimeoutException e){
        	Log.d(this.getClass().getSimpleName(), "Connection timeout", e);
        	//set custom response code for timeouts.        	
        	responseCode=RESP_CODE_CLIENT_CON_TIMEOUT;
        }catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("response code", String.valueOf(responseCode));
        return responseCode;
    }

    /**
     * Perform HTTP Post method execution and return its response status
     * 
     * @param Uri URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Post method execution
     */
    public String postRequest(String Uri, String content)
    {
        initialize();

        HttpPost request = new HttpPost();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);

        try {
            Log.d("Post content", "content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());
            return response.getStatusLine().toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }

        return "error";
    }

    /**
     * Perform HTTP Put method execution and return its response status
     * 
     * @param Uri URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Put method execution
     */
    public String putRequest(String Uri, String content)
    {
        initialize();
        
        HttpPut request = new HttpPut();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);

        try {
            Log.d("Put content", "content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());
            return response.getStatusLine().toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * Gets raw data from the response as a InputStream
     * 
     * @param Uri URL of XWiki RESTful API
     * @return InputStream of the HTTP Get method execution
     */
    public InputStream getResponseAttachment(String Uri)
    {
        initialize();

        HttpGet request = new HttpGet();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);
        try {

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());

            // in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            return response.getEntity().getContent();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Perform HTTP Put method with the raw data file
     * 
     * @param Uri URL of XWiki RESTful API
     * @param filePath path of the file name which to be sent over the HTTP connection
     * @return status of the HTTP Put method execution
     */
    public String putRaw(String Uri, String filePath)
    {
        initialize();

        HttpPut request = new HttpPut();

        try {
            requestUri = new URI(Uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setCredentials();

        request.setURI(requestUri);
        Log.d("Request URL", Uri);

        try {

            File file = new File(filePath);
            FileEntity fe = new FileEntity(file, "/");

            request.setEntity(fe);
            // request.setHeader("Content-Type","application/xml;charset=UTF-8");

            response = client.execute(request);
            Log.d("Response status", response.getStatusLine().toString());
            return response.getStatusLine().toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * initialize the general property fields
     */
    private void initialize()
    {
        client = new DefaultHttpClient();
        //set http params
        HttpParams params= new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);// try 10 seconds to get a socket connection
        HttpConnectionParams.setSoTimeout(params, 20000);// wait 30 seconds waiting for data. then break connection.
        client.setParams(params);
     }

    /**
     * set user credentials with manually developed preemtive Auth
     */
    private void setCredentials()
    {
        if (isSecured) {

            // Setting preemtiveAuth manually since org.apache.http does not support it
            preemptiveAuth = new HttpRequestInterceptor()
            {

                @Override
                public void process(HttpRequest request, HttpContext context) throws HttpException, IOException
                {
                    AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
                    CredentialsProvider credsProvider =
                        (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
                    HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

                    if (authState.getAuthScheme() == null) {
                        AuthScope authScope = new AuthScope(targetHost.getHostName(), targetHost.getPort());
                        Credentials creds = credsProvider.getCredentials(authScope);
                        if (creds != null) {
                            authState.setAuthScheme(new BasicScheme());
                            authState.setCredentials(creds);
                        }
                    }
                }

            };

            client.addRequestInterceptor(preemptiveAuth, 0);

            Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
            client.getCredentialsProvider().setCredentials(new AuthScope(null, -1, AuthScope.ANY_REALM), defaultcreds);
        }
    }

}

//TODO: sas: Behaviour unspecified when connection error happens, for resource get, requests.
//TODO: response body should be sent up.So just send the response back. Some times application logic may require the 
// body of the response for a page put req etc. Rao layer should handle wether the response body should be read and converted or be 
// discarded.
