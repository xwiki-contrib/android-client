package org.xwiki.android.blog.svc;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.blog.xobj.XBlogPost;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.ral.RaoException;
import org.xwiki.android.rest.RestConnectorException;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.svc.DocumentLocalSvcCallbacks;
import org.xwiki.android.xmodel.svc.DocumentSvc;
import org.xwiki.android.xmodel.svc.DocumentRemoteSvcCallbacks;
import org.xwiki.android.xmodel.svc.DocumentSvcImpl;

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
    String xBlogPostType="Blog.BlogPostClass"; 
    DocumentSvc docsvc;

    public BlogDocument(String wikiName, String spaceName, String pageName, String category)
    {
        doc = new Document(wikiName, spaceName, pageName);
        blgPost = new XBlogPost();
        doc.addObject(blgPost);
        doc.setTitle(doc.getSimpleName());
        blgPost.setTitle(doc.getTitle());
        blgPost.setCategory(category);
        blgPost.setHidden(false);
        
               
        docsvc = new DocumentSvcImpl();
        // DocumentSvc construction code was not sent to XWikiApplication Context. Rationale: We will eventually go to
        // Android native Bind Services.
        // The service connection handling should be done at this layer.Then the logic for constructing the service is
        // transformed to that of binding to a service.
    }

    // demo: explain
    // It is the blog svc layers knowledge that the content should go to a XBlogPost(-->BlogPostClass in Xwiki
    // Enterprise) object.
    public void setContent(String content)
    {        
        blgPost.setContent(content);// blgPost is the XBlogPost obj in the doc.
    }
    
    public String getContent(){
        return blgPost.getContent();
    }

    /**
     * @param clbk The above call back is implemented in the activity and passed to post method.It is because posting is
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

    public void save(){
        docsvc.save(doc, SAVE_TAG, null);
    }
    
    public void load(FSDocumentReference fsref, BlogDocumentLocalCallbacks clbk){
        docsvc.load(fsref,clbk);
    }
    
    public void listBlogDocuments(BlogDocumentLocalCallbacks clbk)
    {
        docsvc.listBySpace("Blog", clbk);
       //svc.listByTag("SavedBlogPost");
    }

    
    
    /*
     * demo: explain. * Also that page's title must be set.(Although this validation constraint is low level, validation
     * is not added to Document object to save performance. API user should remember to fill them.The server will send
     * back an error and in the android system there is a runtime RestAPIUsageException) The title property of XBlogPost
     * object should also be filled.However this is blog svc layer knowledge.
     */
    private void initializeDoc()
    {
       
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
                if (ex != null) {// document was already in the server.
                    // we can either tell the user, or simply
                    docsvc.delete(doc.getDocumentReference(), this);
                    // we have to delete and create.
                    // If we want to update we need to retreive the original document first.
                    // If the whole doc is not needed, we can use a Rao<XSimpleObject> to update the single object.
                    docsvc.create(doc, this);
                }
            }
        }

        @Override
        public void handleException(RestConnectorException e)
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
    /**
     * 
     * for local file store callbacks.
     *
     */
    public abstract class BlogDocumentLocalCallbacks extends DocumentLocalSvcCallbacks{
        //
        //for filestore callbacks
        //
        
        @Override
        public void onListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby){          
            onListingComplete(rslts);
        }
        
        @Override
        public void onLoadComplete(Document entity){
            doc=(Document)entity;
            String blgPostKey="";
            Set<String> keys= doc.getAllObjects().keySet();
            for(String key:keys){
                if(key.startsWith(xBlogPostType)){
                    blgPostKey=key;
                    break;
                }
            }
            blgPost=(XBlogPost) doc.getObject(blgPostKey);
            onBlogPostLoaded();
        }
        
        //make it simple for the activities to implement.
        public abstract void onListingComplete(List<FSDocumentReference> rslts);
        public abstract void onBlogPostLoaded();
    }
}
