//package org.xwiki.android.rest.ral.test;
//
//import org.xwiki.android.core.test.properties.Env;
//import org.xwiki.android.resources.Object;
//import org.xwiki.android.resources.Property;
//import org.xwiki.android.rest.RestConnectionException;
//import org.xwiki.android.rest.XWikiAPI;
//import org.xwiki.android.rest.ral.DocumentRao;
//import org.xwiki.android.rest.ral.RESTfulManager;
//import org.xwiki.android.rest.ral.RaoException;
//import org.xwiki.android.rest.ral.XmlRESTFulManager;
//import org.xwiki.android.svc.cmn.LoginFacade;
//import org.xwiki.android.xmodel.blog.XBlogPost;
//import org.xwiki.android.xmodel.entity.Document;
//
//import android.test.AndroidTestCase;
//import android.util.Log;
//
//public class TestDocumentRaoRetreive extends AndroidTestCase 
//{
//
//    private static final String TAG = TestDocumentRaoRetreive.class.getSimpleName();
//    // test org.xwiki.android.test.fixture.teardown.env parameters.
//    String serverUrl, username, password, wikiName, spaceName, pageName,objectClassname,propertyName;
//    int objectNumber;
//
//    // tested apis
//    RESTfulManager rm;
//    DocumentRao rao;
//    Document doc;
//
//    // api used for output verification
//    XWikiAPI api;
//
//    static int count=0;
//    
//    public TestDocumentRaoRetreive()
//    {
//        super();    	
//        username = Env.USERNAME;
//        password = Env.PASSWORD;
//        serverUrl = Env.URL;
//
//        wikiName = Env.WIKI_NAME;
//        spaceName = Env.SPACE_NAME;
//        pageName = Env.PAGE_NAME;
//        objectClassname=Env.CLASS_NAME;
//        objectNumber=Env.OBJECT_NUMBER;
//        propertyName=Env.OBJECT_PROPERTY_NAME;
//        count=0;
//    }
//    
//    
//    
//    @Override
//    protected void setUp() throws Exception
//    {
//        super.setUp();
//        rm = new XmlRESTFulManager(serverUrl, username, password);
//        api = rm.getConnection().getBaseAPI();
//        rao = rm.newDocumentRao();
//        doc = new Document(wikiName, spaceName, pageName);
//        doc.setTitle(pageName);
//        //check precondition        
//        if(count==0){
//            boolean pre=api.existsPage(wikiName, spaceName, pageName);
//            if(!pre){
//                Log.e(TAG, "preconditions failed. Please create a page to retreive. refer Env class");
//            }
//            pre=api.existsObject(wikiName, spaceName, pageName, objectClassname, objectNumber);
//            if(!pre){
//                Log.e(TAG, "preconditions failed. Please create a Object (blogPost) to retreive. refer Env class");
//            } 
//            
//            Property p=api.getObjectProperty(wikiName, spaceName, pageName, objectClassname, objectNumber+"", propertyName);
//            assertNotNull(p);
//            assertTrue(pre);
//        }
//    }
//
//    @Override
//    protected void tearDown() throws Exception
//    {
//        super.tearDown();        
//    }
//
//    public void testRetreive() throws Throwable
//    {        
//        boolean success;
//        try {
//            Document docRet=rao.retreive(doc);
//            assertNotNull(docRet);
//            XBlogPost blgPost=(XBlogPost) docRet.getObject(objectClassname+"/"+objectNumber);
//            assertNotNull(blgPost);
//            assertNotNull(blgPost.getContent());
//        }catch (RaoException e) {
//           success=false;
//        }
//        
//    }
//
//}
