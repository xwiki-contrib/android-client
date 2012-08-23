package org.xwiki.android.howtos.filestore;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.xmodel.entity.Document;

public class _01_ListAndLoad
{
    void load(){
        XWikiApplicationContext ctx = XWikiApplicationContext.getInstance();
        FileStoreManager fm = ctx.getFileStoreManager();
        DocumentFao fao = fm.getDocumentFao();
        
        
       
        FSDocumentReference ref=fao.listByTag("my File Store tag. ").get(0); //file store supports tagging to search documents.
        //listBy... returns a list of references;
        Document doc=fao.load(ref);//ok we loaded it.
        
    }
}
