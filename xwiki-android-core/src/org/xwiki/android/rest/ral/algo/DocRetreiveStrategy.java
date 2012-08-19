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
import org.xwiki.android.rest.AttachmentResources;
import org.xwiki.android.rest.CommentResources;
import org.xwiki.android.rest.ObjectResources;
import org.xwiki.android.rest.PageResources;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.TagResources;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.FetchConfig;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.rest.transformation.DocumentBuilder;
import org.xwiki.android.rest.transformation.DocumentBuilder_XML;
import org.xwiki.android.xmodel.entity.Document;

public class DocRetreiveStrategy implements IDocRetreiveStrategy
{

    String username;
    String password;
    String url;
    XWikiRestConnector api; //TODO: replace with RestFulManager.getRestClient()
    PageResources pageOps;
    ObjectResources objOps;
    CommentResources cmntOps;
    TagResources tagOps;
    AttachmentResources atchOps;

    public DocRetreiveStrategy(String username, String password, String serverUrl)
    {
        this.username = username;
        this.password = password;
        this.url = serverUrl;
        api = new XWikiRestConnector(url, username, password);       
        
    }

    @Override
    public Document retreive(Document resrc) throws RestConnectionException, RaoException
    {
        return retreive(resrc.getDocumentReference());
    }

    @Override
    public Document retreive(Document resrc, FetchConfig lazyConfig)
    {
        return retreive(resrc.getDocumentReference(), lazyConfig);
    }

    @Override
    public Document retreive(DocumentReference dref) throws RestConnectionException, RaoException
    {
        if(dref.getVersion()==null){
            return retreiveCurrentVersion(dref);  
        }else{
            return retreiveInHistory(dref);
        }        
    }

    
    @Override
    public Document retreive(DocumentReference dref, FetchConfig lazyConfig)
    {
        throw new UnsupportedOperationException("Use Configurable DocRet Strategy");
    }

    private Document retreiveCurrentVersion(DocumentReference dref) throws RestConnectionException, RaoException
    {
        String wikiName = dref.getWikiName();
        String spaceName = dref.getSpaceName();
        String pageName = dref.getPageName();

        Page page = null;
        try {
            page = api.getPage(wikiName, spaceName, pageName);

            DocumentBuilder builder = new DocumentBuilder_XML(page);

            fetchObjects(builder, wikiName, spaceName, pageName);
            fetchComments(builder, wikiName, spaceName, pageName);
            fetchTags(builder, wikiName, spaceName, pageName);
            fetchAttachmentMetas(builder, wikiName, spaceName, pageName);

            return builder.build();

        } catch (RestException e) {
            e.printStackTrace();
            int code = e.getCode();
            switch (code) {
                case 401:
                    throw new RaoException("Unauthorized", e);
                case 404:
                    throw new RaoException("page not found", e);
                default:
                    throw new RaoException(e);
            }

        }
    }

    //
    // private methods
    //

