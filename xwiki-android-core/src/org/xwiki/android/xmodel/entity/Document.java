package org.xwiki.android.xmodel.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.xobjects.XObject;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.text.AlteredCharSequence;
import android.util.Log;

/**
 * @author xwiki gsoc 2012 A document that supports only SimpleObjects. Simple Objects are shallow objects that can have
 *         only XProperties for property fields.
 */
public class Document extends DocumentBase
{

    // things in a retreived document.
    private DocumentBase parent;

    private List<Document> children;

    private Map<String, XSimpleObject> objects; // key= ClassName/number

    private Map<Integer,Comment> comments; // key = int index in the list

    private Map<String, Attachment> attatchments;// key = resource id. ex: xwiki:Blog.BlogPost1@mypic -->key is: mypic

    private List<Tag> tags; // search by key not needed

    // resources that get newly added.
    // no keys. These are to be posted to server.Server will define the keys after these resources are posted.
    private List<XSimpleObject> newObjects;
    private List<Attachment> newAttachments;
    private List<Comment> newComments;
    private List<Tag> newTags; // just a ref to tags. We always have to send the whole set in Rest.
    

    // resources to update

    private Map<String, XSimpleObject> editedObjects; // key= ClassName/number 
    private List<Attachment> editedAttachments;// key = resource id i.e <name>. ex: xwiki:Blog.BlogPost1@mypic => mypic
                                                     // space.png
    private List<Comment> editedComments;    

    // resources to delete
    private List<String> deletedObjects; // value= of the deleted obj. ClassName/number
    private List<String> deletedAttachments; 
    private List<Integer> deletedCommetns;
    private List<String> deletedTags;
    
    public Document(String wikiName, String spaceName, String pageName)
    {
        super(wikiName, spaceName, pageName);

        objects = new Hashtable<String, XSimpleObject>();
        newObjects = new ArrayList<XSimpleObject>();
        editedObjects = new Hashtable<String, XSimpleObject>();
        deletedObjects = new ArrayList<String>();
        
        comments = new HashMap<Integer, Comment>();
        newComments=new ArrayList<Comment>();
        editedComments=new ArrayList<Comment>();
        deletedCommetns=new ArrayList<Integer>();
        
        attatchments=new Hashtable<String, Attachment>();
        newAttachments=new ArrayList<Attachment>();
        editedAttachments=new ArrayList<Attachment>();
        deletedAttachments=new ArrayList<String>();
        
        tags=new ArrayList<Tag>();
        newTags=new ArrayList<Tag>();
        deletedTags=new ArrayList<String>();
    }

    /**
     * When retrieving the object through get it will return the reference to the object in the list. Warning! For the
     * current implementation, alterations done to the object will not get affected in the server unless the edited
     * object is reset explicitly through setObject(String key, XSimpleObject object).
     * 
     * @param key
     * @return
     */
    public XSimpleObject getObject(String key)
    {
        // TODO: When retreived by a RAL layer obj::- use isAltered method in Resource. use this to automatically
        // identify altered objects
        // and add them to editedObjects Map at the "ral.transformation" package.
        XSimpleObject obj = objects.get(key);
        if(obj!=null){
            obj.setEdited(true);
        }        
        return obj;
    }
    
    public XSimpleObject getObject(String className, int number)
    {
        String key=className+"/"+number;
        XSimpleObject obj = objects.get(key);
        if(obj!=null){
            obj.setEdited(true);
        }        
        return obj;
    }    
   

