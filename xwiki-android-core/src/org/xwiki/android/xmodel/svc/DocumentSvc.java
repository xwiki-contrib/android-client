package org.xwiki.android.xmodel.svc;

import java.util.List;

import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.rest.IllegalRestUsageException;
import org.xwiki.android.xmodel.entity.DocumentBase;
/**
 * 
 * Combines remote,local services.
 *
 */
public interface DocumentSvc extends DocumentLocalSvcs,DocumentRemoteSvcs
{
}
