package org.xwiki.android.howtos;

import org.xwiki.android.blog.xobj.XBlogPost;
import org.xwiki.android.ral.RaoException;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.svc.DocumentRemoteSvcCallbacks;
import org.xwiki.android.xmodel.svc.DocumentRemoteSvcs;
import org.xwiki.android.xmodel.svc.DocumentSvcImpl;


public class _01_CreateDocument
{
    public void demo(){
        
        Document mydoc=new Document("wikiName", "spaceName", "pageName");//create empty document
        //lets make a Blog.BlogPostClass object to put into mydoc.
        // in XA all model objects,classes, properties are prefixed with X.
        XBlogPost blgpstObj=new XBlogPost(); // Blog.BlogPostClass is know as ...blog.xobj.XBlogPost. Here we have removed
                                            // the suffix Class and added a prefix X.               
        
        //lets edit some properties of the XBlogPost object
        blgpstObj.setContent("This blog post is created with Xwiki Android");         
        mydoc.addObject(blgpstObj);
        
        DocumentRemoteSvcs docsvc=new DocumentSvcImpl();// the current implementation for DocumentRemoteSvcs is this.
        
        //docsvc can be used to creat the document on the server. The service uses a seperate thread to do all the work.
        //The results are given through a callback.
        
        DocumentRemoteSvcCallbacks clbk=new DocumentRemoteSvcCallbacks()
        {
            @Override
            public void onCreated(Document res, boolean success, RaoException ex)
            {
                if(success){
                 // update ui here. You can notify the user that the doc was created in the server.
                }else{
                    //you can check what the exception is and try to rectify.
                    //Most probably this same document is already existing in the server. So you should delete
                    //the already existing one and recreate. Or you can do a simple update by docsvc.update(mydoc, clbk);
                } 
            }
        };        
        docsvc.create(mydoc,clbk); //ok the docsvc does all the work of creating a page for the document and then adding
                                    // all those objects,tags,comments and attachments in the document.
        
   
    }
}
