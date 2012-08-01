package org.xwiki.android.svcx.blog;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentRemoteSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentSvc;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Document;

/**
 * @author xwiki gsoc 2012 DEMO :Low lvl Service API usage Demo. Demo class. To show usage of Document, DocumentService
 *         APIs. Just to make things simple, BlogDocument class has no separation of concerns such as represent model vs
 *         services.
 */
public class BlogDocument implements Serializable
{
    public static final String SAVE_TAG="svdBlgPst";
    Document doc;

    XBlogPost blgPost;
    String xBlogPostType;
    DocumentSvc docsvc;
    boolean toUpdate;

    public BlogDocument(String wikiName, String spaceName, String pageName, String category)
    {
        xBlogPostType=XBlogPost.getType();
        doc = new Document(wikiName, spaceName, pageName);
        
        blgPost = new XBlogPost();
        doc.addObject(blgPost);
        doc.setTitle(doc.getPageName());
        /*
         * demo: explain.  Page's title must be set.(Although this validation constraint is low level, validation
         * is not added to Document object to save performance. API user should remember to fill them.The server will send
         * back an error and in the android system there is a runtime RestAPIUsageException) 
         */
        blgPost.setTitle(doc.getTitle());        
        blgPost.setCategory(category);
        blgPost.setHidden(false);        
        
               
        docsvc = new DocumentSvcImpl();
        // DocumentSvc construction code was not sent to XWikiApplication Context. Rationale: We will eventually go to
        // Android native Bind Services.
       
    }
    
    
    public BlogDocument(Document d){
        xBlogPostType=XBlogPost.getType();
        this.doc=d;
        toUpdate=true;
        blgPost=(XBlogPost) doc.getObject(xBlogPostType+"/0");
        if(blgPost==null){
        	blgPost=(XBlogPost) doc.getObject(xBlogPostType+"/new/0");
        	if(blgPost==null){
        	    blgPost=new XBlogPost();
                doc.addObject(blgPost);                
        	}           
        	toUpdate=false;
        }
        docsvc=new DocumentSvcImpl();
    }

    // demo: explain
    // It is the blog svc layers knowledge that the content should go to a XBlogPost(-->BlogPostClass in Xwiki
    // Enterprise) object.
    public void setContent(String content)
    {        
        blgPost.setContent(content);// blgPost is the XBlogPost obj in the doc.
        if(toUpdate){
        	blgPost.setEdited(true);
        	doc.setObject(xBlogPostType+"/0", blgPost);
        }
    }
    
    public String getContent(){
        return blgPost.getContent();
    }

    /**
     * @param clbk this call back is implemented in the activity and passed to post method.It is because posting is
     *            done asynchronously. Activity is notified of the progress through the callback. ( BlogDocumentCallback
     *            associates with UI threads Looper.)
     */
    public void post(BlogDocumentRemoteCallbacks clbk)
    {
        blgPost.setPublished(false);
        docsvc.create(doc, clbk);
    }

    public void publish(BlogDocumentRemoteCallbacks clbk)
    {
        blgPost.setPublished(true);
        docsvc.create(doc, clbk);
    }
    
    public void postUpdate(BlogDocumentRemoteCallbacks clbk)
    {
        blgPost.setPublished(false);
        docsvc.update(doc, clbk);
    }

    public void publishUpdate(BlogDocumentRemoteCallbacks clbk)
    {
        blgPost.setPublished(true);
        docsvc.update(doc, clbk);
    }

    public void save(){
        docsvc.save(doc, SAVE_TAG, null);
    }
    
    
    public Document unwrap(){
        return doc;
    }
    
    
    
    
    /**
     * 
     * for remote restful method results.
     *
     */
    public abstract class BlogDocumentRemoteCallbacks extends DocumentRemoteSvcCallbacks
    {
        @Override
        public void onCreated(Document res, boolean success, RaoException ex)
        {
            if (success) {
                onBlogPostSent(true);
            } else {               
                //can retry to update.
                docsvc.update(res, this);
            }
        }
        
        @Override
        public void onUpdated(Document res, boolean success, RaoException ex)
        {
        	if(success){
        		onBlogPostSent(success);
        	}else{
        		onBlogPostSent(false);
        	}
        }

        @Override
        public void handleException(RestConnectionException e)
        {
            // SyncServic.syncLater(doc)
            docsvc.save(doc, null, null);// !!! unending loop if file saving is not successful, and u pass 'this' as
                                         // callback ;-)
            onBlogPostSent(false);
        }

        // define absract method for the activity to put it's logic in.

        /**
         * if success is false, Don't worry. Doc has been stored in the local device and sent for the sync service.(yet
         * to impl ;-))
         */
        public abstract void onBlogPostSent(boolean success);
        
        
        
       

    }
    
}
