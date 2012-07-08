package demo_tutorials;

import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.svc.DocumentLocalSvcs;
import org.xwiki.android.xmodel.svc.DocumentSvcImpl;

public class _03_SaveDocument
{
     public void demo(){
        
        Document mydoc=new Document("wikiName", "spaceName", "pageName");//create empty document        
        // ... edit mydoc
        
        //lets save it!
        
        DocumentLocalSvcs docsvcl=new DocumentSvcImpl();
        
        docsvcl.save(mydoc, "My tag to identify this easily", null);//docsvcl.save(doc, tag, clbk). we pass null for clbk
                                                                    // since we do not want to know wether the doc was sucessfully
                                                                    // saved or not.
                       
    }
}
