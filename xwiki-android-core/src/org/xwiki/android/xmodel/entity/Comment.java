package org.xwiki.android.xmodel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xwiki.android.rest.reference.Link;
import org.xwiki.android.xmodel.xobjects.XComment;

public class Comment extends XWikiResource
{

    private int id = -1; // -1 to mean null
    private String author;
    private Date date;
    private String text;
    private int replyTo = -1; // -1 to mean null
    private String highlight;

    private Document ownerDoc;
    private List<Comment> replies;
   
    //non bean props.
    // object associated to this comment.
    private XComment xobj;
    boolean isaReply;

    public Comment()
    {
        replies = new ArrayList<Comment>();
    }

    public Comment(String text)
    {
        this();
        this.text = text;
    }
    
    public void replyTo(Comment c){
        c.addReplyComment(this);
        isaReply=true;
    }

    /**
     * This method also adds the reply comment to the parent comment's owning document if it is owned by a document.
     * 
     * @return true if added. false if already contained the comment.
     */
    public boolean addReplyComment(Comment rply)
    {
        if (this == rply)
            throw new IllegalArgumentException("Comment cannot reply to itself");
        rply.isaReply=true;
        if (!replies.contains(rply)) {
            replies.add(rply);
            if (ownerDoc != null) {
                ownerDoc.addComment(rply);
            }
            rply.setReplyTo(this.id);
            return true;
        } else {
            return false;
        }
        
    }    
    

    /**
     * when the id of this comment is changed refresh the change to replyTo fields of direct reply comments.
     */
    private void refreshChildrenReplyToID()
    {
        for (Comment rply : this.getReplies()) {
            rply.setReplyTo(this.getId());
        }
    }

    public List<Comment> getReplies()
    {
        return replies;
    }

    public void setReplies(List<Comment> replies)
    {
        for (Comment c : replies) {
            addReplyComment(c);
        }
    }

    //
    // getter setteres
    //

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        if (ownerDoc != null) {
            throw new IllegalStateException("You cannot alter the id of a comment after it is owned by a document");
        }
        this.id = id;
        refreshChildrenReplyToID();
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo(int replyTo)
    {
        if(replyTo!=-1){
            if (this.id == -1) {
                this.replyTo = replyTo;
            } else if (this.id >= 0) {
                if (replyTo < -1) {
                    if ((-10 - replyTo) > id) {
                        String msg =
                            "this cmnts id is " + id + " but we reply to " + replyTo + " which is the " + (-10 - replyTo)
                                + "th new comment which obviously gets added after this comment";
                        throw new IllegalStateException(msg);
                    }
                } else if (replyTo >= 0) {
                    if (!(replyTo < id)) {
                        throw new IllegalStateException("cannot reply to a comment that is after this.");
                    }
                } else {// replyto=-1
                    if (this.ownerDoc != null) {
                        int newCmnts = ownerDoc.getAllNewComments().size();
                        if (newCmnts >= id) {
                            throw new IllegalStateException(
                                "replying to a comment that will obviously be added after this comment. there are "
                                    + newCmnts + " already added to Document. They are enough to fill the gap. this.id is:"
                                    + id);
                        }
                    }
                }
            }else{//this.id<-1 i.e. already associated to document.
               if(!(id<replyTo)){
                   throw new IllegalStateException("this.tmp_id: "+id+" i.e. this is the "+(-10-id)+"th comment. Replying to "+(-10-replyTo)+"th comment with tmpid:"+replyTo);
               }
            } 
        }
        this.replyTo = replyTo;
    }

    public String getHighlight()
    {
        return highlight;
    }

    public void setHighlight(String highlight)
    {
        this.highlight = highlight;
    }

    // package

    Document getDocument()
    {
        return ownerDoc;
    }

    void setOwner(Document ownerDoc)
    {
        this.ownerDoc = ownerDoc;
    }

    // private

    private XComment getXObject()
    {
        if (xobj == null) {
            xobj = new XComment();

            // ! xobj.setId(""+id);// dont set.Not the same.
            xobj.setNumber(id);
            xobj.setAuthor(author);
            xobj.setDate(date);
            xobj.setComment(text);
            xobj.setReplyto(replyTo);

        }
        return xobj;
    }

    private void setXObject(XComment xobj)
    {
        this.xobj = xobj;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof XComment)) {
            return false;
        } else {
            Comment c = (Comment) o;
            return c.getId() == this.id && c.text == this.text;
        }

    }

}
