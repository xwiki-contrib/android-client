package org.xwiki.android.howtos;

import org.xwiki.android.svc.xmodel.DocumentLocalSvcs;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;

public class _03_SaveDocument
{
     public void demo(){
        
        Document mydoc=new Document("wikiName", "spaceName", "pageName");//create empty document        
        // ... edit mydoc
        mydoc.addObject(new XBlogPost());
        mydoc.setContent("edited page content");
        mydoc.addComment(new Comment("hi"));        
        
        //lets save it!
        
        DocumentLocalSvcs docsvcl=new DocumentSvcImpl();
        
        docsvcl.save(mydoc, "My short TAG to identify this easily", null);//docsvcl.save(doc, tag, clbk). we pass null for clbk
                                                                    // since we do not want to know whether the doc was sucessfully
                                                                    // saved or not.
        /*
         * The current implementatation supports tagging to identify documents saved in the file store.
         * The key for the document is automatically generated as a combination of wiki:space:page:version.
         */
                       
    }
}
