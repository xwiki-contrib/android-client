package org.xwiki.android.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public interface RESTfulManager
{    
    DocumentRao newDocumentRao();

    SpaceRao newSpaceRao();

    WikiRao newWikiRao();
}
