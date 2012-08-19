package org.xwiki.android.rest.ral.test;

import java.util.Map;

import org.xwiki.android.core.test.properties.TestConstants;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.ral.XmlRESTFulManager;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Tag;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestDocumentRaoRetreive extends AndroidTestCase
{

    private static final String TAG = TestDocumentRaoRetreive.class.getSimpleName();
    // test org.xwiki.android.test.fixture.teardown.env parameters.
    String serverUrl, username, password, wikiName, spaceName, pageName, objectClassname, propertyName;
    int objectNumber;

    // tested apis
    RESTfulManager rm;
    DocumentRao rao;
    Document doc;

    // api used for output verification
    XWikiRestConnector api;

    static int count = 1;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        username = TestConstants.USERNAME;
        password = TestConstants.PASSWORD;
        serverUrl = TestConstants.SERVER_URL;

        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        pageName = TestConstants.PAGE_NAME;
        objectClassname = TestConstants.OBJECT_CLASS_NAME_1;
        objectNumber = TestConstants.OBJECT_NUMBER;
        propertyName = TestConstants.OBJECT_PROPERTY_NAME;

        rm = new XmlRESTFulManager(serverUrl, username, password);
        api = rm.getRestConnector();
        rao = rm.newDocumentRao();
        doc = new Document(wikiName, spaceName, pageName);
        doc.setTitle(pageName);
        // check precondition
        if (count == 0) {
            boolean pre = api.existsPage(wikiName, spaceName, pageName);
            assertTrue("preconditions failed. Please create a page to retreive. refer Env class", pre);

            pre = api.existsObject(wikiName, spaceName, pageName, objectClassname, objectNumber);

            assertTrue("preconditions failed. Please create a Object (blogPost) to retreive. refer Env class", pre);

            pre = api.existsPageComment(wikiName, spaceName, pageName, 0);
            assertTrue("preconditions failed. Please create comments to retreive. refer Env class", pre);

            Property p =
                api.getObjectProperty(wikiName, spaceName, pageName, objectClassname, objectNumber + "", propertyName);
            assertNotNull(p);
            assertTrue(pre);
        }
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testRetreive_01() throws Throwable
    {
        boolean success;
        try {
            Document rdoc = rao.retreive(doc);
            
            //verifications
            assertNotNull(rdoc);
            Log.d(TAG, rdoc.toString());
            
            XBlogPost blgPost = (XBlogPost) rdoc.getObject(objectClassname + "/" + objectNumber);
            assertNotNull(blgPost);
            assertNotNull(blgPost.getContent());
            
            int numcmnts=rdoc.getAllComments().size();
            assertTrue(numcmnts>=2);
            
            Comment c=rdoc.getComment(0);
            Comment c2=rdoc.getComment(1);
            assertNotNull(c);
            assertNotNull(c2);
            assertTrue(c2.getReplyTo()==c.getId());
            
            Tag t=rdoc.getTags().get(0);
            assertNotNull(t);
            
            Map<String, Attachment> allAttatchments = rdoc.getAllAttatchments();
            assertTrue(allAttatchments.size()>0);
            Attachment a=rdoc.getAttachment("a.png");
            assertNotNull(a);
           
        } catch (RaoException e) {
            success = false;
        }

    }
    
    public void testRetreive_02_inHistory(){
        
    }

}
