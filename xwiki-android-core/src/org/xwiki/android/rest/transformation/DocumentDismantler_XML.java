package org.xwiki.android.rest.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translation;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public class DocumentDismantler_XML
{

    // conversion param
    public static int PAGE = 1;
    public static int OBJECTS = 2;
    public static int COMMENTS = 4;
    public static int TAGS = 8;
    public static int ATTACHMENTS = 16;
    public static int ALL = 31;

    // public static int PAGE_AND_OBJECTS=3;

    private int parts;
    private XModelTranslator_XML translator;

    /**
     * @param parts {[PAGE]?|[OBJECTS]?|[COMMENTS]?|[TAGS]?} or ALL Ex1: make simple xml page + simple xml objects
     *            (PAGE+OBJECTS) or (PAGE|OBJECTS) Ex2: make Simplexml Attachment objects + comments + objects (first
     *            class entities derived from Object objects) (ATTACHMENTS+COMMENTS+OBJECTS)
     */
    public DocumentDismantler_XML(int parts)
    {
        this.parts = parts;
        this.translator = new XModelTranslator_XML();
    }

    public DocumentDismantler_XML()
    {
        // defalut
        parts = ALL;
        this.translator = new XModelTranslator_XML();
    }

    public DocLaunchPadForXML convertDocument(Document doc)
    {
        DocLaunchPadForXML pad = new DocLaunchPadForXML();
        // save coordinates
        DocumentReference dref = doc.getDocumentReference();
        pad.wikiName = dref.getWikiName();
        pad.spaceName = dref.getSpaceName();
        pad.pageName = dref.getPageName();

        if ((parts & OBJECTS) == OBJECTS) {
            // add new objects
            for (XSimpleObject xobj : doc.getAllNewObjects()) {
                Object smplObj = translator.toObject(xobj);
                pad.newObjects.add(smplObj);
            }
            // put altered objects
            Map<String, XSimpleObject> xobjs = doc.getAllEditedObjects();
            Set<Entry<String, XSimpleObject>> entrySet = xobjs.entrySet();
            for (Entry<String, XSimpleObject> en : entrySet) {
            	Object ores=translator.toObject(en.getValue());
                pad.editedObjects.put(en.getKey(),ores );
            }
            // mark deleted objects.
            Set<String> delObjs = doc.getDeletedObjects();
            for (String objKey : delObjs) {
                pad.deletedObjects.add(objKey);
            }
        }
        if ((parts & PAGE) == PAGE) {
            Page page = translator.toPage(doc);
            pad.page = page;
        }

        if ((parts & COMMENTS) == COMMENTS) {
            // new comments
            List<Comment> newComments = doc.getAllNewComments();
            for (Comment c : newComments) {
                org.xwiki.android.resources.Comment cres = translator.toComment(c);
                pad.newComments.add(cres);
            }
            // updates
            List<Comment> editedComments = doc.getAllEditedComments();
            for (Comment ec : editedComments) {
                Object ores=translator.toObject(ec);
                pad.editedComments.add(ores);
            }
            // deletes
            Set<Integer> deletedComments = doc.getDeletedCommentSet();
            for (int id : deletedComments) {
                String key = "XWiki.XWikiComments";
                key += "/" + id;
                pad.deletedComments.add(key);
            }

        }
        if ((parts & TAGS) == TAGS) {
            // new,update,del all in one xmlrpc method.
            Tags tgsres = null;
            if (doc.getAllNewTags() != null && !doc.getAllNewTags().isEmpty()) {
                tgsres = translator.toTags(doc.getTags());
            }
            pad.tags = tgsres;
        }
        if ((parts & ATTACHMENTS) == ATTACHMENTS) {
            // new ones, updated ones.
            List<org.xwiki.android.xmodel.entity.Attachment> newlist = doc.getAllNewAttachments();
            List<org.xwiki.android.xmodel.entity.Attachment> edlist = doc.getAllEditedAttachments();
            edlist.addAll(newlist);
            pad.attatchmentsToupload = edlist;
            // deleted ones
            pad.deletedAttachments = doc.getDeletedAttachments();
        }
        // "ALL", its automatically done as 31=1+2+...
        return pad;
    }
}
