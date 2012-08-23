package org.xwiki.android.howtos;

import java.io.File;

import org.xwiki.android.svc.xmodel.DocumentLocalSvcCallbacks;
import org.xwiki.android.svc.xmodel.DocumentLocalSvcs;
import org.xwiki.android.svc.xmodel.DocumentSvcImpl;
import org.xwiki.android.xmodel.entity.Document;

public class _04_SaveDocumentAdv
{
 public void demo(){
        
        Document mydoc=new Document("wikiName", "spaceName", "pageName");//create empty document        
        // ... edit mydoc
        
           
        DocumentLocalSvcs docsvcl=new DocumentSvcImpl();        
        DocumentLocalSvcCallbacks clbk=new DocumentLocalSvcCallbacks()
        {
            @Override
            public void onSaveComplete(File savedto)
            {
                if(savedto!=null){
                    //success. Edit UI to notify user here.
                }else{
                    //oops problem. May be the device storage is full.
                }
                
            }
        };
        docsvcl.save(mydoc, "My tag to identify this easily", null);
                       
    }
}
