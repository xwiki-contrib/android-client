package org.xwiki.android.rest.ral.algo;



import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.ObjectSummary;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.XWikiRestfulAPI;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.transformation.DocumentBuilder;
import org.xwiki.android.rest.transformation.DocumentBuilder_XML;
import org.xwiki.android.xmodel.entity.Document;

public class DocRetreiveStrategy implements IDocRetreiveStrategy
{

    String username;
    String password;
    String url; 
    XWikiRestfulAPI api;
    
    public DocRetreiveStrategy(String username, String password, String serverUrl)
    {       
        this.username = username;
        this.password = password;
        this.url = serverUrl;
        api=new XWikiRestConnector(url, username, password);
    }

    @Override
    public Document retreive(Document resrc) throws RestConnectionException, RaoException
    {        
        return retreive(resrc.getDocumentReference());
    }

    @Override
    public Document retreive(Document resrc, int parts)
    {       
        return retreive(resrc.getDocumentReference(),parts);
    }

    @Override
    public Document retreive(Document resrc, int parts, String... objTypeArgs)
    {        
        return retreive(resrc.getDocumentReference(), parts, objTypeArgs);
    }

    @Override
    public Document retreive(DocumentReference dref) throws RestConnectionException, RaoException
    {
        String wikiName=dref.getWikiName();
        String spaceName=dref.getSpaceName();
        String pageName=dref.getPageName();        
       
        Page page=null;
        try {
            page = api.getPage(wikiName, spaceName, pageName);
            
            DocumentBuilder builder=new DocumentBuilder_XML(page);          
            
            fetchObjects(builder, wikiName, spaceName, pageName);
            fetchComments(builder, wikiName, spaceName, pageName);
            fetchTags(builder, wikiName, spaceName, pageName);
            fetchAttachmentMetas(builder, wikiName, spaceName, pageName);
            return builder.build();
            
        } catch (RestException e) {            
            e.printStackTrace();
            int code=e.getCode();
            switch (code) {
                case 401:
                    throw new RaoException("Unauthorized" , e);
                case 404:
                    throw new RaoException("page not found",e);
                default : throw new RaoException(e);
            }
            
        }          
    }

    @Override
    public Document retreive(DocumentReference dref, int parts)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document retreive(DocumentReference dref, int parts, String... objTypeArgs)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
   
    //
    //private methods
    //
    
    
    private DocumentBuilder fetchObjects(DocumentBuilder builder,String wikiName,String spaceName,String pageName)throws RestConnectionException, RaoException
    {
        Objects objs;
        try {
            objs = api.getAllObjects(wikiName, spaceName, pageName);
        } catch (RestException e) {
            // TODO             
            e.printStackTrace();
            throw new RaoException(e);
        }
        for(ObjectSummary obSummary:objs.getObjectSummaries()){
            if(obSummary.className.equals("XWiki.XWikiComments") || obSummary.className.equals("XWiki.TagClass")){
                continue;   // skip comment, tag objects.         
            }else{                
                Object res;
                try {
                    res = api.getObject(wikiName, spaceName, pageName, obSummary.className, obSummary.number);
                    builder.withObject(res);
                } catch (RestException e) {
                    // TODO 
                    e.printStackTrace();
                    throw new RaoException(e);
                }                
            }
        }  
        
        return builder;        
    }
    
    private DocumentBuilder fetchComments(DocumentBuilder builder,String wikiName,String spaceName,String pageName)throws RestConnectionException, RaoException
    {
        Comments cmnts;
        try {
            cmnts = api.getPageComments(wikiName, spaceName, pageName);
            for(Comment c:cmnts.getComments()){
                builder.withComment(c);
            }        
            return builder;
        } catch (RestException e) {
            // TODO
            e.printStackTrace();
            throw new RaoException(e);
        }      
    }
    
    private DocumentBuilder fetchTags(DocumentBuilder builder,String wikiName,String spaceName,String pageName)throws RestConnectionException, RaoException
    {
        Tags tags;
        try {
            tags = api.getPageTags(wikiName, spaceName, pageName);
            builder.withTags(tags);               
            return builder; 
        } catch (RestException e) {
            // TODO
            e.printStackTrace();
            throw new RaoException(e);
        }    
    }
    
    private DocumentBuilder fetchAttachmentMetas(DocumentBuilder builder,String wikiName,String spaceName,String pageName)throws RestConnectionException, RaoException
    {
        Attachments as;
        try {
            as = api.getAllPageAttachments(wikiName, spaceName, pageName);
            for(Attachment a:as.getAttachments()){
                builder.withAttachment(a);
            }        
            return builder; 
        } catch (RestException e) {
            // TODO 
            e.printStackTrace();
            throw new RaoException(e);
        }
               
    }
    
    
}
