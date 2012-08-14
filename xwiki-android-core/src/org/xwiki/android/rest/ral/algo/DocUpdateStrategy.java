package org.xwiki.android.rest.ral.algo;

import java.util.Map.Entry;
import java.util.Set;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;
import org.xwiki.android.rest.XWikiRestConnecion;
import org.xwiki.android.rest.XWikiAPI;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.transformation.DocLaunchPadForXML;
import org.xwiki.android.rest.transformation.DocumentDismantler_XML;
import org.xwiki.android.xmodel.entity.Attachment;
import org.xwiki.android.xmodel.entity.Document;

import android.util.Log;

public class DocUpdateStrategy implements IDocUpdateStragegy
{

    private static final String TAG = "DocUpdate";
    XWikiAPI api;
    DocumentDismantler_XML dismantler;

    public DocUpdateStrategy(String serverUrl, String username, String password)
    {
        api = new XWikiRestConnecion(serverUrl, username, password);
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
        Set<Entry<String, Object>> entrySet = pad.getEditedObjects().entrySet();
        // edited objs
        for (Entry<String, Object> e : entrySet) {
            String key = e.getKey();
            String ss[] = key.split("/");
            Object ores = e.getValue();
            String objectClassname = ss[0];
            int objectNumber = new Integer(ss[1]);
            try {
                api.updateObject(wikiName, spaceName, pageName, objectClassname, objectNumber, ores);
                numEdObj++; // after the api op. If exception happens no ++ happens.
            } catch (RestException e1) {
                throw new RaoException(e1);
                // TODO
            }
        }
        // deleted objs
        for (String key : pad.getDeletedObjects()) {
            String ss[] = key.split("/");
            String objectClassname = ss[0];
            String objectNumber = ss[1];
            try {
                api.deleteObject(wikiName, spaceName, pageName, objectClassname, objectNumber);
                numDelObj++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // new comments

        for (Comment comment : pad.getNewComments()) {
            try {
                api.addPageComment(wikiName, spaceName, pageName, comment);
                numNwCmnt++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // edited comments
        // need to conver to objs. Direct editing is not currently supported in
        // xwiki restful api.
        for (Object cmntObj : pad.getEditedComments()) {
            String objectClassname = cmntObj.getClassName();
            int objectNumber = cmntObj.getNumber();
            try {
                api.updateObject(wikiName, spaceName, pageName, objectClassname, objectNumber, cmntObj);
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
                api.deleteObject(wikiName, spaceName, pageName, objectClassname, objectNumber);
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
                api.setTags(wikiName, spaceName, pageName, tags);
                numTags = tags.getTags().size();
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);

            }
        }

        // update Attachments

        for (Attachment a : pad.getAttatchmentsToupload()) {
            try {
                api.putPageAttachment(wikiName, spaceName, pageName, a.getFile().getAbsolutePath(), a.getName());
                numEdNwAtch++;
            } catch (RestException e1) {
                // TODO Auto-generated catch block
                throw new RaoException(e1);
            }
        }

        // delete @s

        for (String s : pad.getDeletedAttachments()) {
            try {
                api.deletePageAttachment(wikiName, spaceName, pageName, s);
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
