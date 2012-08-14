//package org.xwiki.android.rest.ral.test;
//
//import org.xwiki.android.core.test.properties.Env;
//import org.xwiki.android.rest.RestConnectionException;
//import org.xwiki.android.rest.XWikiAPI;
//import org.xwiki.android.rest.ral.DocumentRao;
//import org.xwiki.android.rest.ral.RESTfulManager;
//import org.xwiki.android.rest.ral.RaoException;
//import org.xwiki.android.rest.ral.XmlRESTFulManager;
//import org.xwiki.android.svc.cmn.LoginFacade;
//import org.xwiki.android.xmodel.blog.XBlogPost;
//import org.xwiki.android.xmodel.entity.Comment;
//import org.xwiki.android.xmodel.entity.Document;
//
//import android.test.AndroidTestCase;
//import android.util.Log;
//
//public class TestDocumentRaoUpdate extends AndroidTestCase
//{
//
//    private static final String TAG = TestDocumentRaoUpdate.class.getSimpleName();
//    // test org.xwiki.android.test.fixture.teardown.env parameters.
//    String serverUrl, username, password, wikiName, spaceName, pageName;
//
//    int count;
//
//    // tested apis
//    RESTfulManager rm;
//    DocumentRao rao;
//    Document doc;
//
//    // api used for output verification
//    XWikiAPI api;
//
//    public TestDocumentRaoUpdate()
//    {
//        // things that need to run only one time. workaround for @BeforeClass
//        // obtain vars from Var class
//        username = Env.USERNAME;
//        password = Env.PASSWORD;
//        serverUrl = Env.URL;
//
//        wikiName = Env.WIKI_NAME;
//        spaceName = Env.SPACE_NAME;
//        pageName = Env.PAGE_NAME;
//        count = 0;
//    }
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
//    }
//
//    @Override
//    protected void tearDown() throws Exception
//    {
//        super.tearDown();
//        count++;
//    }
//
//    public void testUpdate1() throws Throwable
//    {
//        boolean success = false;
//
//        XBlogPost xb = new XBlogPost();
//        xb.setContent("edited");
//        xb.setNumber(0);
//        doc.setObject(xb);
//
//        try {
//            rao.update(doc);
//        } catch (RaoException e) {
//            success = false;
//        }
//        assertTrue(success);
//
//    }
//
//    public void testUpdate2() throws Throwable
//    {
//        boolean success = false;
//
//        Comment cmnt = new Comment("hi comment");       
//        doc.addComment(cmnt);
//        try {
//            rao.update(doc);
//        } catch (RaoException e) {
//            success = false;
//        }
//        assertTrue(success);
//    }
//
//}
