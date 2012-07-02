package org.xwiki.android.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public interface RESTfulManager {
	////generic method , <D extends Rao<T>,T> D getRao(Class<T> raocls, RaoCallback<T> callback); 
	
	Rao<Document> newDocumentRao(RaoCallback<Document> callback);
	Rao<Space>    newSpaceRao(RaoCallback<Space> callback);
	Rao<Wiki>     newWikiRao(RaoCallback<Wiki> callback);
}
