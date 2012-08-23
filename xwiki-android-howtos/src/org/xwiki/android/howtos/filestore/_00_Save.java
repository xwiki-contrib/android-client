package org.xwiki.android.howtos.filestore;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.xmodel.entity.Document;

public class _00_Save
{
    void save()
    {
        XWikiApplicationContext ctx = XWikiApplicationContext.getInstance();
        FileStoreManager fm = ctx.getFileStoreManager();
        DocumentFao fao = fm.getDocumentFao();
        
        
        Document doc=new Document("WikiName", "spaceName", "pageName");
        FSDocumentReference ref = fao.save(doc, "sas");
    }

}
