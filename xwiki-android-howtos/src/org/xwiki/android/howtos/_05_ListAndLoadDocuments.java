package org.xwiki.android.howtos;

import java.util.List;
import java.util.Map;

import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcs;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.entity.Document;

import android.app.Activity;

public class _05_ListAndLoadDocuments extends Activity
{

    FSDocumentReference loadMe;
    DocumentLocalSvcs ds;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {

        ds = new DocumentSvcImpl();

        DocumentLocalSvcCallbacks clbk1 = new DocumentLocalSvcCallbacks()
        {
            @Override
            public void onListingComplete(List<FSDocumentReference> rslts, Map<String, Object> matchedby)
            {
                // matched by will contain entry ("space", "MySpace")
                loadMe = rslts.get(0);
                loadThis(loadMe);
            }
        };

        ds.listBySpace("MySpace", clbk1);
        // after the listing is complete you will be notified in the onListingComplete.
    }

    private void loadThis(FSDocumentReference ref)
    {
        DocumentLocalSvcCallbacks clbk2 = new DocumentLocalSvcCallbacks()
        {
            @Override
            public void onLoadComplete(Document entity)
            {
                 //entity is the loaded document.
            }
        };
        ds.load(ref, clbk2);        
    }

}
