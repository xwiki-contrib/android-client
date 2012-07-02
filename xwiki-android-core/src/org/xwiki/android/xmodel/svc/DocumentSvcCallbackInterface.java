package org.xwiki.android.xmodel.svc;

import java.util.List;

import org.xwiki.android.ral.RaoCallback;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.reference.DocumentReference;
import org.xwiki.android.xmodel.reference.EntityReference;

public interface DocumentSvcCallbackInterface extends RaoCallback<Document>{
	
	void onLoadComplete(Document entity);
	void onSaveComplete(boolean success);
	void onSearchComplete(List<Document> rslts);
	
}
