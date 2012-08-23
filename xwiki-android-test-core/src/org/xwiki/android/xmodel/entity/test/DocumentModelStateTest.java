package org.xwiki.android.xmodel.entity.test;

import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;

import android.test.AndroidTestCase;

public class DocumentModelStateTest extends AndroidTestCase
{
    
    private String wikiName, spaceName, pageName;
    
    public void testAddSetStateManipulation(){
        
        Document doc=new Document(wikiName, spaceName, pageName);
        
        XBlogPost xblgpst1=new XBlogPost();
        XBlogPost xblgpst2=new XBlogPost();
        
        Comment c1=new Comment();
        Comment c2=new Comment();
        
//        Attachment a1=new Attachment();
//        Attachment a2=new Attachment();
        
        xblgpst1.setNumber(0);
        xblgpst1.setEdited(false);
        doc.setObject(xblgpst1);
        xblgpst2.setNew(false);
        doc.addObject(xblgpst2);
        
        c1.setId(0);
        c1.setEdited(false);
        c2.setId(1);
        c2.setNew(false);
        doc.setComment(c1);
        doc.addComment(c2);
        
        assertEquals(0, doc.getAllNewObjects().size());
        assertEquals(0, doc.getAllEditedObjects().size());
        assertEquals(0, doc.getAllNewComments().size());
        assertEquals(0, doc.getAllEditedComments().size());
        
        
        
    }

}