    private DocumentBuilder fetchObjects(DocumentBuilder builder, String wikiName, String spaceName, String pageName)
        throws RestConnectionException, RaoException
    {
        Objects objs;
        try {
            objs = api.getAllObjects(wikiName, spaceName, pageName);
        } catch (RestException e) {
            // TODO
            e.printStackTrace();
            throw new RaoException(e);
        }
        for (ObjectSummary obSummary : objs.getObjectSummaries()) {
            if (obSummary.className.equals("XWiki.XWikiComments") || obSummary.className.equals("XWiki.TagClass")) {
                continue; // skip comment, tag objects.
            } else {
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

    private DocumentBuilder fetchComments(DocumentBuilder builder, String wikiName, String spaceName, String pageName)
        throws RestConnectionException, RaoException
    {
        Comments cmnts;
        try {
            cmnts = api.getPageComments(wikiName, spaceName, pageName);
            for (Comment c : cmnts.getComments()) {
                builder.withComment(c);
            }
            return builder;
        } catch (RestException e) {
            // TODO
            e.printStackTrace();
            throw new RaoException(e);
        }
    }

    private DocumentBuilder fetchTags(DocumentBuilder builder, String wikiName, String spaceName, String pageName)
        throws RestConnectionException, RaoException
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

    private DocumentBuilder fetchAttachmentMetas(DocumentBuilder builder, String wikiName, String spaceName,
        String pageName) throws RestConnectionException, RaoException
    {
        Attachments as;
        try {
            as = api.getAllPageAttachments(wikiName, spaceName, pageName);
            for (Attachment a : as.getAttachments()) {
                builder.withAttachment(a);
            }
            return builder;
        } catch (RestException e) {
            // TODO
            e.printStackTrace();
            throw new RaoException(e);
        }

    }
    
    
    
    /**
     * Default retrieve for a history doc version
     * @param dref
     * @return
     * @throws RestConnectionException
     * @throws RaoException
     */
    
    private Document retreiveInHistory(DocumentReference dref)throws RestConnectionException, RaoException
    {
        throw new UnsupportedOperationException("XWiki does not fully support fetching a document[objects+comments+...all] in history." +
        		"You may fetch the page in history using fetchConfig. "
            );
//        String wikiName = dref.getWikiName();
//        String spaceName = dref.getSpaceName();
//        String pageName = dref.getPageName();
//        String version =dref.getVersion();
//
//        Page page = null;
//        try {
//            page = api.getPageInVersionHistory(wikiName, spaceName, pageName, version);
//
//            DocumentBuilder builder = new DocumentBuilder_XML(page);
//
//            fetchObjectsInHistory(builder, wikiName, spaceName, pageName, version);
//            fetchCommentsInHistory(builder, wikiName, spaceName, pageName, version);
//            fetchTagsInHistory(builder, wikiName, spaceName, pageName, version);
//            fetchAttachmentMetasInHistory(builder, wikiName, spaceName, pageName, version);
//
//            return builder.build();
//
//        } catch (RestException e) {
//            e.printStackTrace();
//            int code = e.getCode();
//            switch (code) {
//                case 401:
//                    throw new RaoException("Unauthorized", e);
//                case 404:
//                    throw new RaoException("page not found", e);
//                default:
//                    throw new RaoException(e);
//            }
//
//        }
        
    }
    
    
//    private DocumentBuilder fetchObjectsInHistory(DocumentBuilder builder, String wikiName, String spaceName, String pageName, String version)
//        throws RestConnectionException, RaoException
//    {
//        Objects objs;
//        try {
//            objs = api.objectOperations(wikiName, spaceName, pageName);
//        } catch (RestException e) {
//            // TODO
//            e.printStackTrace();
//            throw new RaoException(e);
//        }
//        for (ObjectSummary obSummary : objs.getObjectSummaries()) {
//            if (obSummary.className.equals("XWiki.XWikiComments") || obSummary.className.equals("XWiki.TagClass")) {
//                continue; // skip comment, tag objects.
//            } else {
//                Object res;
//                try {
//                    res = api.getObject(wikiName, spaceName, pageName, obSummary.className, obSummary.number);
//                    builder.withObject(res);
//                } catch (RestException e) {
//                    // TODO
//                    e.printStackTrace();
//                    throw new RaoException(e);
//                }
//            }
//        }
//
//        return builder;
//    }
//
//    private DocumentBuilder fetchCommentsInHistory(DocumentBuilder builder, String wikiName, String spaceName, String pageName, String version)
//        throws RestConnectionException, RaoException
//    {
//        Comments cmnts;
//        try {
//            cmnts = api.getPageComments(wikiName, spaceName, pageName);
//            for (Comment c : cmnts.getComments()) {
//                builder.withComment(c);
//            }
//            return builder;
//        } catch (RestException e) {
//            // TODO
//            e.printStackTrace();
//            throw new RaoException(e);
//        }
//    }
//
//    private DocumentBuilder fetchTagsInHistory(DocumentBuilder builder, String wikiName, String spaceName, String pageName, String version)
//        throws RestConnectionException, RaoException
//    {
//        Tags tags;
//        try {
//            tags = api.getPageTags(wikiName, spaceName, pageName);
//            builder.withTags(tags);
//            return builder;
//        } catch (RestException e) {
//            // TODO
//            e.printStackTrace();
//            throw new RaoException(e);
//        }
//    }
//
//    private DocumentBuilder fetchAttachmentMetasInHistory(DocumentBuilder builder, String wikiName, String spaceName,
//        String pageName, String version) throws RestConnectionException, RaoException
//    {
//        Attachments as;
//        try {
//            as = api.getAllPageAttachments(wikiName, spaceName, pageName);
//            for (Attachment a : as.getAttachments()) {
//                builder.withAttachment(a);
//            }
//            return builder;
//        } catch (RestException e) {
//            // TODO
//            e.printStackTrace();
//            throw new RaoException(e);
//        }
//
//    }
//
//
}
