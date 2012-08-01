package org.xwiki.android.svc.xmodel;

import java.util.Collection;

import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentLocalSvcs
{
    void save(Document doc, String tag, DocumentLocalSvcCallbacks clbk);

    void load(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk);    

    void listBySpace(String spaceName, DocumentLocalSvcCallbacks clbk);
    
    void listByTag(String tag,DocumentLocalSvcCallbacks clbk);
    
    void delete(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk);
    void deleteAll(Collection<FSDocumentReference> refs, DocumentLocalSvcCallbacks clbk);
}
