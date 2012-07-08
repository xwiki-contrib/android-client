package org.xwiki.android.ral;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.xmodel.entity.DocumentBase;

public interface RaoCallback<T>
{

    void onProgressUpdate(int progress);

    /**
     * @param success TODO
     * @param ex reasons: doc does not exist in server.
     * @param doc The document constructed from server responses. Should configure the Rao to construct the Document
     *            entity before calling create(). //TODO: currently not supported by underlying ReSTful Connector.
     * @param success whether the document was successfully created on the server.
     * @param statusLine
     */
    void onCreated(T res, boolean success, RaoException ex);

    void onFullyRetreived(T res, boolean success, RaoException ex);

    /**
     * This method returns a EntityWrapper.(For automatic lazy fetching)
     * 
     * @param res The partially retrieved Resource.If the resouce is a document calling for an object that is not yet
     *            retreived inside the document will throw a runtime RESTfulLazyFetchException. The object will be
     *            automatically loaded and onDocumentFull/PartiallyRetreived() will be called accordingly.Please make
     *            sure to catch the exception if you call a Object or first class entity inside the document which
     *            haven't loaded yet.
     * @param success TODO
     * @param ex reasons: this doc does not exist in the server.
     * @param args List of newly loaded components of the document. Ex: Blog.BlogPostClass/* means all BlogPostClass
     *            objs have been loaded.
     */
    void onPartiallyRetreived(T res, boolean success, RaoException ex, String... args);

    /**
     * @param rslts
     * @param success TODO
     * @param ex reasons:Interanl querry engine discovered the querry to be wrong, Unauthorized, ...
     * @param args The list of components already loaded into the documents.
     */
    void onQuerryComplete(List<T> rslts, boolean success, RaoException ex, String... args);

    /**
     * @param res The updated resource. May be the original or a clone filled with response data from server
     * @param success Whether update operation was successful
     * @param ex If not successful what the exception is. reasons 1) The document to be updated is not yet created.
     */
    void onUpdated(T res, boolean success, RaoException ex);

    /**
     * @param success
     * @param ex TODO
     */
    void onDeleted(boolean success, RaoException ex);

    /**
     * Call back when IO error occures(ex: the Internet connectivity is not available). The client should handle this
     * situation and choose whether to sync later,retry, or ignore.
     * 
     * @param e the exception passed back asynchronously to be handled by the client.
     */
    void handleException(IOException e);

}
