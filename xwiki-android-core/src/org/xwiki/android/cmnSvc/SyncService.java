package org.xwiki.android.cmnSvc;

import java.io.File;
import java.sql.SQLException;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.entity.SyncOutEntity;
import org.xwiki.android.fileStore.DocumentFao;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.xmodel.entity.Document;

import com.j256.ormlite.dao.Dao;

/**
 * 
 * @author xwiki gsoc 2012
 * Marks a FSDocumentReference to be syned out.
 */
public class SyncService
{
	
	private FileStoreManager fsmngr;
	private EntityManager emngr;
	
	public SyncService(){
		XWikiApplicationContextAPI ctx=XWikiApplicationContext.getInstance();
		fsmngr=ctx.getFileStoreManager();
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
			e.printStackTrace();
		}
		
	}
}
