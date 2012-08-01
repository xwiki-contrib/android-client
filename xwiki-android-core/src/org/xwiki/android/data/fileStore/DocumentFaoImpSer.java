package org.xwiki.android.data.fileStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.rdb.EntityManager;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

/**
 * @author xwiki gsoc 2012 Document Fao Implementation . Method: java
 *         Serialization.
 */
class DocumentFaoImpSer implements DocumentFao
{

    private final String TAG="Document FAO";
    
	private XWikiApplicationContext ctx;
	private final File FSDIR;
	
	private static final String tag="DocumentFao";//loggin tag

	public DocumentFaoImpSer(XWikiApplicationContext ctx, FileStoreManager mngr)
	{
		this.ctx = ctx;
		FSDIR = mngr.getFileStoreDir();
	}

	@Override
	public FSDocumentReference save(Document doc, String tag)
	{
		File f;
		String wikiName = doc.getWikiName();
		String spaceName = doc.getSpaceName();
		String pageName = doc.getPageName();
		String dirPath = FSDIR.getAbsolutePath() + wikiName + "/" + spaceName + "/" + pageName;
		String filePath=dirPath+"/doc.ser";
		f = new File(filePath);
		boolean success=true;

		FSDocumentReference fsref = new FSDocumentReference();
		// doc ref data
		DocumentReference docref = doc.getDocumentReference();
		docref.setServerName(ctx.getUserSession().getRealm());
		fsref.copyfrom(docref);
		// fs data
		fsref.setTag(tag);
		fsref.setFile(f);

		EntityManager em = ctx.newEntityManager();
		Dao<FSDocumentReference, Integer> dao = null;
		try {
			dao = em.getDao(FSDocumentReference.class);
			dao.create(fsref);
			em.close();
		} catch (SQLException e) {//thrown for duplicate saves. Just ignore file is allowed to be overwritten.	Make sure to return the fsref saved in the DB.		    
            try {
                dao = em.getDao(FSDocumentReference.class);
                Map< String , Object > flds=new Hashtable<String, Object>();
                flds.put("pageName", fsref.getPageName());
                flds.put("spaceName", fsref.getSpaceName());
                flds.put("wikiName", fsref.getWikiName());                
                List<FSDocumentReference> lst = dao.queryForFieldValues(flds);
                if(!lst.isEmpty()){
                    fsref= lst.get(0); 
                    Log.d(TAG,"Doc already saved "+ fsref.getPageName());
                    return fsref;
                }else{
                    throw new RuntimeException("error in logic");
                }
            } catch (SQLException e1) {               
                Log.e(TAG, "error while retreiving already saved doc's doc ref");
            }		    
		}

		ObjectOutputStream oos;
		try {
		    File dir=new File(dirPath);
		    dir.mkdirs();
			f.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(doc);
			
			//default location should always be available. So catch the exceptions.
			//Only for a save(Document doc,String tag, File f) type save m() you should throw the IOException.
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success=false;
		} catch (IOException e) {
			e.printStackTrace();
			success=false;
		}
		
		if(success){
			return fsref;
		}
		else{
			return null;
		}
	}

	@Override
	public Document load(FSDocumentReference ref)
	{
		File f = ref.getFile();		
		return load(f);
	}

	@Override
	public Document load(File file)
	{
		ObjectInputStream ois;
		Document doc = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			doc = ((Document) ois.readObject());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public List<FSDocumentReference> listBySpace(String spaceName)
	{
		EntityManager em = ctx.newEntityManager();
		List<FSDocumentReference> list = null;
		try {
			Dao<FSDocumentReference, Integer> dao = em.getDao(FSDocumentReference.class);
			list = dao.queryForEq("spaceName", spaceName);
			em.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<FSDocumentReference> listByTag(String tag)
	{
		EntityManager em = ctx.newEntityManager();
		List<FSDocumentReference> list = null;
		try {
			Dao<FSDocumentReference, Integer> dao = em.getDao(FSDocumentReference.class);
			list = dao.queryForEq("tag", tag);
			em.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Document> list(String tag)
	{
		EntityManager em = ctx.newEntityManager();
		List<FSDocumentReference> fsrlist = null;
		List<Document> doclist = new ArrayList<Document>();
		try {
			Dao<FSDocumentReference, Integer> dao = em.getDao(FSDocumentReference.class);
			fsrlist = dao.queryForEq("tag", tag);
			em.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (FSDocumentReference fsr : fsrlist) {
			doclist.add(load(fsr));
		}
		return doclist;
	}

	@Override
	public List<FSDocumentReference> listByFieldValues(Map<String, Object> fld_val)
	{

		EntityManager em = ctx.newEntityManager();
		List<FSDocumentReference> list = null;
		try {
			Dao<FSDocumentReference, Integer> dao = em.getDao(FSDocumentReference.class);
			list = dao.queryForFieldValues(fld_val);
			em.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
   

    @Override
    public boolean delete(FSDocumentReference ref)
    {        
       EntityManager em=ctx.newEntityManager(); 
       try {
           Dao<FSDocumentReference, Integer> dao = em.getDao(FSDocumentReference.class);
           if(ref.get_id()>0){
               dao.deleteById(ref.get_id());
           }else{
               //dao.queryForMatching(matchObj)          
               dao.delete(ref); 
           }
           em.close();
           File f = ref.getFile();
           return f.delete();
       } catch (SQLException e) {
           Log.e(TAG, e.getMessage());          
           return false;
       }
    }

}
