package org.xwiki.android.xmodel.entity.test;

import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XComment;

import android.test.AndroidTestCase;

public class CommentModelStateTest extends AndroidTestCase
{
    
    String wikiName, spaceName, pageName;
    
    public void testCommentNormalAddSet(){
        Document doc=new Document(wikiName, spaceName, pageName);
        Comment c=new Comment();
        Comment c2=new Comment();
        Comment c3=new Comment();
        c3.setId(2);
        c.addReplyComment(c2);
        c.addReplyComment(c3);
        
        doc.addComment(c, true);
        doc.addComment(c2);
        doc.addComment(c3);
        
        
        
    }
    
    public void testCommentNormalAddSet_02(){
        Document doc=new Document(wikiName, spaceName, pageName);
        Comment c=new Comment();
        Comment c2=new Comment();
        Comment c3=new Comment();
        c3.setId(5);
        c.addReplyComment(c2);
        c.addReplyComment(c3);
        
        doc.addComment(c, true);
        doc.addComment(c2);
        doc.addComment(c3);
        
        
        
    }
    
    public void testCommentIllegalReplies(){
        Document doc=new Document(wikiName, spaceName, pageName);
        Comment c=new Comment();
        Comment c2=new Comment();        
        
        doc.addComment(c);//c id=-11
        doc.addComment(c2); //c2 id=-12
        assertEquals(2, doc.getAllNewComments().size());
        
        boolean success=false;
        try{
            c2.addReplyComment(c); 
        }catch(IllegalStateException e){
            success=true;
        }     
        assertTrue(success);        
    }
    
    public void testCommentIllegalReplies_02(){
        boolean success=false;
        Document doc=new Document(wikiName, spaceName, pageName);
        Comment c=new Comment();
        Comment c2=new Comment();
        Comment c3=new Comment();
        c3.setId(0);
        c2.replyTo(c);//ok
        c3.replyTo(c);//illegal here. Identified when adding to doc.
        
        try{
            doc.addComment(c, true);
            doc.addComment(c2);
            doc.addComment(c3);    
        }catch(IllegalStateException e){
            success=true;            
        }        
        assertTrue(success);
             
        
    }

}
