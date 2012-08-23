package org.xwiki.android.rest.ral.algo;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.rpc.XWikiAPI;
import org.xwiki.android.rest.transformation.DocLaunchPadForXML;
import org.xwiki.android.rest.transformation.DocumentDismantler_XML;
import org.xwiki.android.rest.transformation.RestModelTransformer;
import org.xwiki.android.rest.transformation.XModelTranslator_XML;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Document;

import android.util.Log;

public class DocUpdateStrategy implements IDocUpdateStragegy
{

    private static final String TAG = "DocUpdate";
    XWikiRestConnector rpc;
    DocumentDismantler_XML dismantler;

    public DocUpdateStrategy(String serverUrl, String username, String password)
    {
        rpc = new XWikiRestConnector(serverUrl, username, password);
        dismantler = new DocumentDismantler_XML();

    }

    @Override
    public Document update(Document d) throws RestConnectionException, RaoException
    {
        // update stats
        int numEdObj = 0, numEdCmnt = 0, numDelObj = 0, numDelCmnt = 0, numDelAtch = 0, numNwObj = 0, numNwCmnt = 0, numEdNwAtch =
            0, numTags = 0;

        String wikiName = d.getWikiName();
        String spaceName = d.getSpaceName();
        String pageName = d.getPageName();

        DocLaunchPadForXML pad = dismantler.convertDocument(d);
//page
        if (d.isEdited()) {
            Page p=pad.getPage();
            try {
                rpc.addPage(wikiName, spaceName, pageName, p);// add page does the update.
            } catch (RestException e1) {
               throw new RaoException("Error while updating content of the Page Element");
            } 
        }

        Set<Entry<String, Object>> entrySet = pad.getEditedObjects().entrySet();
        // edited objs
        for (Entry<String, Object> e : entrySet) {
            String key = e.getKey();
            String ss[] = key.split("/");
            Object ores = e.getValue();
            String objectClassname = ss[0];
            int objectNumber = new Integer(ss[1]);
            try {
                rpc.updateObject(wikiName, spaceName, pageName, objectClassname, objectNumber, ores);
                numEdObj++; // after the api op. If exception happens no ++ happens.
            } catch (RestException e1) {
                throw new RaoException(
                    "Object may not exist in actual doc. Also see wether you are updating non existing document. Because Update does not check for doc existence.",
                    e1);
                // TODO
            }
        }
        // deleted objs
        for (String key : pad.getDeletedObjects()) {
            String ss[] = key.split("/");
            String objectClassname = ss[0];
            String objectNumber = ss[1];
            try {
                rpc.deleteObject(wikiName, spaceName, pageName, objectClassname, objectNumber);
                numDelObj++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(
                    "?. By the way, delete op should work even if there is no actual object in the server to delete.",
                    e1);
            }
        }

        // new comments
        // TODO: this algo needs improvements if a large num of comments is added.
        List<Comment> newComments = pad.getNewComments();
        List<Comment> editedComments = pad.getEditedComments();
        for (Comment comment : newComments) {// TODO: Update op cannot achieve replyto s, unless rest layer is upgraded.
            try {
                Comment c = rpc.commentOperations(wikiName, spaceName, pageName).addPageCommentForResult(comment);
                for (Comment rply : newComments) {
                    if (rply.replyTo != null && rply.replyTo == comment.replyTo) {// cause both cmnts id,replyTo can be
                                                                                  // null.
                        rply.setReplyTo(comment.id);
                    }
                }
                for (Comment rply : editedComments) {
                    if (rply.replyTo != null && rply.replyTo == comment.replyTo) {// cause both cmnts id,replyTo can be
                                                                                  // null.
                        rply.setReplyTo(comment.id);
                    }
                }
                numNwCmnt++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // edited comments
        // need to conver to objs. Direct editing is not currently supported in
        // xwiki restful api.
        RestModelTransformer transformer = new RestModelTransformer();
        for (Comment cmnt : pad.getEditedComments()) {
            Object cmntObj = transformer.toObject(cmnt);
            String objectClassname = cmntObj.getClassName();
            int objectNumber = cmntObj.getNumber();
            try {
                rpc.updateObject(wikiName, spaceName, pageName, objectClassname, objectNumber, cmntObj);
                numEdCmnt++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }

        }

        // deleted comments

        for (String key : pad.getDeletedComments()) {
            String ss[] = key.split("/");
            String objectClassname = ss[0];
            String objectNumber = ss[1];
            try {
                rpc.deleteObject(wikiName, spaceName, pageName, objectClassname, objectNumber);
                numDelCmnt++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // replace tags <<new, edited
        Tags tags = pad.getTags();
        if (tags != null) {
            try {
                rpc.setTags(wikiName, spaceName, pageName, tags);
                numTags = tags.getTags().size();
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);

            }
        }

        // update Attachments

        for (Attachment a : pad.getAttatchmentsToupload()) {
            try {
                rpc.putPageAttachment(wikiName, spaceName, pageName, a.getFile().getAbsolutePath(), a.getName());
                numEdNwAtch++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // delete @s

        for (String s : pad.getDeletedAttachments()) {
            try {
                rpc.deletePageAttachment(wikiName, spaceName, pageName, s);
                numDelAtch++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        Log.i(TAG, "Stats: " + "edited-Obj=" + numEdObj + "edited Comments=" + numEdCmnt
            + "edited and new Attachments=" + numEdNwAtch + "\n deleted Objects:Comments:Attachments=" + numDelObj
            + ": " + numDelCmnt + ": " + numDelAtch + "\n new Objects:Comments=" + numNwObj + ": " + numNwCmnt
            + "\n num of tags reset" + numTags);
        return null;// should return the updated doc extracted from the response.
    }

}
