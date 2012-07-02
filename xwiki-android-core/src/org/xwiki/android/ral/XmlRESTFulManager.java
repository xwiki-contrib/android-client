package org.xwiki.android.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public class XmlRESTFulManager implements RESTfulManager{

	@Override
	public Rao<Document> newDocumentRao(RaoCallback<Document> callback) {
		return new XmlDocumentRao((RaoCallbackForDocument) callback);
	}

	@Override
	public Rao<Space> newSpaceRao(RaoCallback<Space> callback) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not yet supported");
		
	}

	@Override
	public Rao<Wiki> newWikiRao(RaoCallback<Wiki> callback) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not yet supported");
		
	}
	
}