    /**
     * Update a existing object in the doc. The update is done if the Object.isAltered returns true. This property is
     * set by defalut, when the object is retrieved using getObject(key)
     * 
     * @param key
     * @param object
     */
    @Deprecated
    public void setObject(String key, XSimpleObject object)
    {    	
        String keyprefix=object.getClassName();
        boolean valid=true;
        valid=key.startsWith(keyprefix);
        if(valid){
        	String[] args=key.split("[/]");
        	String clsName=args[0];
        	String number=args[1];
        	valid=clsName.equals(object.getClassName());// &&number.matches("[\\d]+") ;
        	object.setNumber(new Integer(number));
        }        
        if(!valid){
        	throw new IllegalArgumentException("invalid form of key.\n" +
        			" Key should be of the form <class>/<number>. " +
        			" \nIdeally you shold retreive these objects from server before editing.");
        }
        
    	if (object.isEdited()) {//may remove this line. Since set is done because XObj is edited.
            editedObjects.put(key, object);
        }
        objects.put(key, object);
    }    
    /**
     * 
     * @param object
     * @return key to refer this obj.
     */
    public String setObject(XSimpleObject object)
    {       
        if(object.getNumber()==-1){
            throw new IllegalArgumentException("object's number must be set");
        }
        if(object.getClassName().equals("")){
            throw new IllegalArgumentException("object's class name must not be empty");
        }
        //end validation
        
        String key=object.getClassName()+"/"+object.getNumber();
        
        if (object.isEdited()) {//may remove this line. Since set is done because XObj is edited.
            editedObjects.put(key, object);
        }
        objects.put(key, object);
        return key;
    }

    private int _addNum = 0;

    /**
     * @param obj
     * @return the auto generated key for this object. key is  <classType>/new/<number>
     */
    public String addObject(XSimpleObject obj)
    {
        String key = obj.getClassName() + "/new/" + _addNum++;
        obj.setNew(true);
        objects.put(key, obj);
        newObjects.add(obj);
        return key;
    }

    public void deleteObject(String key)
    {
        XSimpleObject obj = objects.get(key);
        if (obj.isNew()) {
            newObjects.remove(obj);
        } else {
            deletedObjects.add(key);
        }
        if (obj.isEdited()) {
            editedObjects.remove(key);
        }
        objects.remove(key);

    }
    
    
    public Comment getComment(int id){
    	return comments.get(id);
    }
    /**
     * 
     * @param cmnt
     * @return id of the new comment.
     */
    public int addComment(Comment cmnt){
        if(!newComments.contains(cmnt)&&!comments.containsKey(cmnt.getId())){
            newComments.add(cmnt);
            int id=-newComments.size()-10;
            comments.put(id,cmnt);
            cmnt.setId(id); 
            cmnt.setOwner(this);
            return id; 
        }else{
            return cmnt.getId();
        }
    }
    
    /**
     * 
     * @param cmnt
     * @param cascade if true adds all the reply comments as well to the document.
     * @return int[0]=parent comments id, int[1] number of comments added
     */
    public int[] addComment(Comment prntCmnt, boolean cascade){        
        int pid=addComment(prntCmnt);
        int[] rslts=new int[2];
        rslts[0]=pid;
        rslts[1]=1;
        if(cascade){
            for(Comment rply:prntCmnt.getReplies()){
                int[] recRslt=addComment(rply,true);
                rslts[1]+=recRslt[1];
            } 
        }      
       return rslts;      
    }
    
    @Deprecated  //TODO remove soon
    public void setComment(int id, Comment cmnt){
        if(id<0){
            throw new IllegalArgumentException("comment id should be gt or eq to 0, \n If you are seting a comment that was added with add method no need to set again");
        }
    	comments.put(id, cmnt);
    	if(!editedComments.contains(cmnt)){
    		editedComments.add(cmnt);
    	}
    	if(cmnt.getDocument()!=null){
    	   if(this!=cmnt.getDocument())throw new IllegalStateException("comment is already owned by another doc");
    	}else{
    	    cmnt.setId(id);
    	    cmnt.setOwner(this);    	    
    	}
    	
    }
    
    public void setComment(Comment cmnt){
        int id=cmnt.getId();
        if(id<0){
            throw new IllegalArgumentException("comment id should be gt or eq to 0, \n If you are seting a comment that was added with add method no need to set again");
        }
        comments.put(id, cmnt);
        if(!editedComments.contains(cmnt)){
            editedComments.add(cmnt);
        }
        if(cmnt.getDocument()!=null){
           if(this!=cmnt.getDocument())throw new IllegalStateException("comment is already owned by another doc");
        }else{
            cmnt.setId(id);
            cmnt.setOwner(this);            
        }
        
    }
    
    
    public void deleteComment(int id){
    	comments.remove(id);
    	Comment cmnt=new Comment();
    	cmnt.setId(id);
    	if(id<0){
    	    int i=newComments.indexOf(cmnt);
    	    if(i>=0){
    	        newComments.remove(i); 
    	    }    	    
    	}else{
    	    int j=editedComments.indexOf(cmnt);
    	    if(j>=0){
    	        editedComments.remove(j);
    	    }
    	}  
    	
    	deletedCommetns.add(id);
    }
    
