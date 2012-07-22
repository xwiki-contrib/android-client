package org.xwiki.android.xmodel.svc;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.fileStore.DocumentFao;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.ral.DocumentRao;
import org.xwiki.android.ral.RESTfulManager;
import org.xwiki.android.ral.RaoException;
import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.rest.RestConnectorException;
import org.xwiki.android.xmodel.entity.Document;

/**
 * @author xwiki gsoc 2012 version 1.0
 */
public class DocumentSvcImpl implements DocumentSvc
{

	XWikiApplicationContext ctx;

	RESTfulManager rMngr;

	FileStoreManager fMngr;

	public DocumentSvcImpl()
	{
		ctx = XWikiApplicationContext.getInstance();
		rMngr = ctx.newRESTfulManager();
		fMngr = ctx.getFileStoreManager();
	}

	//
	// Rest
	//

	public void create(Document d, DocumentRemoteSvcCallbacks callback)
	{
		Thread t = new Worker(d,callback)
		{
			public void run()
			{
				Document doc = (Document) args[0];
				DocumentRao rao = rMngr.newDocumentRao();
				DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
				Document d;
				try {
					d = rao.create(doc);
					clbk.invokeCreated(d,true,null);
				} catch (RestConnectorException e) {
					clbk.handleException(e);
				} catch (RaoException e) {
					clbk.invokeCreated(doc,false,e);
				}				
			}
		};
		t.start();		
	}

	public void retreive(DocumentReference dref, DocumentRemoteSvcCallbacks clbk)
	{
		DocumentRao rao= rMngr.newDocumentRao();
		//rao.retreive(dref);

	}

	public void update(Document d, DocumentRemoteSvcCallbacks clbk)
	{
		DocumentRao rao = rMngr.newDocumentRao();
		//rao.update(d);
	}

	public void delete(DocumentReference dref, DocumentRemoteSvcCallbacks clbk)
	{
		Thread t = new Worker(dref,clbk)
		{
			public void run()
			{
				DocumentReference dref = (DocumentReference) args[0];
				DocumentRao rao = rMngr.newDocumentRao();
				DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
				Document d;
				try {
					rao.delete(dref);
					clbk.invokeDeleted(true,null);
				} catch (RestConnectorException e) {
					clbk.handleException(e);
				} catch (RaoException e) {
					clbk.invokeDeleted(false, e);
				}				
			}
		};
		t.start();		
		
	}

	// Local File Store functions. Document Local Services.
	//
	//

	public void save(Document doc, String tag, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(doc, tag, clbk)
		{
			public void run()
			{
				Document doc = (Document) args[0];
				String tag = (String) args[1];
				DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[2];

				DocumentFao fao = fMngr.getDocumentFao();
				FSDocumentReference fref = fao.save(doc, tag);
				File f=fref.getFile();
				if (clbk != null) {
					clbk.invokeSaveComplete(f);
				}

			};
		};
		t.start();
	}

	public void load(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(fsref, clbk)
		{
			public void run()
			{
				FSDocumentReference fsref = (FSDocumentReference) args[0];
				DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[1];

				DocumentFao fao = fMngr.getDocumentFao();
				Document doc = fao.load(fsref);
				if (clbk != null) {
					clbk.invokeLoadComplete(doc);
				}

			};
		};
		t.start();

	}

	@Override
	public void listBySpace(String spaceName, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(spaceName, clbk)
		{
			public void run()
			{
				String spaceName = (String) args[0];
				DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[1];

				DocumentFao fao = fMngr.getDocumentFao();
				List<FSDocumentReference> list = fao.listBySpace(spaceName);
				if (clbk != null) {
					Map<String, Object> matchedby = new Hashtable<String, Object>(1);
					matchedby.put("spaceName", spaceName);
					clbk.invokeListingComplete(list, matchedby);
				}

			};
		};
		t.start();
	}

	@Override
	public void listByTag(String tag, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(tag, clbk)
		{
			public void run()
			{
				String tag = (String) args[0];
				DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[1];

				DocumentFao fao = fMngr.getDocumentFao();
				List<FSDocumentReference> list = fao.listByTag(tag);
				if (clbk != null) {
					Map<String, Object> matchedby = new Hashtable<String, Object>(1);
					matchedby.put("tag", tag);
					clbk.invokeListingComplete(list, matchedby);
				}

			};
		};
		t.start();
	}

	//public void listByFieldValues(Map<String,Object> fld_val)
	
	private abstract class Worker extends Thread
	{
		Object[] args;

		public Worker(Object... args)
		{
			this.args = args;
		}

		public abstract void run();

	}

}
