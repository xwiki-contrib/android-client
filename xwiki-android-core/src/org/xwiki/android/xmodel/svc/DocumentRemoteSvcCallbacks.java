package org.xwiki.android.xmodel.svc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.ral.RaoCallbackForDocument;
import org.xwiki.android.ral.RaoException;
import org.xwiki.android.xmodel.entity.Document;


/**
 * @author xwiki gsoc 2012
 */
public abstract class DocumentRemoteSvcCallbacks extends RaoCallbackForDocument
{

    //
    // -------------------------------client should override the needed call
    // backs.
    //
    // RAO methods.

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#onProgressUpdate(int)
     */
    @Override
    public void onProgressUpdate(int progress)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#onCreated(java.lang.Object, boolean, int)
     */
    @Override
    public void onCreated(Document res, boolean success, RaoException ex)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#ontFullyRetreived(java.lang.Object)
     */
    @Override
    public void onFullyRetreived(Document res, boolean success, RaoException ex)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#onPartiallyRetreived(java.lang.Object, java.lang.String[])
     */
    @Override
    public void onPartiallyRetreived(Document res, boolean success, RaoException ex, String... args)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#onQuerryComplete(java.util.List, java.lang.String[])
     */
    @Override
    public void onQuerryComplete(List<Document> rslts, boolean success, RaoException ex, String... args)
    {

    }

    @Override
    public void onUpdated(Document res, boolean success, RaoException ex)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#onDeleted(boolean)
     */
    @Override
    public void onDeleted(boolean success, RaoException ex)
    {

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.ral.RaoCallback#handleOffline(java.lang.Object)
     */
    @Override
    public void handleException(IOException e)
    {

    }
    
   
}
