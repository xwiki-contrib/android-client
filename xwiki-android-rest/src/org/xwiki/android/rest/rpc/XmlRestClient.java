package org.xwiki.android.rest.rpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
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
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Resource;
import org.xwiki.android.rest.HttpConnector;
import org.xwiki.android.rest.RestBadRequestException;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

import android.util.Log;

//TODO: under construction.
public class XmlRestClient implements RestClient
{

    private static final String TAG = RestClient.class.getSimpleName();
    private static final String TAG2 = RestClient.class.getSimpleName() + "_ SimpleXML";

    /**
     * URI of the Remote XWiki instance i.e. host:port
     */
    private String serverUrl;

    /**
     * User credential details
     */
    private String username, password;

    /**
     * wether authentication enabled.
     */
    private boolean isSecured = false;

    // /**
    // * currently we use preemptive auth. Not a good choice to send username password every time.
    // * This var is immutable. U have to change it internally because we will move away from pre emptive auth very
    // soon.
    // */
    // private boolean smartAuth =true;

    /**
     * For executing http connection
     */
    private DefaultHttpClient client;

    /**
     * Sets user credentials of the XWiki user
     * 
     * @param username username of the XWiki user account
     * @param password password of the XWiki user account
     */
    public void setAuthenticaion(String username, String password)
    {
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            this.username = username;
            this.password = password;
            isSecured = true;
        } else {
            isSecured = false;
        }
    }

    /**
     * @return gets whether connection should add user credentials
     */
    public boolean isAuthenticated()
    {
        return isSecured;
    }

    public int headForStatus(String Uri) throws RestConnectionException
    {
        return head(Uri).getStatusLine().getStatusCode();
    }

    public <T extends Resource> T getForResource(String uri, Class<T> retType) throws RestConnectionException,
        RestException
    {
        HttpResponse response = get(uri);
        if (retType != null) {
            return buildResource(retType, response.getEntity());
        } else {
            return null;
        }
    }

    public <T extends Resource> T postForResource(String uri, Resource res, Class<T> retType)
        throws RestConnectionException, RestException
    {
        String content = toXmlString(res);
        HttpResponse response = post(uri, content);
        if (retType != null) {
            return buildResource(retType, response.getEntity());
        } else {
            return null;
        }
    }

    public <T extends Resource> T putForResource(String uri, Resource res, Class<T> retType)
        throws RestConnectionException, RestException
    {
        String content = toXmlString(res);
        HttpResponse response = put(uri, content);
        if (retType != null) {
            return buildResource(retType, response.getEntity());
        } else {
            return null;
        }
    }

    public boolean deleteResource(String uri) throws RestConnectionException, RestException
    {
        int code = delete(uri).getStatusLine().getStatusCode();
        return 204 == code;
    }

    HttpResponse head(String uri) throws RestConnectionException
    {

        HttpHead request = new HttpHead();
        URI requestUri;
        try {
            requestUri = new URI(uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Request URL :" + uri);

        try {
            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());
            return response;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RestConnectionException(e);
        }
    }

    /**
     * Perform HTTP Get method execution and return the response as a String
     * 
     * @param uri URL of XWiki RESTful API
     * @return Response data of the HTTP connection as a String
     */
    HttpResponse get(String uri) throws RestConnectionException, RestException
    {

        HttpGet request = new HttpGet();

        try {
            URI requestUri = new URI(uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "GET URL :" + uri);
        try {
            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());
            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RestConnectionException(e);
        }
    }

    /**
     * Perform HTTP Delete method execution and return its status
     * 
     * @param uri URL of XWiki RESTful API
     * @return status of the HTTP method execution
     * @throws RestConnectionException
     * @throws RestException
     */
    HttpResponse delete(String uri) throws RestConnectionException, RestException
    {

        HttpDelete request = null;
        try {
            URI requestUri = new URI(uri);
            request = new HttpDelete();
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "DELETE URL :" + uri);
        try {
            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());
            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RestConnectionException(e);
        }
    }

    /**
     * Perform HTTP Post method execution and return its response status
     * 
     * @param uri URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Post method execution
     * @throws RestConnectionException
     * @throws RestException
     */
    HttpResponse post(String uri, String content) throws RestConnectionException, RestException
    {

        HttpPost request = new HttpPost();

        try {
            URI requestUri = new URI(uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "POST URL :" + uri);

        try {
            Log.d("Post content", "content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());

            validate(response.getStatusLine().getStatusCode());
            return response;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            throw new RestConnectionException(e);
        }
        return null;
    }

    /**
     * Perform HTTP Put method execution and return its response status
     * 
     * @param uri URL of XWiki RESTful API
     * @param content content to be posted to the server
     * @return status code of the Put method execution
     * @throws RestConnectionException
     * @throws RestException
     */
    public HttpResponse put(String uri, String content) throws RestConnectionException, RestException
    {

        HttpPut request = new HttpPut();

        try {
            Log.d(TAG, "Request URL :" + uri);
            System.out.println(uri);
            URI requestUri = new URI(uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "Put content=" + content);
            StringEntity se = new StringEntity(content, "UTF-8");

            se.setContentType("application/xml");
            // se.setContentType("text/plain");
            request.setEntity(se);
            request.setHeader("Content-Type", "application/xml;charset=UTF-8");

            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());
            // close the stream to releas resources. //TODO: [ignore since test utils.]ideally this should be closed
            // after content is read (if needed) by requester. So move it to PageOps etc.
            validate(response.getStatusLine().getStatusCode());
            return response;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RestConnectionException(e);
        }

        return null;
    }

    /**
     * Perform HTTP Put method with the raw data file
     * 
     * @param uri URL of XWiki RESTful API
     * @param file file to upload
     * @return status of the HTTP Put method execution
     * @throws RestConnectionException
     * @throws RestException
     */
    public HttpResponse put(String uri, File file) throws RestConnectionException, RestException
    {

        HttpPut request = new HttpPut();
        try {
            URI requestUri = new URI(uri);
            request.setURI(requestUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Request URL :" + uri);
        try {
            FileEntity fe = new FileEntity(file, "/");
            request.setEntity(fe);
            // request.setHeader("Content-Type","application/xml;charset=UTF-8");
            HttpResponse response = client.execute(request);
            Log.d(TAG, response.getStatusLine().toString());

            validate(response.getStatusLine().getStatusCode());
            return response;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RestConnectionException(e);
        }
    }

    @Override
    public XWikiAPI getBaseAPI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public SpaceOperations spaceOperations(String wikiName)
    {
        return new _SpaceOperations(serverUrl, wikiName, this);
    }

    @Override
    public PageOperations pageOperations(String wikiName, String spaceName)
    {
        return new _PageOperations(serverUrl, wikiName, spaceName, this);
    }

    @Override
    public ObjectOperations objectOperations(String wikiName, String spaceName, String pageName)
    {
        return new _ObjectOperations(serverUrl, wikiName, spaceName, pageName, this);
    }

    @Override
    public CommentOperations commentOperations(String wikiName, String spaceName, String pageName)
    {
        return new _CommentOperations(serverUrl, wikiName, spaceName, pageName, this);
    }

    @Override
    public AttachmentOperations attachmentOperations(String wikiName, String spaceName, String pageName)
    {
        return new _AttachmentOperations(serverUrl, wikiName, spaceName, pageName, this);
    }

    @Override
    public TagOperations tagOperations(String wikiName, String spaceName, String pageName)
    {
        return new _TagOperations(serverUrl, wikiName, spaceName, pageName, this);
    }

    @Override
    public HistoryOperations historyOperations(String wikiName, String spaceName, String pageName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void shutDown()
    {
        client.getConnectionManager().shutdown();
    }

    /**
     * initializeHttpClient the general property fields
     */
    private void initializeHttpClient()
    {
        client = new DefaultHttpClient();
        // set http params
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);// try 10 seconds to get a socket connection
        HttpConnectionParams.setSoTimeout(params, 20000);// wait 30 seconds waiting for data. then break connection.
        client.setParams(params);
    }

    /**
     * set user credentials with manually developed preemtive Auth
     */
    private void setCredentials()
    {
        // Setting preemtiveAuth manually since org.apache.http does not support it
        HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor()
        {
            @Override
            public void process(HttpRequest request, HttpContext context) throws HttpException
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

    private void validate(int code) throws RestException
    {
        if (400 <= code & code < 500) {
            throw new RestException(code);
        } else if (500 <= code & code < 600) {
            throw new RuntimeException("server error : bad request.  code:" + code);
        }
    }

    private String toXmlString(Resource res)
    {
        Serializer serializer = new Persister();
        StringWriter result = new StringWriter();

        try {
            serializer.write(res, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result.toString();
    }

    private <T extends Resource> T buildResource(Class<T> ofType, HttpEntity from)
    {
        Serializer serializer = new Persister();
        T res = null;
        try {
            res = serializer.read(ofType, EntityUtils.toString(from));
            from.consumeContent(); // from.finish().
        } catch (Exception e) {
            Log.e(TAG2, e.getMessage());
        }
        return res;
    }

}
