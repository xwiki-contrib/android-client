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
package org.xwiki.android.test.utils.xmlrpc;

import java.io.BufferedReader;
import java.io.File;
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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
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
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for test setup setup. Same as org.xwiki.android.xmlrpc.HttpConnector. But not Dependant on android platform.   *
 * org.apache.http does not support preemtiveAuth, it is provided with HttpRequestInterceptor.
 */

public class RestClient {
    Logger log = LoggerFactory.getLogger(RestClient.class);


    private boolean secured;
    private String serverUrl;

    /**
     * User credential details
     */
    private String username, password;

    /**
     * For executing http connection
     */
    private DefaultHttpClient client;

    public RestClient(String serverUrl) {
        this.serverUrl = serverUrl;
        initializeHttpClient();
    }

    /**
     * @param serverUrl = server name = host:port
     * @param username
     * @param password
     */
    public RestClient(String serverUrl, String username, String password) {
        this.username = username;
        this.password = password;
        this.serverUrl = serverUrl;
        initializeHttpClient();
        secured = true;
        setCredentials();
    }

    public void setAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
        secured = true;
        setCredentials();
    }

    public boolean isSecured() {
        return secured;
    }

    public int headRequest(String Uri) throws IOException {

        HttpHead request = new HttpHead();
        URI requestUri;
        try {
            requestUri = new URI(Uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.debug("Request URL :" + Uri);

        try {
            HttpResponse response = client.execute(request);
            log.debug("Response status", response.getStatusLine().toString());
            return response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Perform HTTP Get method execution and return the response as a String
     *
     * @param Uri URL of XWiki RESTful API
     * @return Response data of the HTTP connection as a String
     */
    public HttpResponse getRequest(String Uri) throws IOException, RestException {


        BufferedReader in = null;
        HttpGet request = new HttpGet();
        String responseText = new String();

        try {
            URI requestUri = new URI(Uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.debug("Request URL :" + Uri);
        try {

            HttpResponse response = client.execute(request);
            log.debug("Response status", response.getStatusLine().toString());
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            responseText = sb.toString();
            log.debug("Response", "response: " + responseText);
            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Perform HTTP Delete method execution and return its status
     *
     * @param Uri URL of XWiki RESTful API
     * @return status of the HTTP method execution
     * @throws IOException
     * @throws RestException
     */
    public HttpResponse deleteRequest(String Uri) throws IOException, RestException {

        HttpDelete request = null;

        try {
            URI requestUri = new URI(Uri);
            request = new HttpDelete();
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.debug("Request URL :" + Uri);
        try {
            HttpResponse response = client.execute(request);

            log.debug("Response status", response.getStatusLine().toString());
            EntityUtils.consume(response.getEntity());
            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Perform HTTP Post method execution and return its response status
     *
     * @param Uri     URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Post method execution
     * @throws IOException
     * @throws RestException
     */
    public HttpResponse postRequest(String Uri, String content) throws IOException, RestException {


        HttpPost request = new HttpPost();

        try {
            URI requestUri = new URI(Uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        log.debug("Request URL :" + Uri);

        try {
            log.debug("Post content", "content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            HttpResponse response = client.execute(request);
            log.debug("Response status", response.getStatusLine().toString());
            EntityUtils.consume(response.getEntity());
            validate(response.getStatusLine().getStatusCode());
            return response;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Perform HTTP Put method execution and return its response status
     *
     * @param Uri     URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Put method execution
     * @throws IOException
     * @throws RestException
     */
    public HttpResponse putRequest(String Uri, String content) throws IOException, RestException {


        HttpPut request = new HttpPut();

        try {
            log.debug("Request URL :" + Uri);
            System.out.println(Uri);
            URI requestUri = new URI(Uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        try {
            log.debug("Put content", "content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            HttpResponse response = client.execute(request);
            log.debug("Response status", response.getStatusLine().toString());
            EntityUtils.consume(response.getEntity());//close the stream to releas resources. //TODO: [ignore since test utils.]ideally this should be closed after content is read (if needed) by requester. So move it to PageOps etc.
            validate(response.getStatusLine().getStatusCode());
            return response;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IOException(e);
        }

        return null;
    }


    /**
     * Perform HTTP Put method with the raw data file
     *
     * @param Uri  URL of XWiki RESTful API
     * @param file file to upload
     * @return status of the HTTP Put method execution
     * @throws IOException
     * @throws RestException
     */
    public HttpResponse putRequest(String Uri, File file) throws IOException, RestException {

        HttpPut request = new HttpPut();
        try {
            URI requestUri = new URI(Uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.debug("Request URL :" + Uri);
        try {
            FileEntity fe = new FileEntity(file, "/");
            request.setEntity(fe);
            // request.setHeader("Content-Type","application/xml;charset=UTF-8");
            HttpResponse response = client.execute(request);
            log.debug("Response status", response.getStatusLine().toString());
            EntityUtils.consume(response.getEntity());
            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        }
    }


    public SpaceOperations spaceOperations(String wikiName) {
        return new SpaceOperations(serverUrl, wikiName, this);
    }

    public PageOperations pageOperations(String wikiName, String spaceName) {
        return new PageOperations(serverUrl, wikiName, spaceName, this);
    }

    public ObjectOperations objectOperations(String wikiName, String spaceName, String pageName) {
        return new ObjectOperations(serverUrl, wikiName, spaceName, pageName, this);
    }

    public CommentOperations commentOperations(String wikiName, String spaceName, String pageName) {
        return new CommentOperations(serverUrl, wikiName, spaceName, pageName,this);
    }

    public AttachmentOperations attachmentOperations(String wikiName, String spaceName, String pageName) {
        return new AttachmentOperations(serverUrl, wikiName, spaceName, pageName,this);
    }

    public TagOperations tagOperations(String wikiName, String spaceName, String pageName){
        return new TagOperations(serverUrl,wikiName,spaceName,pageName,this);
    }



    public void shutDown() {
        client.getConnectionManager().shutdown();
    }


    /**
     * initializeHttpClient the general property fields
     */
    private void initializeHttpClient() {
        client = new DefaultHttpClient();
        //set http params
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);// try 10 seconds to get a socket connection
        HttpConnectionParams.setSoTimeout(params, 20000);// wait 30 seconds waiting for data. then break connection.
        client.setParams(params);
    }

    /**
     * set user credentials with manually developed preemtive Auth
     */
    private void setCredentials() {
        // Setting preemtiveAuth manually since org.apache.http does not support it
        HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
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

    private void validate(int code) throws RestException {
        if (400 <= code & code < 500) {
            throw new RestException(code);
        } else if (500 <= code & code < 600) {
            throw new RuntimeException("server error : bad request.  code:" + code);
        }
    }


}









