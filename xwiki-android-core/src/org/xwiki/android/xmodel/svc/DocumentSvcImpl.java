package org.xwiki.android.xmodel.svc;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.ral.RESTfulManager;
import org.xwiki.android.ral.Rao;
import org.xwiki.android.ral.RaoCallbackForDocument;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.reference.DocumentReference;
/**
 * 
 * @author xwiki gsoc 2012
 * version 1.0
 */
public class DocumentSvcImpl implements DocumentSvc {	
	
	XWikiApplicationContext ctx;
	RESTfulManager rMngr;
	FileStoreManager fMngr;
	public DocumentSvcImpl() {
		ctx=XWikiApplicationContext.getInstance();
		rMngr=ctx.newRESTfulManager();
		fMngr=ctx.newFileStoreManager();
	}
	
	public void create(Document d, DocumentSvcCallbackInterface callback) {
		Rao<Document> rao=rMngr.newDocumentRao(callback);
		rao.create(d);		
	}
	
	public void retreive(DocumentReference dref,DocumentSvcCallbackInterface clbk) {
		Rao<Document> rao=rMngr.newDocumentRao(clbk);
		rao.retreive(dref);	
		
	}
	
	public void update(Document d, DocumentSvcCallbackInterface clbk) {
		Rao<Document> rao=rMngr.newDocumentRao(clbk);
		rao.update(d);		
	}
	
	public void delete(DocumentReference dref, DocumentSvcCallbackInterface clbk) {
		Rao<Document> rao=rMngr.newDocumentRao(clbk);
		rao.delete(dref);			
	}
	
	public void save(Document doc, DocumentSvcCallbackInterface clbk) {
		// TODO Auto-generated method stub
		//fMngr.
		
	}
	
	public void load(DocumentReference d, DocumentSvcCallbackInterface clbk) {
		// TODO Auto-generated method stub
		//fMngr.
	}
	
	

	
}
