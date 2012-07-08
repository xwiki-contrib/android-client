package demo_tutorials;

import java.util.List;
import java.util.Map;

import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.svc.DocumentLocalSvcCallbacks;
import org.xwiki.android.xmodel.svc.DocumentLocalSvcs;
import org.xwiki.android.xmodel.svc.DocumentSvcImpl;

import android.app.Activity;

public class _05_ListAndLoadDocuments extends Activity
{

    FSDocumentReference loadMe;
    DocumentLocalSvcs ds;

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
