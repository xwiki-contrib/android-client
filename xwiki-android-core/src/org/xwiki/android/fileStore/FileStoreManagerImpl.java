package org.xwiki.android.fileStore;

import android.content.Context;

public class FileStoreManagerImpl implements FileStoreManager{
	private Context ctx;
	
	/**
	 * use @link{org.xwiki.android.context.XWikiApplicationContext} to get an instance
	 * @param appCtx
	 */
	FileStoreManagerImpl(Context appCtx){
		this.ctx=appCtx;
	}
	
	
}
