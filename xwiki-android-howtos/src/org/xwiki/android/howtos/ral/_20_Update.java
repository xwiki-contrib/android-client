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

public class _20_Update extends Activity
{

    public void update(){
       
        Document doc=new Document("WikiName", "spaceName", "pageName"); 
      //make 2 Blog.BlogPostClass objects
        XBlogPost blg1=new XBlogPost();
        XBlogPost blg2=new XBlogPost();
        
        // add a new object in update op
        doc.addObject(blg1);
        
        blg2.setNumber(0);//edit the Blog.BlogPostClass/0
        doc.setObject(blg2);
        
        
        
        //edit comment 0
        Comment c0=new Comment("comments/0 edited");
        c0.setId(0);
        doc.setComment(c0);
        
        //add a new comment
        Comment c1=new Comment("new one");
        //c1.setId(7); //oops this will be ignored if you do an addComment 
        doc.addComment(c1);
        
        //lets delete another comment        
        doc.deleteComment(2);        //delete comment with id 2. 
        
        //lets replace an attachement
        
        File f=new File("/path to my attachment file");
        Attachment a=new Attachment("existing @ name", f);        
        doc.setAttachment(a);
        
        
        //lets update this doc in the server;
        XWikiApplicationContext ctx=(XWikiApplicationContext)getApplication();        
        RESTfulManager rm=ctx.newRESTfulManager();
        DocumentRao rao=rm.getDocumentRao();
        
        //we then simply call the update method.
        
        try {
            rao.update(doc);
        } catch (RestConnectionException e) {
           //if IO/connection failure
        } catch (RaoException e) {
            // thrown when the document is already created on the server. If the space does not exist etc.
        }
        
    }
    
}
