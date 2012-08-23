package org.xwiki.android.howtos.ral;


import java.io.File;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Tag;

import android.app.Activity;

public class _00_Create extends Activity
{
    
    public void create(){
        Document doc=new Document("WikiName", "spaceName", "pageName");
        //make 2 Blog.BlogPostClass objects
        XBlogPost blg1=new XBlogPost();
        XBlogPost blg2=new XBlogPost();
        
        //lets add them to the doc.
        doc.addObject(blg1);
        //We need blg2 to exactly take the id Blog.BlogPostClass/0 in the created doc.
        blg2.setNumber(0);/* set the number to the one you want. If you put a number like 3 this will obviously get created as 
                             the Blog.BlogPostClass/1 object as there are only 2 BlogPost objects.*/
        // note the naming convention blog.XBlogPost --> Blog.BlogPostClass : we remove the suffix "Class" and add a prefix "X" to the
        // corresponding serverside object type when in the android model.
        doc.setObject(blg2);
        
        
        
        //make a few comments.
        Comment c0=new Comment("hi");
        Comment c1=new Comment("reply to hi");
        Comment c2=new Comment("another reply"); 
        
        c1.replyTo(c0); //let c1 comment reply to c0
        c2.replyTo(c1);
        boolean cascade=true;
        doc.addComment(c0, cascade); //we add c0 and instruct to add c0 s children/replies to the doc as well
        
        // add a few tags to the doc.
        Tag t=new Tag("tag1");
        Tag t2=new Tag("tag2");
        
        doc.addTag(t);
        doc.addTag(t2);
        
        //lets add an attachement
        
        File f=new File("/path to my attachment file");
        Attachment a=new Attachment("Attachment name", f);
        
        doc.addAttachment(a);
        
        
        //lets create this doc in the server;
        XWikiApplicationContext ctx=(XWikiApplicationContext)getApplication();
        
        RESTfulManager rm=ctx.newRESTfulManager();
        DocumentRao rao=rm.getDocumentRao();
        
        //we then simply call the create method.
        
        try {
            rao.create(doc);
        } catch (RestConnectionException e) {
           //if IO/connection failure
        } catch (RaoException e) {
            // thrown when the document is already created on the server. If the space does not exist etc.
        }
      
        
        
        
    }

}
