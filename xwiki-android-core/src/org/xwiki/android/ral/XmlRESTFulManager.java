package org.xwiki.android.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public class XmlRESTFulManager implements RESTfulManager
{

    public DocumentRao newDocumentRao()
    {
        return new XmlDocumentRao();
    }

    public SpaceRao newSpaceRao()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

    public WikiRao newWikiRao()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

}
