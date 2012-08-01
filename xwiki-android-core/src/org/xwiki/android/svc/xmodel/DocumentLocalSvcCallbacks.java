package org.xwiki.android.svc.xmodel;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import android.os.Handler;
import android.os.Looper;

public abstract class DocumentLocalSvcCallbacks
{

    protected final Handler mhandle;

    protected Object[] args;

    /**
     * binds the Callback with the UI threads looper.
     */
    public DocumentLocalSvcCallbacks()
    {
        Looper mainLooper = Looper.getMainLooper(); // reference to the main threads looper in
                                                    // MainThread's Thread Local Storage.
        mhandle = new Handler(mainLooper);
    }

    public DocumentLocalSvcCallbacks(Object... args)
    {
        this();
        this.args = args;
    }

    /**
     * Binds the callback with the given looper. You will not be able to update UI from the call back if not bound to
     * the UI threads looper.
     * 
     * @param looper
     */
    public DocumentLocalSvcCallbacks(Looper looper)
    {
        mhandle = new Handler(looper);
    }

    //
    // For file store async access.
    //

    public void onLoadComplete(Document entity)
    {

    }

    public void onSaveComplete(File savedto)
    {

    }

    public void onDeleteComplete(boolean success)
    {

    }

    public void onDeleteAllComplete(List<FSDocumentReference> failures)
    {

    }

    public void onListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby)
    {

    }

    // Note: IO exceptions for files are not handled by client.(yet)

    // non public invoker methods for file store ansync access callbacks.

    public void invokeLoadComplete(Document entity)
    {
        mhandle.post(new RunnableWiP(entity)
        {
            @Override
            public void run()
            {
                onLoadComplete((Document) args[0]);
            }
        });
    }

    public void invokeSaveComplete(File savedto)
    {
        mhandle.post(new RunnableWiP(savedto)
        {
            @Override
            public void run()
            {
                onSaveComplete((File) args[0]);
            }
        });
    }

    public void invokeDeleted(boolean success)
    {
        mhandle.post(new RunnableWiP(success)
        {
            @Override
            public void run()
            {
                onDeleteComplete((Boolean) args[0]);
            }
        });
    }

    public void invokeDeleteAllComplete(List<FSDocumentReference> failures)
    {
        mhandle.post(new RunnableWiP(failures)
        {
            @Override
            public void run()
            {
                onDeleteAllComplete((List<FSDocumentReference>)args[0]);
            }
        });
    }

    public void invokeListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby)
    {
        mhandle.post(new RunnableWiP(rslts, matchedby)
        {
            @Override
            public void run()
            {
                onListingComplete((List<FSDocumentReference>) args[0], (Map<String, Object>) args[1]);
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
