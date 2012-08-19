package org.xwiki.android.rest.ral.test;

import org.xwiki.android.core.test.properties.TestConstants;
import org.xwiki.android.resources.Page;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.ral.XmlRESTFulManager;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.svc.cmn.LoginFacade;
import org.xwiki.android.xmodel.entity.Document;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestDocumentRaoDelete extends AndroidTestCase 
{

    private static final String TAG = TestDocumentRaoDelete.class.getSimpleName();
    static int count=1;
    
 // test org.xwiki.android.test.fixture.teardown.env parameters.
    String serverUrl, username, password, wikiName, spaceName, pageName;

    // tested apis
    RESTfulManager rm;
    DocumentRao rao;
    Document doc;

    // api used for output verification
    XWikiRestConnector api;

    
   
    
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp(); 
        username = TestConstants.USERNAME;
        password = TestConstants.PASSWORD;
        serverUrl = TestConstants.SERVER_URL;

        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        pageName = TestConstants.CREATE_PAGE_NAME;
        
        rm = new XmlRESTFulManager(serverUrl, username, password);
        api = rm.getRestConnector();
        rao = rm.newDocumentRao();
        doc = new Document(wikiName, spaceName, pageName);
        doc.setTitle(pageName);
        Page p=new Page();
        p.setName(pageName);
        p.setTitle(pageName);
        api.addPage(wikiName, spaceName, pageName, p);
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        count++;        
    }

    public void testDelete01() throws Throwable
    {        
        rao.delete(doc);
    }
    
    public void testDelete02() throws Throwable
    {        
        rao.delete(doc.getDocumentReference());
    }
    
    

}
