package org.xwiki.android.svc.cmn;

import java.io.File;
import java.sql.SQLException;


import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.data.rdb.EntityManager;
import org.xwiki.android.entity.SyncOutEntity;
import org.xwiki.android.svcbg.SyncDaemon;
import org.xwiki.android.xmodel.entity.Document;

import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

/**
 * 
 * @author xwiki gsoc 2012
 * Marks a FSDocumentReference to be syned out.
 */
public class SyncService
{
	
	private final String TAG = this.getClass().getSimpleName();
	
    private FileStoreManager fsmngr;
	private EntityManager emngr;
	
	public SyncService(){
		XWikiApplicationContextAPI ctx=XWikiApplicationContext.getInstance();
		fsmngr=ctx.getFileStoreManager();
		emngr=ctx.newEntityManager();
	}
	
	/**
	 * Saves the document in default filestore location for documents,and sends the doc to the server later.
	 * @param doc
	 */
	public void SyncOutLater(Document doc){
		DocumentFao fao=fsmngr.getDocumentFao();		
		FSDocumentReference ref=fao.save(doc, "Sync");
		SyncOutLater(ref);		
	}
	
	/**
	 * Sync out the document refered by ref.
	 * Document should have been presaved.
	 * @param doc
	 */
	public void SyncOutLater(FSDocumentReference ref){
		SyncOutEntity se=new SyncOutEntity(ref);
		try {
			Dao<SyncOutEntity, Integer>dao=emngr.getDao(SyncOutEntity.class);
			dao.create(se);
			emngr.close();				
		} catch (SQLException e) {
		    Log.e(TAG,"possible: sync entity alredy created",e);			
		}
		
		//start bg svc
        XWikiApplicationContext ctx=XWikiApplicationContext.getInstance();
        ctx.startService(new Intent(ctx,SyncDaemon.class));		
	}
}
