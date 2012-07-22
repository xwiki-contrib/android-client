package org.xwiki.android.ral.wrappers;

import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.DocumentBase;

public abstract class DocumentWrapper // extends DocumentInterface
{
   
    private Document doc;
    
    public DocumentWrapper(Document doc)
    {
        this.doc=doc;
    }
    
    public  Document unWrap(){
        return doc;
    }

    public  void wrap(Document d){
        doc=d;
    }
}
