package org.xwiki.android.data.fileStore;

import java.io.File;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.xmodel.entity.XWikiPage;

import android.content.Context;

public class FileStoreManagerImpl implements FileStoreManager
{
    // static final File FS_DIR;
    // static {
    // //the context should have been loaded.Class.forName("XWikiApplicationContext");
    // XWikiApplicationContext ctx=XWikiApplicationContext.getInstance();
    // FS_DIR=ctx.getFilesDir();
    // }
    // TODO: make singleton after implementation is firm.

    private final File FSDIR;

    private XWikiApplicationContext ctx;

    /**
     * use @link{org.xwiki.android.context.XWikiApplicationContext} to get an instance
     * 
     * @param appCtx
     */
    public FileStoreManagerImpl(XWikiApplicationContext appCtx)
    {
        this.ctx = appCtx;
        String fsdirpath = ctx.getFilesDir().getAbsolutePath() + "/FileStore";
        this.FSDIR = new File(fsdirpath);
    }

    @Override
    public DocumentFao getDocumentFao()
    {// here get (rather than new) means you can return the same instance without consequences.
        return new DocumentFaoImpSer(ctx, this);
    }

    @Override
    public File getFileStoreDir()
    {
        return FSDIR;
    }

}
