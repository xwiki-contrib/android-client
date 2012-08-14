package org.xwiki.android.xmodel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xwiki.android.rest.reference.Link;
import org.xwiki.android.xmodel.xobjects.XComment;

public class Comment extends Resource
{
    List<Link> links;
    int id =-1; //-1 to mean null
    String author;
    Date date;
    String text;
    int replyTo=-1; //-1 to mean null
    String highlight;
    
    Document ownerDoc;
    List<Comment> replies;
    // object associated to this comment.
    XComment xobj;

    public Comment()
    {
    	replies=new ArrayList<Comment>();
    }
    
    public Comment(String text){
    	this();
     this.text=text;   
    }
    
    /**
     * 
     * This method also adds the reply comment to the parent comment's owning document if it is owned by a document.
     * @return true if added. false if already contained the comment.
     */
    public boolean addReplyComment(Comment rply){
        if(!replies.contains(rply)){            
            replies.add(rply);
            if(ownerDoc!=null){
                rply.replyTo=this.id;//setting id is done by document. If this cmnt is owned by a doc then it is 
                ownerDoc.addComment(rply);
            }
            return true;
        }else{
            return false;
        }        
    }   

    public List<Comment> getReplies()
    {
        return replies;
    }

    public void setReplies(List<Comment> replies)
    {
        this.replies = replies;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public void addLink(Link link)
    {
        links.add(link);
    }

    public void removeLink(int index)
    {
        links.remove(index);
    }

    public void clearLinks()
    {
        links.clear();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        if(ownerDoc!=null){
            throw new IllegalStateException("You cannot alter the id of a comment after it is owned by a document");
        }
        this.id = id;
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
    
    //package
    
    Document getDocument(){
        return ownerDoc;
    }
    void setOwner(Document ownerDoc){
        this.ownerDoc=ownerDoc;
    }
    
    
    
    
    //private

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
            return c.getId() == this.id && c.text==this.text;
        }

    }

}
