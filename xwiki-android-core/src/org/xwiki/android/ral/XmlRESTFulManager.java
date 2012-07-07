package org.xwiki.android.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public class XmlRESTFulManager implements RESTfulManager
{

    public Rao<Document> newDocumentRao(RaoCallback<Document> callback)
    {
        return new XmlDocumentRao((RaoCallbackForDocument) callback);
    }

    public Rao<Space> newSpaceRao(RaoCallback<Space> callback)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

    public Rao<Wiki> newWikiRao(RaoCallback<Wiki> callback)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

}
