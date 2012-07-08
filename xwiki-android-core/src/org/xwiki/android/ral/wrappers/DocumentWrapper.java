package org.xwiki.android.ral.wrappers;

import org.xwiki.android.xmodel.entity.DocumentBase;

public interface DocumentWrapper // extends DocumentInterface
{
    DocumentBase unWrap();

    DocumentWrapper unWrapOnce();
}
