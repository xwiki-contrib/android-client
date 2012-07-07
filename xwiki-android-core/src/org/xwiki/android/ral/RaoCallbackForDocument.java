package org.xwiki.android.ral;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.xmodel.entity.Document;
import android.os.Handler;
import android.os.Looper;

public abstract class RaoCallbackForDocument implements RaoCallback<Document>
{
    protected final Handler mhandle;

    /**
     * binds the Callback with the UI threads looper.
     */
    public RaoCallbackForDocument()
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
    public RaoCallbackForDocument(Looper looper)
    {
        mhandle = new Handler(looper);
    }

    // -----------------------------//non public methods. Should onbly be
    // accessed by Raos.

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
            public void run()
            {
                onProgressUpdate((Integer) args[0]);
            }
        });
    }

    void invokeCreated(Document res)
    {
        mhandle.post(new RunnableWiP(res)
        {

            public void run()
            {
                onCreated((Document) args[0], true, null);
            }
        });
    }

    void invoketFullyRetreived(Document res)
    {
        // TODO Auto-generated method stub

    }

    void invokePartiallyRetreived(Document res, String... args)
    {
        // TODO Auto-generated method stub

    }

    void invokeQuerryComplete(List<Document> rslts, String... args)
    {
        // TODO Auto-generated method stub

    }

    void invokeDeleted(boolean success)
    {
        // TODO Auto-generated method stub

    }

    void invokeHandleException(IOException e)
    {
        // TODO Auto-generated method stub

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
