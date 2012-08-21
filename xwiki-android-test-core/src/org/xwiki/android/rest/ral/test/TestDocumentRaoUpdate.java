package org.xwiki.android.rest.ral.test;

import java.util.ArrayList;
import java.util.List;

import org.xwiki.android.core.test.properties.TestConstants;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.ral.XmlRESTFulManager;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.svc.cmn.LoginFacade;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestDocumentRaoUpdate extends AndroidTestCase
{

    private static final String TAG = TestDocumentRaoUpdate.class.getSimpleName();
    // test org.xwiki.android.test.fixture.teardown.env parameters.
    String serverUrl, username, password, wikiName, spaceName, pageName;

    static int count = 1;

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
        pageName = TestConstants.CREATE_PAGE_NAME + "-" + count;

        rm = new XmlRESTFulManager(serverUrl, username, password);
        api = rm.getRestConnector();
        rao = rm.newDocumentRao();
        doc = new Document(wikiName, spaceName, pageName);
        doc.setTitle(pageName);

        // setup external fixture
        //add page
        Page page = new Page();
        page.setName(pageName);
        page.setSpace(spaceName);
        page.setWiki(wikiName);
        api.addPage(wikiName, spaceName, pageName, page);
       //add object blogpost 0
        Object o = new Object();
        o.setClassName("Blog.BlogPostClass");
        Property content = new Property();
        content.attributes = new ArrayList<Attribute>();// needed by simple xml serializer
        Property category = new Property();
        category.attributes = new ArrayList<Attribute>();
        Property published = new Property();
        published.attributes = new ArrayList<Attribute>();
        content.name = "content";
        content.value = "BlogPost in to be updated document";
        category.setName("category");
        category.setValue("Blog.Other");
        published.setName("published");
        published.setValue("1");
        o.withProperties(content, category, published);
        api.addObject(wikiName, spaceName, pageName, o);
        //add comment
        org.xwiki.android.resources.Comment comment=new org.xwiki.android.resources.Comment();
        comment.setText("0");        
        api.addPageComment(wikiName, spaceName, pageName, comment);

    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        count++;
    }

    public void testUpdate_01() throws Throwable
    {

        XBlogPost xb = new XBlogPost();
        xb.setContent("edited");
        xb.setNumber(0);
        doc.setObject(xb);
        rao.update(doc);

        // verify
        boolean success = false;
        Object object = api.getObject(wikiName, spaceName, pageName, "Blog.BlogPostClass", 0);
        for (Property p : object.getProperties()) {
            if (p.name.equals("content")) {
                success = p.getValue().equals("edited");
            }
        }
        assertTrue(success);

    }

    public void testUpdate_02_withComments() throws Throwable
    {
        boolean success = true;
        
        //get state of external fixture page.
        int numCmntsBefore=0;
        List<org.xwiki.android.resources.Comment> comments = api.getPageComments(wikiName, spaceName, pageName).comments;
        if(comments!=null){
            numCmntsBefore=comments.size();
        }       

        //tested logic
        Comment cmnt = new Comment("hi comment");
        doc.addComment(cmnt);
        rao.update(doc);
        
        //verify if new comment was added.
        int numCmntsAfter =api.getPageComments(wikiName, spaceName, pageName).comments.size();
        assertTrue(numCmntsAfter == numCmntsBefore+1);
    }
    
    public void testUpdate_03_withComments() throws Throwable{
        
        //get state of external fixture page.
        int numCmntsBefore=0;
        List<org.xwiki.android.resources.Comment> comments = api.getPageComments(wikiName, spaceName, pageName).comments;
        if(comments!=null){
            numCmntsBefore=comments.size();
        } 
        
        //tested logic        
        Comment cmnt = new Comment("hi comment");
        String rtBfr="hi reply";
        Comment cmnt2= new Comment(rtBfr);
        cmnt2.replyTo(cmnt);
        
        doc.addComment(cmnt,true); //add the prnt cmnt with cascade to replies.
        
        rao.update(doc);
        
        //verify
        Comments comments2 = api.getPageComments(wikiName, spaceName, pageName);
        int numCmntsAfter =api.getPageComments(wikiName, spaceName, pageName).comments.size();
        assertTrue(numCmntsAfter == numCmntsBefore+2); // make sure 2 new were added.
        
        List<org.xwiki.android.resources.Comment> lst = comments2.getComments();
        org.xwiki.android.resources.Comment rply=lst.get(lst.size()-1);//it should get added last
        int replyTo=rply.replyTo;
        String rtAftr=rply.text;
        
        
        assertEquals(rtBfr, rtAftr);
    }

}
