package org.xwiki.android.rest.ral;

import java.util.Properties;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.rest.RestConnection;
import org.xwiki.android.rest.XWikiAPI;
import org.xwiki.android.xmodel.entity.Document;

public interface RESTfulManager
{    
    DocumentRao newDocumentRao();

    SpaceRao newSpaceRao();

    WikiRao newWikiRao();
    
    RestConnection getConnection();
    
  
   
}