    public List<Tag> getTags(){
    	return tags;
    }
    
    public void addTag(Tag tag){
    	tags.add(tag);
    	newTags=tags;
    }
    
    public void clearTags(){
        for(Tag t:tags){
            deletedTags.add(t.getName());
        }        
    	tags.clear();
    }
    
    public Attachment getAttachment(String name){
        Attachment a=attatchments.get(name);
        a.setEdited(true);
        return attatchments.get(name);
    }
    
    public String addAttachment(Attachment a){
        if(a.isNew()==true){
            newAttachments.add(a);
        }       
       attatchments.put(a.getName(), a);
       return a.getName();
    }
    
    public void setAttachment(String name,Attachment a){
        if(a.getName()==null){
            a.setName(name);
        }
        
        if(a.isEdited()==true){
            int i=editedAttachments.indexOf(a);            
            if(i>-1){
                editedAttachments.add(i,a);
            }else{
                editedAttachments.add(a);
            }
            attatchments.put(name, a); 
        }
           
    }
    /**
     * 
     * @param name
     * @return true. If the attachment existed locally in the list and was marked for deletion.
     *         false. Attachement is not in the local list. But the model has marked the attachment for 
     *         deletion on the server.
     *               
     */
    public boolean deleteAttachment(String name){
        Attachment a=attatchments.remove(name);
        if(!deletedAttachments.contains(name)){
            deletedAttachments.add(a.getName()); 
        }           
        if(a==null){
            Log.w(this.getClass().getSimpleName(), "marking an unknown attachment to be deleted");
            return false;
        }else {
            return true;
        }
        
    }
    
    

    public Map<String, XSimpleObject> getAllObjects()
    {
        return objects;
    }

    public DocumentBase getParentDocument()
    {
        return parent;
    }

    public List<Document> getChildrenDocuments()
    {
        return children;
    }

    public Map<Integer,Comment> getAllComments()
    {
        return comments;
    }

    public Map<String, Attachment> getAllAttatchments()
    {
        return attatchments;
    }

    public List<Tag> getAllTags()
    {
        return tags;
    }

    public List<XSimpleObject> getAllNewObjects()
    {
        return newObjects;
    }
    

    public List<Comment> getAllNewComments()
	{
		return newComments;
	}
    
    
    public List<Attachment> getAllNewAttachments()
    {
        return newAttachments;
    }
    
    public List<Tag> getAllNewTags()
	{
		return newTags;
	}
    

    public Map<String, XSimpleObject> getAllEditedObjects()
    {
        return editedObjects;
    }

    public List<Comment> getAllEditedComments()
	{
		return editedComments;
	}
  
    public List<Attachment> getAllEditedAttachments()
    {
        return editedAttachments;
    }

   

    public List<String> listObjectDeletions()
    {
        return deletedObjects;
    }
    
    public Set<String> getDeletedObjects(){
        Set<String> s=new HashSet<String>();
        s.addAll(deletedObjects);
        return s;
    }
    
    public List<Integer> listCommentDeletions()
    {
        return deletedCommetns;
    }
    
    public Set<Integer> getDeletedCommentSet(){
        Set<Integer> s=new HashSet<Integer>();
        s.addAll(deletedCommetns);
        return s;
    }
    

    public List<String> listAttachmentDeletions(){
    	return deletedAttachments;
    } 
    
    public Set<String> getDeletedAttachments(){
        Set<String> s=new HashSet<String>();
        s.addAll(deletedObjects);
        return s;
    }

	public List<String> listTagDeletions(){
	    return deletedTags;
	}
	

	public void setParent(DocumentBase parent)
    {
        if (parentFullName == null) {
            parentFullName = parent.fullName;
        }
        if (parentId == null) {

        }
        this.parent = parent;
    }

    public void setChildren(List<Document> children)
    {
        this.children = children;
    }

}

/**
 * NOTE: TODO: extract interface Document.l Always use XWikiApplicationContext to create new.
 **/
