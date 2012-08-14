package org.xwiki.android.rest.ral.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.core.test.properties.Env;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.PageResources;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiAPI;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.ral.XmlRESTFulManager;
import org.xwiki.android.svc.cmn.LoginFacade;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Tag;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Tests XMLDocumentRao.create();
 */
public class TestDocumentRaoCreate extends AndroidTestCase
{
    private static final String TAG = TestDocumentRaoCreate.class.getSimpleName();
	// test org.xwiki.android.test.fixture.teardown.env parameters.
    String serverUrl, username, password, wikiName, spaceName, pageName, attachmentName;
    static int count=1;

    // tested apis
    RESTfulManager rm;
    DocumentRao rao;

    // api used for output verification
    XWikiAPI api;

    // test inputs
    Document doc;
    // special inputs set in tests
    XBlogPost xb1, xb2, xb3;
    XSimpleObject so1, so2; // a general so.
    Comment c1, c2, c3;
    Tag t1, t2;
    Attachment a1;
    File af1;

    public TestDocumentRaoCreate()
    {
        username = Env.USERNAME;
        password = Env.PASSWORD;
        serverUrl = Env.URL;

        wikiName = Env.WIKI_NAME;
        spaceName = Env.SPACE_NAME;
        pageName = Env.TEMP_PAGE_NAME;
        attachmentName=Env.ATTACHMENT_NAME;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        rm = new XmlRESTFulManager(serverUrl, username, password);
        api = rm.getConnection().getBaseAPI();
        rao = rm.newDocumentRao();
        doc = new Document(wikiName, spaceName, pageName);
        doc.setTitle(pageName);

        // setup preconditions on serverside
        if(count<6)api.deletePage(wikiName, spaceName, pageName);
        if(count==1){
            Application sys=XWikiApplicationContext.getInstance();            
            FileOutputStream fos = sys.openFileOutput(attachmentName, Context.MODE_WORLD_READABLE);            
            PrintWriter writer=new PrintWriter(fos);
            writer.println("this is a text attachment.");
            writer.flush();
            writer.close();
            af1=sys.getFileStreamPath(attachmentName);
        }
        Log.d(TAG,"setup test method:"+ count );
        
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        count++;
        // rm.close();
    }

    /**
     * a minimal test
     */
    public void testCreate01() throws Throwable
    {
        boolean success = true;

        try {
            rao.create(doc);
            success = api.existsPage(wikiName, spaceName, pageName);
        } catch (RaoException e) {
            success = false;
        } catch (RestException e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * test with xobjects. Use add method.
     */
    public void testCreate02() throws Throwable
    {
        boolean success = false;
        xb1 = new XBlogPost();
        xb1.setContent("test post");
        doc.addObject(xb1);
        try {
            rao.create(doc);
        } catch (RaoException e) {
            success = false;
        }
        try {
            success = api.existsPage(wikiName, spaceName, pageName);
        } catch (RestException e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * test add/set general XSimpleObject.
     * 
     * @throws Throwable
     */
    public void testCreate03() throws Throwable
    {
        boolean success;
        so1 = new XSimpleObject("Blog.BlogClass")
        {
        };
        so2 = new XSimpleObject("Blog.BlogCategoryClass")
        {
        };
        so2.setNumber(1);
        doc.addObject(so1);
        doc.setObject(so2);

        try {
            rao.create(doc);
        } catch (RaoException e) {
            success = false;
        }

        // verify
        try {
            success = api.existsPage(wikiName, spaceName, pageName);
            if (success) {
                boolean crite1 = api.existsObject(wikiName, spaceName, pageName, so1.getClassName(), 0);
                boolean crite2 = api.existsObject(wikiName, spaceName, pageName, so1.getClassName(), 0);
                success = crite1 & crite2;
            }
        } catch (RestException e) {
            success = false;
        }
    }

    /**
     * checks wether the objects get added in the correct sequence
     * 
     * @throws Throwable
     */
    public void testCreate04() throws Throwable
    {
        boolean success = false;
        xb1 = new XBlogPost();
        xb2 = new XBlogPost();
        xb3 = new XBlogPost();

        xb1.setContent("0");
        xb2.setContent("1");
        xb3.setContent("2");

        xb1.setNumber(0);
        // dont set num on xb2
        xb3.setNumber(2);

        doc.setObject(xb1);
        doc.addObject(xb2);
        doc.setObject(xb3);

        rao.create(doc);
        
        try {
            success = api.existsPage(wikiName, spaceName, pageName);
            if (success) {
                boolean crite1 = api.existsObject(wikiName, spaceName, pageName, xb1.getClassName(), 0);
                boolean crite2 = api.existsObject(wikiName, spaceName, pageName, xb1.getClassName(), 1);
                boolean crite3 = api.existsObject(wikiName, spaceName, pageName, xb1.getClassName(), 2);
                success = crite1 & crite2 & crite3;
            }
            if (success) {
                Property xb2PropRet =
                    api.getObjectProperty(wikiName, spaceName, pageName, xb2.getClassName(), "1", "content");
                success = xb2PropRet.getValue().equals("1");
            }

        } catch (RestException e) {
            success = false;
        }
        assertTrue(success);
    }

    public void testCreate05WithCmnts() throws Throwable
    {

        c1 = new Comment("hi");
        c2 = new Comment("reply to hi");
        c1.addReplyComment(c2);
        doc.addComment(c1, true);

        try {
            rao.create(doc);
        } catch (RaoException e) {
            throw new AssertionFailedError("xmlrpc exception code=" + e.getCode());
        }
        try {
            boolean success = api.existsPage(wikiName, spaceName, pageName);
            assertTrue(success);
            if (success) {
                Comments cmnts = api.getPageComments(wikiName, spaceName, pageName);
                List<org.xwiki.android.resources.Comment> cl = cmnts.comments;
                assertEquals(2, cl.size());
                int replyto = cl.get(1).replyTo;
                assertEquals(0, replyto);
            }
        } catch (RestException e) {
            throw new AssertionFailedError("xmlrpc exception code=" + e.getCode());
        }
    }

//    public void testCreate06WithAttachment() throws Throwable
//    {       
//        
//        Attachment a=new Attachment(attachmentName, af1);
//        doc.addAttachment(a);
//        rao.create(doc);
//        assertNotNull(api.getPageAttachment(wikiName, spaceName, pageName, attachmentName));
//        
//            
//        
//    }
}
