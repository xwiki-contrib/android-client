package org.xwiki.android.rest.ral.wrappers;

import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.XWikiPage;

public abstract class DocumentWrapper extends Document 
{
   
    private Document doc;
    
    public DocumentWrapper(Document doc)
    {
        super(null,null,null);
    	this.doc=doc;        
    }
    
    /**
     * 
     * @return either a Document or a DocumentWrapper.
     */
    public  Document unwrap(){
        return doc;
    }

    public  void wrap(Document d){
        doc=d;
    }
    
    /**
     * 
     * @return always the real document at the root.
     */
    public Document unwrapAll(){
    	Document d=doc;
    	while(d instanceof DocumentWrapper){
    		d=((DocumentWrapper)d).unwrap();
    	}
    	return d;   	
    }
}
