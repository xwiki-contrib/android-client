package org.xwiki.android.xmodel.entity;

import java.util.Date;
import java.util.List;

import org.xwiki.android.ral.reference.Link;
import org.xwiki.android.xmodel.xobjects.XComment;

public class Comment
{
	List<Link> links;
	int id;
	String author;
	Date date;
	String text;
	int replyTo;
	String highlight;
	//object associated to this comment.
	XComment xobj;
	public List<Link> getLinks()
	{
		return links;
	}
	public void addLink(Link link)
	{
		links.add(link);
	}
	public void removeLink(int index){
		links.remove(index);
	}
	public void clearLinks(){
		links.clear();
	}
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
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
	private XComment getXObject()
	{
		if(xobj==null){
			xobj=new XComment();

			//! xobj.setId(""+id);// dont set.Not the same.
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
		if(!(o instanceof XComment)){
			return false;
		}else{
			Comment c=(Comment) o;
			return c.getId()==this.id;
		}
		
	}
	
}
