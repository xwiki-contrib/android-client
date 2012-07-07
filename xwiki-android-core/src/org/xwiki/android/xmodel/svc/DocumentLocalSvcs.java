package org.xwiki.android.xmodel.svc;

import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentLocalSvcs
{
    void save(Document doc, String tag, DocumentLocalSvcCallbacks clbk);

    void load(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk);    

    void listBySpace(String spaceName, DocumentLocalSvcCallbacks clbk);
    
    void listByTag(String tag,DocumentLocalSvcCallbacks clbk);
}
