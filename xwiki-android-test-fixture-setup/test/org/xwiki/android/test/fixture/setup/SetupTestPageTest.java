package org.xwiki.android.test.fixture.setup;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwiki.android.resources.*;
import org.xwiki.android.resources.Object;
import org.xwiki.android.test.fixture.setup.env.TestEnv;
import org.xwiki.android.test.utils.xmlrpc.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 8/11/12
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetupTestPageTest extends Assert {
    Logger logger = LoggerFactory.getLogger(SetupTestPageTest.class);
    boolean failed;

    String serverUrl, username, password, wikiName, spaceName, pageName, objecteClass, objectPropertyName, objectPropertyValue,
            commentText, commentReplyText, tagWord, attachementName, attachmentDelName;
    int commentReplyReplyTo;
    RestClient rc;

    @Before
    public void setup() {
        serverUrl = TestEnv.SERVER_URL;
        username = TestEnv.USERNAME;
        password = TestEnv.PASSWORD;

        rc = new RestClient(serverUrl, username, password);

        wikiName = TestEnv.WIKI_NAME;
        spaceName = TestEnv.SPACE_NAME;
        pageName = TestEnv.PAGE_NAME;
        objecteClass = TestEnv.CLASS_NAME;
        objectPropertyName = TestEnv.OBJECT_PROPERTY_NAME;
        objectPropertyValue = TestEnv.OBJECT_PROPERTY_VALUE;
        commentText = TestEnv.COMMENT_TEXT;
        commentReplyText = TestEnv.COMMENT_REPLY_TEXT;
        commentReplyReplyTo = TestEnv.COMMENT_REPLY_REPLY_TO;
        tagWord = TestEnv.TAG_WORD;
        attachementName=TestEnv.ATTACHMENT_NAME;
        attachmentDelName=TestEnv.ATTACHMENT_DEL_NAME;

        logger.debug("setting up the external fixture at server: " + serverUrl + " wiki:" + wikiName + " spaces: [ " + spaceName + " ]");
    }

    @Test
    public void setupPage() throws RestException, IOException {
        //delete page if exists. We delete it here because after the tests are over u can manually see the state on the server by starting it again.
        PageOperations pageOps = rc.pageOperations(wikiName, spaceName);
        if (pageOps.exists(pageName)) {
            pageOps.deletePage(pageName);
        }
        ;
        //add page

        Page p = new Page();
        p.setTitle(pageName);
        p.setName(pageName);
        p.setContent("Hi All! Happy Testing!");
        pageOps.addPage(p);

        //add obj
        org.xwiki.android.resources.Object obj = new Object();
        Property prop = new Property();
        prop.setName(objectPropertyName);
        prop.setValue(objectPropertyValue);
        prop.attributes = new ArrayList<Attribute>();   //without attributes list added simplexml will throw exception.
        obj.withClassName(objecteClass).withProperties(prop);

        ObjectOperations objOps = rc.objectOperations(wikiName, spaceName, pageName);
        objOps.addObject(obj);

        //add comment
        Comment c0 = new Comment();
        c0.setText(commentText);

        Comment c1 = new Comment();
        c1.setReplyTo(commentReplyReplyTo);
        c1.setText(commentReplyText);

        CommentOperations cmntOps = rc.commentOperations(wikiName, spaceName, pageName);
        cmntOps.addPageComment(c0);
        cmntOps.addPageComment(c1);

        //edit comment c0
        //we have to use object with class XWiki.Comment
        Object cobj=new Object();
        cobj.withClassName("XWiki.XWikiComments");
        Property cp=new Property();
        cp.setName("comment");
        cp.setValue("hi edited");
        cp.attributes=new ArrayList<Attribute>();//simple xml does not check for null
        cobj.withProperties(cp);
        objOps.updateObject("XWiki.XWikiComments",0,cobj);
        //add tag

        Tag t = new Tag();
        t.setName(tagWord);
        Tags ts = new Tags();
        ts.withTags(t);
        TagOperations tagOps = rc.tagOperations(wikiName, spaceName, pageName);
        tagOps.setTags(ts);

        //add attachment
        AttachmentOperations attachmentOperations = rc.attachmentOperations(wikiName, spaceName, pageName);
        URL url = ClassLoader.getSystemResource(attachementName);
        URL urldel = ClassLoader.getSystemResource(attachementName);
        logger.debug("Attachment path: " + url);
        File f = null;
        File fdel=null;
        try {
            f = new File(url.toURI());
            fdel=new File(urldel.toURI());
        } catch (URISyntaxException e) {
            logger.error("cannot create file for attachment:", e);
        }
        attachmentOperations.putPageAttachment(attachementName, f);
        attachmentOperations.putPageAttachment(attachementName, f);//add it again
        attachmentOperations.putPageAttachment(attachmentDelName,fdel);



        //page translations.
        Page pfr=new Page();
        pfr.setName(pageName);
        pfr.setTitle(pageName);
        pfr.setContent("Bonzua!");
        pageOps.addPageTranslation(pfr,"fr");
        pfr.setContent("Bonzua! Happy Testing in French");
        pageOps.addPageTranslation(pfr, "fr");



    }

    @After
    public void cleanup() throws Throwable {
        rc.shutDown();
    }

    ///
    /*
    public static final String COMMENT_ID = "0";

    public static final String LANGUAGE = "null";

    public static final String CLASS_NAME = "Blog.BlogPostClass";

    public static final String OBJECT_NUMBER = "0";

    public static final String OBJECT_PROPERTY_NAME = "content";

    public static final String SEARCH_KEWORD = "test";

    public static final String TAG_WORD = "testTag";

    public static final String ATTACHMENT_NAME = "a.png";

    public static final String ATTACHMENT_PATH = "./";

    public static final String ATTACHMENT_VERSION = "1.0";
    */

}
