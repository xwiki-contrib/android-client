package org.xwiki.android.svc.xmodel;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.xmodel.entity.Document;

import android.os.Handler;
import android.os.Looper;


/**
 * @author xwiki gsoc 2012
 */
public abstract class DocumentRemoteSvcCallbacks 
{
	protected final Handler mhandle;
	/**
     * binds the Callback with the UI threads looper.
     */
    public DocumentRemoteSvcCallbacks()
    {
        Looper mainLooper = Looper.getMainLooper(); // reference to the main
                                                    // threads looper in
                                                    // MainThread's Thread Local
                                                    // Storage.
        mhandle = new Handler(mainLooper);
    }

    /**
     * Binds the callback with the given looper. You will not be able to update UI from the call back if not bound to
     * the UI threads looper.
     * 
     * @param looper
     */
    public DocumentRemoteSvcCallbacks(Looper looper)
    {
        mhandle = new Handler(looper);
    }

    
	
	
    //
    // -------------------------------client should override the needed call backs.
    //
    // for RAO .

    
    public void onProgressUpdate(int progress)
    {

    }

    /**
     * @param doc The document constructed from server responses. Should configure the Rao to construct the Document
     *            entity before calling create(). //TODO: currently not supported by underlying ReSTful Connector.
     * @param success whether the document was successfully created on the server.
     * @param ex reasons: doc does not exist in server.
     * 
     */
    public void onCreated(Document res, boolean success, RaoException ex)
    {

    }

    
    public void onFullyRetreived(Document res, boolean success, RaoException ex)
    {

    }

    /**
     * This method returns a EntityWrapper.(For automatic lazy fetching)
     * 
     * @param res The partially retrieved Resource.If the resouce is a document calling for an object that is not yet
     *            retreived inside the document will throw a runtime RESTfulLazyFetchException. The object will be
     *            automatically loaded and onDocumentFull/PartiallyRetreived() will be called accordingly.Please make
     *            sure to catch the exception if you call a Object or first class entity inside the document which
     *            haven't loaded yet.
     * @param success 
     * @param ex reasons: this doc does not exist in the server.
     * @param args List of newly loaded components of the document. Ex: Blog.BlogPostClass/* means all BlogPostClass
     *            objs have been loaded.
     */
    public void onPartiallyRetreived(Document res, boolean success, RaoException ex, String... args)
    {

    }

    /**
     * @param rslts
     * @param success TODO
     * @param ex reasons:Interanl querry engine discovered the querry to be wrong, Unauthorized, ...
     * @param args The list of components already loaded into the documents.
     */
    public void onQuerryComplete(List<Document> rslts, boolean success, RaoException ex, String... args)
    {

    }

    /**
     * @param res The updated resource. May be the original or a clone filled with response data from server
     * @param success Whether update operation was successful
     * @param ex If not successful what the exception is. reasons 1) The document to be updated is not yet created.
     */
    public void onUpdated(Document res, boolean success, RaoException ex)
    {

    }

    /**
     * @param success
     * @param ex TODO
     */
    public void onDeleted(boolean success, RaoException ex)
    {

    }

    /**
     * Call back when IO error occures(ex: the Internet connectivity is not available). The client should handle this
     * situation and choose whether to sync later,retry, or ignore.
     * 
     * @param e the exception passed back asynchronously to be handled by the client.
     */
    public void handleException(RestConnectionException e)
    {

    }
    
    

    

    /*
     * following are methods implementing an invoker pattern. These methods transfer control of the event handling to
     * the UI thread. i.e. UI thread is a pipeline thread which has a queue of messages which it waits on(Looper). These
     * methods transfer a Runnable to be run by the UI thread. (Or the thread of the looper in
     * RaoCallbackForDocument(Looper looper), which this Callback was constructed with.)
     */

    void invokeProgressUpdate(int progress)
    {
        mhandle.post(new RunnableWiP(progress)
        {
            @Override
            public void run()
            {
                onProgressUpdate((Integer) args[0]);
            }
        });
    }

    void invokeCreated(Document res, boolean success, RaoException rex)
    {
        mhandle.post(new RunnableWiP(res,success,rex)
        {
            @Override
            public void run()
            {
            	boolean success=(Boolean) args[1];
            	RaoException ex=(RaoException) args[2];
                onCreated((Document) args[0], success, ex);
            }
        });
    }

    void invoketFullyRetreived(Document res, boolean success, RaoException rex)
    {
        mhandle.post(new RunnableWiP(res,success,rex)
        {
            @Override
            public void run()
            {
                boolean success=(Boolean) args[1];
                RaoException ex=(RaoException) args[2];
                onFullyRetreived((Document) args[0], success, ex);
            }
        });

    }

    void invokePartiallyRetreived(Document res, boolean success,RaoException rex, String... args)
    {
        mhandle.post(new RunnableWiP(res,success,rex,args)
        {
            @Override
            public void run()
            {
                boolean success=(Boolean) args[1];
                RaoException ex=(RaoException) args[2];
                String[] ags=(String[]) this.args[3];
                onPartiallyRetreived((Document) args[0], success, ex, ags);
            }
        });

    }

    void invokeQuerryComplete(List<Document> rslts,boolean success,RaoException ex, String... args)
    {
        mhandle.post(new RunnableWiP(rslts,success,ex,args)
        {
            @Override
            public void run()
            {
                List<Document> rslts=(List<Document>) args[0];
                boolean success=(Boolean) args[1];
                RaoException ex=(RaoException) args[2];
                String[] args2=(String[]) args[3];
                onQuerryComplete(rslts, success, ex, args2);
            }
        });

    }
    
    void invokeUpdated(Document doc, boolean success, RaoException ex){
    	mhandle.post(new RunnableWiP(doc,success,ex)
        {
            @Override
            public void run()
            {
                boolean success=(Boolean) args[1];
                RaoException ex=(RaoException) args[2];
                onUpdated((Document) args[0], success, ex);
            }
        });
    }

    void invokeDeleted(boolean success,RaoException ex)
    {
        mhandle.post(new RunnableWiP(success,ex)
        {
            @Override
            public void run()
            {
                boolean success=(Boolean) args[0];
                RaoException ex=(RaoException) args[1];
                onDeleted(success, ex);
            }
        });

    }

    void invokeHandleException(RestConnectionException e)
    {
        mhandle.post(new RunnableWiP(e)
        {
            @Override
            public void run()
            {                
                handleException((RestConnectionException) args[0]);
            }
        });
    }

    /**
     * Runnable with parameters.
     */
    protected abstract class RunnableWiP implements Runnable
    {
        public Object[] args;

        public RunnableWiP(Object... args)
        {
            this.args = args;
        }
    }
    
   
}
