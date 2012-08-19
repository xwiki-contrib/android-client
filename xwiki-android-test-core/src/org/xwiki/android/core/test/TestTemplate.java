package org.xwiki.android.core.test;

import org.xwiki.android.core.test.properties.TestConstants;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.ral.XmlRESTFulManager;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.svc.cmn.LoginFacade;
import org.xwiki.android.xmodel.entity.Document;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestTemplate extends AndroidTestCase 
{

    private static final String TAG = TestTemplate.class.getSimpleName();
    static int count=1;
    
 // test org.xwiki.android.test.fixture.teardown.env parameters.
    String serverUrl, username, password, wikiName, spaceName, pageName;

    // tested apis
    RESTfulManager rm;
    DocumentRao rao;
    Document doc;

    // api used for output verification
    XWikiAPI api;
    
   
    
    
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        Log.d(TAG, "SET UP" + countTestCases());
        
        //run before each testxxxx; 
        //
        username = TestConstants.USERNAME;
        password = TestConstants.PASSWORD;
        serverUrl = TestConstants.SERVER_URL;

        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        pageName = TestConstants.PAGE_NAME;
        count=0;
        
        
        
        rm = new XmlRESTFulManager(serverUrl, username, password);
        api = rm.getRestConnector().getBaseAPI();
        rao = rm.newDocumentRao();
        doc = new Document(wikiName, spaceName, pageName);
        doc.setTitle(pageName);
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        count++;
        Log.d(TAG, "TEAR DOWN");
        //run after each testxxxx
        // add cleanup code here
    }

    public void testYourThing1() throws Throwable
    {        
        assertTrue(true);
    }
    
    public void testYourThing2() throws Throwable
    {        
        assertTrue(true);
    }

}
