package org.xwiki.android.fileStore;

import java.io.File;

import org.xwiki.android.xmodel.entity.DocumentBase;

public interface FileStoreManager
{

    File getFileStoreDir();

    /**
     * @return an Implementation of Fao<Document> *
     */
    DocumentFao getDocumentFao();
}
