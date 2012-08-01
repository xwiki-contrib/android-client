package org.xwiki.android.data.fileStore;

import java.util.List;
import java.util.Map;

import org.xwiki.android.xmodel.entity.Document;

/**
 * @author xwiki gsoc 2012 non public. Use mngr to get.
 */
public interface DocumentFao extends Fao<Document>
{
	@Override
	FSDocumentReference save(Document entity, String tag);
    Document load(FSDocumentReference ref);
    boolean delete(FSDocumentReference ref);
   
    List<FSDocumentReference> listBySpace(String spaceName);
    List<FSDocumentReference> listByTag(String tag);
    List<FSDocumentReference> listByFieldValues(Map<String,Object> fld_val);
}
