package org.xwiki.android.xmodel.svc;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.ral.RaoCallbackForDocument;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.reference.DocumentReference;
import org.xwiki.android.xmodel.reference.EntityReference;

import android.os.Handler;
import android.os.Looper;
/**
 * 
 * @author xwiki gsoc 2012
 *
 */
public abstract class DocumentSvcCallback extends RaoCallbackForDocument implements DocumentSvcCallbackInterface {
	
	
	//
	//-------------------------------interface methods overridden for ease. !!! Client should override the needed call backs.
	//
	// RAO methods.
	
	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#onProgressUpdate(int)
	 */
	@Override
	public void onProgressUpdate(int progress) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#onCreated(java.lang.Object, boolean, int)
	 */
	@Override
	public void onCreated(Document res, boolean success, String statusLine) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#ontFullyRetreived(java.lang.Object)
	 */
	@Override
	public void ontFullyRetreived(Document res) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#onPartiallyRetreived(java.lang.Object, java.lang.String[])
	 */
	@Override
	public void onPartiallyRetreived(Document res, String... args) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#onQuerryComplete(java.util.List, java.lang.String[])
	 */
	@Override
	public void onQuerryComplete(List<Document> rslts, String... args) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#onDeleted(boolean)
	 */
	@Override
	public void onDeleted(boolean success) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xwiki.android.ral.RaoCallback#handleOffline(java.lang.Object)
	 */
	@Override
	public void handleException(IOException e) {
		// TODO Auto-generated method stub
		
	}
	
	//For file store async access.
	
	@Override
	public void onLoadComplete(Document entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSaveComplete(boolean success) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	/**
	 * @Param list of DocumentReference matching search rslts.
	 */
	public void onSearchComplete(List<Document> rslts){
		
	}
	
	//Note: IO exceptions for files are not handled by client.(yet)
}
