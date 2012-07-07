package org.xwiki.android.ral.wrappers;

import org.xwiki.android.xmodel.entity.Document;

public interface DocumentWrapper // extends DocumentInterface
{
    Document unWrap();

    DocumentWrapper unWrapOnce();
}
