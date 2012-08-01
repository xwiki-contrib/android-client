package org.xwiki.android.svc.xmodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import android.util.Log;

/**
 * @author xwiki gsoc 2012 version 1.0
 */

public class DocumentSvcImpl implements DocumentSvc
{

	protected final String TAG = "DcoumentSvcImpl";

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

	@Override
    public void create(Document d, DocumentRemoteSvcCallbacks callback)
	{
		Thread t = new Worker(d,callback)
		{
			@Override
            public void run()
			{
				Document doc = (Document) args[0];
				DocumentRao rao = rMngr.newDocumentRao();
				DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
				Document d;
				try {
					d = rao.create(doc);
					clbk.invokeCreated(d,true,null);
				} catch (RestConnectionException e) {
					clbk.handleException(e);
				} catch (RaoException e) {
					clbk.invokeCreated(doc,false,e);
				}				
			}
		};
		t.start();		
	}

	@Override
    public void retreive(DocumentReference dref, DocumentRemoteSvcCallbacks clbk)
	{
		
	    Thread t = new Worker(dref,clbk)
        {	        
            @Override
            public void run()
            {
                DocumentReference dref = (DocumentReference) args[0];                
                DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
                
                DocumentRao rao= rMngr.newDocumentRao();
                try {
                    Document d=rao.retreive(dref);
                    clbk.invoketFullyRetreived(d,true,null);
                } catch (RestConnectionException e) {
                    clbk.invokeHandleException(e);
                    e.printStackTrace();
                } catch (RaoException e) {
                    clbk.invoketFullyRetreived(null,false,e);
                    e.printStackTrace();
                }                            
            }
        };
        t.start();  
	    
	    

	}

	@Override
    public void update(Document d, DocumentRemoteSvcCallbacks clbk)
	{
		Thread t = new Worker(d,clbk)
		{
			@Override
            public void run()
			{
				Document d = (Document) args[0];
				DocumentRao rao = rMngr.newDocumentRao();
				DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
				
				try {
					rao.update(d);
					clbk.invokeUpdated(d, true, null);
				} catch (RestConnectionException e) {
					clbk.handleException(e);
				} catch (RaoException e) {
					clbk.invokeUpdated(d, false, e);
				}				
			}
		};
		t.start();	
		DocumentRao rao = rMngr.newDocumentRao();
		try {
			rao.update(d);
		} catch (RestConnectionException e) {
			
			
		} catch (RaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
    public void delete(DocumentReference dref, DocumentRemoteSvcCallbacks clbk)
	{
		Thread t = new Worker(dref,clbk)
		{
			@Override
            public void run()
			{
				DocumentReference dref = (DocumentReference) args[0];
				DocumentRao rao = rMngr.newDocumentRao();
				DocumentRemoteSvcCallbacks clbk =(DocumentRemoteSvcCallbacks) args[1];
				Document d;
				try {
					rao.delete(dref);
					clbk.invokeDeleted(true,null);
				} catch (RestConnectionException e) {
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

	@Override
    public void save(Document doc, String tag, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(doc, tag, clbk)
		{
			@Override
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

	@Override
    public void load(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk)
	{
		Thread t = new Worker(fsref, clbk)
		{
			@Override
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
			@Override
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
			@Override
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
	
	@Override
    public void delete(FSDocumentReference fsref, DocumentLocalSvcCallbacks clbk)
    {
	    Thread t = new Worker(fsref, clbk)
        {
            @Override
            public void run()
            {
                FSDocumentReference fsref = (FSDocumentReference) args[0];
                DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[1];

                org.xwiki.android.data.fileStore.DocumentFao fao = fMngr.getDocumentFao();
                boolean success=fao.delete(fsref);
                if (clbk != null) {                    
                    clbk.invokeDeleted(success);
                }

            };
        };
        t.start();        
    }
	
	 @Override
	    public void deleteAll(Collection<FSDocumentReference> refs, DocumentLocalSvcCallbacks clbk)
	    {
	     Thread t = new Worker(refs, clbk)
	        {
	            @Override
	            public void run()
	            {
	                Collection<FSDocumentReference> col = (Collection<FSDocumentReference>) args[0];
	                DocumentLocalSvcCallbacks clbk = (DocumentLocalSvcCallbacks) args[1];
	                org.xwiki.android.data.fileStore.DocumentFao fao = fMngr.getDocumentFao();
	                List<FSDocumentReference> failures=new ArrayList<FSDocumentReference>();
	                for(FSDocumentReference ref:col){
	                    boolean success=fao.delete(ref);
	                    if(!success){
	                        failures.add(ref);
	                       Log.w(TAG, "could not dlete "+ref.getPageName());
	                    }
	                }
	                
	                
	                if (clbk != null) {                    
	                    clbk.invokeDeleteAllComplete(failures);
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

		@Override
        public abstract void run();

	}

   

    

}
