package org.xwiki.android.xmodel.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.xobjects.XObject;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.text.AlteredCharSequence;
import android.util.Log;

/**
 * @author xwiki gsoc 2012 A document that supports only SimpleObjects. Simple
 *         Objects are shallow objects that can have only XProperties for
 *         property fields.
 */
public class Document extends XWikiPage
{

	// things in a retreived document.
	private XWikiPage parent;

	private List<Document> children;

	private Map<String, XSimpleObject> objects; // key= ClassName/number

	private Map<Integer, Comment> comments; // key = int index in the list

	private Map<String, Attachment> attatchments;// key = resource id. ex:
													// xwiki:Blog.BlogPost1@mypic
													// -->key is: mypic

	private List<Tag> tags; // search by key not needed
	private Map<String, XWikiPage> translationPages; //key : fr, en, si ...
	
	private List<HistoryRecord> history;
	
	

	// resources that get newly added.
	// no keys. These are to be posted to server.Server will define the keys
	// after these resources are posted.
	private List<XSimpleObject> newObjects;
	private List<Attachment> newAttachments;
	private List<Comment> newComments;
	private List<Tag> newTags; // just a ref to tags. We always have to send the
								// whole set in Rest.
	private List<XWikiPage> newTranslationPages;

	// resources to update

	private Map<String, XSimpleObject> editedObjects; // key= ClassName/number
	private List<Attachment> editedAttachments;// key = resource id i.e <name>.
												// ex:
												// xwiki:Blog.BlogPost1@mypic =>
												// mypic
												// space.png
	private List<Comment> editedComments;
	private List<XWikiPage> editedTranslationPages;

	// resources to delete
	private List<String> deletedObjects; // value= of the deleted obj.
											// ClassName/number
	private List<String> deletedAttachments;
	private List<Integer> deletedCommetns;
	private List<String> deletedTags;
	private List<String> deletedTranslationPages;

	public Document(String wikiName, String spaceName, String pageName)
	{
		super(wikiName, spaceName, pageName);

		objects = new Hashtable<String, XSimpleObject>();
		newObjects = new ArrayList<XSimpleObject>();
		editedObjects = new Hashtable<String, XSimpleObject>();
		deletedObjects = new ArrayList<String>();

		comments = new HashMap<Integer, Comment>();
		newComments = new ArrayList<Comment>();
		editedComments = new ArrayList<Comment>();
		deletedCommetns = new ArrayList<Integer>();

		attatchments = new Hashtable<String, Attachment>();
		newAttachments = new ArrayList<Attachment>();
		editedAttachments = new ArrayList<Attachment>();
		deletedAttachments = new ArrayList<String>();

		tags = new ArrayList<Tag>();
		newTags = new ArrayList<Tag>();
		deletedTags = new ArrayList<String>();
		
		translationPages=null;// will be init when setting
		newTranslationPages=null;
		editedTranslationPages=null;
		deletedTranslationPages=null;
		    
	}

	/**
	 * When retrieving the object through get it will return the reference to
	 * the object in the list. Warning! For the current implementation,
	 * alterations done to the object will not get affected in the server unless
	 * the edited object is reset explicitly through setObject(String key,
	 * XSimpleObject object).
	 * 
	 * @param key
	 * @return
	 */
	public XSimpleObject getObject(String key)
	{
		// TODO: When retreived by a RAL layer obj::- use isAltered method in
		// Resource. use this to automatically
		// identify altered objects
		// and add them to editedObjects Map at the "ral.transformation"
		// package.
		XSimpleObject obj = objects.get(key);
		if (obj != null) {
			obj.setEdited(true);
			obj.setNew(false);
		}
		return obj;
	}

	public XSimpleObject getObject(String className, int number)
	{
		String key = className + "/" + number;
		XSimpleObject obj = objects.get(key);
		if (obj != null) {
			obj.setEdited(true);
		}
		return obj;
	}

	/**
	 * Update a existing object in the doc. The update is done if the
	 * Object.isAltered returns true. This property is set by defalut, when the
	 * object is retrieved using getObject(key)
	 * 
	 * @param key
	 * @param object
	 */
	@Deprecated
	public void setObject(String key, XSimpleObject object)
	{
		String keyprefix = object.getClassName();
		boolean valid = true;
		valid = key.startsWith(keyprefix);
		if (valid) {
			String[] args = key.split("[/]");
			String clsName = args[0];
			String number = args[1];
			valid = clsName.equals(object.getClassName());// &&number.matches("[\\d]+")
															// ;
			object.setNumber(new Integer(number));
		}
		if (!valid) {
			throw new IllegalArgumentException("invalid form of key.\n"
					+ " Key should be of the form <class>/<number>. "
					+ " \nIdeally you shold retreive these objects from server before editing.");
		}

		if (object.isEdited()) {// may remove this line. Since set is done
								// because XObj is edited.
			editedObjects.put(key, object);
		}
		objects.put(key, object);
	}

	/**
	 * When creating the doc on the server side this object will be added with best try to achieve the same obj number at server side.
	 * In a update operation the corresponding object at the server will be updated.
	 * If you does not want this set operation to affect the server when either updating or crating make object.setEdited(false).
	 * @param object
	 * @return key to refer this obj.
	 */
	public String setObject(XSimpleObject object)
	{
		if (object.getNumber() == -1) {
			throw new IllegalArgumentException("object's number must be set");
		}
		if (object.getClassName().equals("")) {
			throw new IllegalArgumentException("object's class name must not be empty");
		}
		// end validation

		String key = object.getClassName() + "/" + object.getNumber();

		if (object.isEdited()) {// may remove this line. Since set is done
								// because XObj is edited.
			editedObjects.put(key, object);
		}
		objects.put(key, object);
		return key;
	}

	private int _addNum = 0;

	/**
	 * @param obj
	 * @return the auto generated key for this object. key is
	 *         <classType>/new/<number>
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

	public Comment getComment(int id)
	{
	    Comment c=comments.get(id);
	    if(c!=null){
	        c.setEdited(true);
	        c.setNew(false);
	    }
		return c;
	}

	/**
	 * Add a cmnt. No cmnt id needed for the cmnt. A new negative valued id will be temporary assigned.
	 * @param cmnt
	 * @return id of the new comment.
	 */
	public int addComment(Comment cmnt)
	{
		if (!newComments.contains(cmnt) && !comments.containsKey(cmnt.getId())) {
		    if(cmnt.isNew()){
		        if(!newComments.contains(cmnt)){
		            newComments.add(cmnt);
		        }		        
		    }			
			int id = -newComments.size() - 10;
			comments.put(id, cmnt);
			cmnt.setId(id);
			cmnt.setOwner(this);			
			return id;
		} else {
			return cmnt.getId();
		}
	}

	/**
	 * Add a cmnt. No cmnt id needed for the cmnt.
	 * @param cmnt
	 * @param cascade
	 *            if true adds all the reply comments as well to the document.
	 * @return int[0]=parent comments id, int[1] number of comments added
	 */
	public int[] addComment(Comment prntCmnt, boolean cascade)
	{
		int pid = addComment(prntCmnt);
		int[] rslts = new int[2];
		rslts[0] = pid;
		rslts[1] = 1;		
		if (cascade) {
			for (Comment rply : prntCmnt.getReplies()) {				
				int[] recRslt = addComment(rply, true);
				rslts[1] += recRslt[1];
			}
		}
		return rslts;
	}

	

	@Deprecated
	// TODO remove soon
	public void setComment(int id, Comment cmnt)
	{
		if (id < 0) {
			throw new IllegalArgumentException(
					"comment id should be gt or eq to 0, \n If you are seting a comment that was added with add method no need to set again");
		}
		cmnt.setId(id);
		comments.put(id, cmnt);	
		if(cmnt.isEdited()){
		    if (!editedComments.contains(cmnt)) {
	            editedComments.add(cmnt);
	        } 
		}		
		if (cmnt.getDocument() != null) {
			if (this != cmnt.getDocument())
				throw new IllegalStateException("comment is already owned by another doc");
		} else {
			cmnt.setId(id);
			cmnt.setOwner(this);
		}

	}

	/**
	 * set a comment with id.	 
	 * In create operations, it will be tried to achieve the same comment id in server.
	 * In updates the comment will be updated.
	 * @param cmnt
	 */
	public void setComment(Comment cmnt)
	{
		int id = cmnt.getId();
		if (id < 0) {
			throw new IllegalArgumentException(
					"comment id should be gt or eq to 0, \n If you are seting a comment that was added with add method no need to set again");
		}
		comments.put(id, cmnt);
		if(cmnt.isEdited()){
		    if (!editedComments.contains(cmnt)) {
	            editedComments.add(cmnt);
	        }
		}		
		if (cmnt.getDocument() != null) {
			if (this != cmnt.getDocument())
				throw new IllegalStateException("comment is already owned by another doc");
		} else {
			cmnt.setId(id);
			cmnt.setOwner(this);
		}

	}

	public void deleteComment(int id)
	{
		comments.remove(id);
		Comment cmnt = new Comment();
		cmnt.setId(id);
		if (id < 0) {
			int i = newComments.indexOf(cmnt);
			if (i >= 0) {
				newComments.remove(i);
			}
		} else {
			int j = editedComments.indexOf(cmnt);
			if (j >= 0) {
				editedComments.remove(j);
			}
		}

		deletedCommetns.add(id);
	}

	public List<Tag> getTags()
	{
		return tags;
	}

	public void addTag(Tag tag)
	{
		tags.add(tag);
		newTags = tags;
	}

	public void clearTags()
	{
		for (Tag t : tags) {
			deletedTags.add(t.getName());
		}
		tags.clear();
	}

	public Attachment getAttachment(String name)
	{
		Attachment a = attatchments.get(name);
		if(a!=null){
		    a.setEdited(true);
		    a.setNew(false);
		}		
		return attatchments.get(name);
	}

	public String addAttachment(Attachment a)
	{
		if (a.isNew() == true) {
			newAttachments.add(a);
		}
		attatchments.put(a.getName(), a);
		return a.getName();
	}
	
	public void setAttachment(Attachment a)
    {
        if (a.getName() == null) {
            throw new IllegalArgumentException("attachment name should be set");
        }

        if (a.isEdited() == true) {
            int i = editedAttachments.indexOf(a);
            if (i > -1) {
                editedAttachments.add(i, a);
            } else {
                editedAttachments.add(a);
            }            
        }
        attatchments.put(a.getName(), a);
    }

	public void setAttachment(String name, Attachment a)
	{
		if (a.getName() == null) {
			a.setName(name);
		}

		if (a.isEdited() == true) {
			int i = editedAttachments.indexOf(a);
			if (i > -1) {
				editedAttachments.add(i, a);
			} else {
				editedAttachments.add(a);
			}			
		}
		attatchments.put(name, a);

	}

	/**
	 * 
	 * @param name
	 * @return true. If the attachment existed locally in the list and was
	 *         marked for deletion. false. Attachement is not in the local list.
	 *         But the model has marked the attachment for deletion on the
	 *         server.
	 * 
	 */
	public boolean deleteAttachment(String name)
	{
		Attachment a = attatchments.remove(name);
		if (!deletedAttachments.contains(name)) {
			deletedAttachments.add(a.getName());			
			editedAttachments.remove(a);
			newAttachments.remove(a);
			
		}
		if (a == null) {
			Log.w(this.getClass().getSimpleName(), "marking an unknown attachment to be deleted");
			return false;
		} else {
			return true;
		}

	}

	public XWikiPage getTranslationPage(String lang){
	    XWikiPage p=translationPages.get(lang);
	    if(p!=null){
	        p.setEdited(true);
	        p.setNew(false);
	    }
        return p;	    
	}
	/**
	 * adds a translation page to the doc.
	 * When doc is created on server the translation pages are added
	 * In a update operation if translation page already exists it will be updated in normal non strict updates.
	 * setting page.isNew(false) will ignore page under above operations.
	 * @param page
	 * @return
	 */
	public String addTranslationPage(XWikiPage page){
	    //verification
	    if(page.getLanguage()==null){
	        throw new IllegalArgumentException("please set language for the translated XWikiPage.");
	    }
	    String lang=page.getLanguage();
	    
	    if(translationPages==null){
            init_DataStructuresForTranslations();  
        }
	    
	    if(page.isNew()){
            boolean alreadyAdded=false;           
            for(int i=0;i<newTranslationPages.size();i++){
                XWikiPage trns=editedTranslationPages.get(i);
                alreadyAdded= trns.language==lang;
                if(alreadyAdded==true)break;
            }
            if(alreadyAdded){
                throw new IllegalStateException("translation page for this language is already added.");
            }
            else{
                newTranslationPages.add(page);  
            }            
        }
	    
	    //logic
	   
	    translationPages.put(lang, page);
	    if(page.isNew()){
	        newTranslationPages.add(page); 
	    }	    
	    return lang;
	}
	/**
	 * sets a translation page.
	 * In updates the translation page will always be updated.
	 * In creates the translation page will be anyway added to the server side doc.
	 * @param page
	 * @return
	 */
	public String setTranslationPage(XWikiPage page){
	    if(page.getLanguage()==null){
            throw new IllegalArgumentException("please set language for the translated XWikiPage.");
        }
	    if(translationPages==null){
	        init_DataStructuresForTranslations();  
	    }
        String lang=page.getLanguage();
        translationPages.put(lang, page);
        if(page.isEdited()){
            boolean alreadyAdded=false;
            int i;
            for(i=0;i<editedTranslationPages.size();i++){
                XWikiPage trns=editedTranslationPages.get(i);
                alreadyAdded= trns.language==lang;
                if(alreadyAdded==true)break;
            }
            if(alreadyAdded){
                editedTranslationPages.set(i, page);
            }
            else{
                editedTranslationPages.add(page);  
            }            
        }
        return lang;
	}
	

    /**
	 * marks the translation Page for deletion(i.e. a delete req will be sent when updating doc.).
	 * Removes the translation page in local document.
	 * @param lang
	 * @return translation page if it was in the document (fetched). Else null.
	 */
	public XWikiPage deleteTranslationPage(String lang){
	    if(lang==null)throw new NullPointerException();
	    if(translationPages==null){
            init_DataStructuresForTranslations();  
        }
	    boolean alreadyAdded=false;        
        for(int i=0;i<editedTranslationPages.size();i++){
            XWikiPage trns=editedTranslationPages.get(i);
            alreadyAdded= trns.language==lang;
            if(alreadyAdded==true)break;
        }
        if(!alreadyAdded){
            deletedTranslationPages.add(lang);
        }	    
	    return translationPages.remove(lang); //if again added after removal 
	}
	
	private void init_DataStructuresForTranslations()
    {
	    translationPages=new HashMap<String, XWikiPage>(2);
        newTranslationPages=new ArrayList<XWikiPage>(2);
        editedTranslationPages=new ArrayList<XWikiPage>(2);
        deletedTranslationPages=new ArrayList<String>(2);        
    }
	
	public List<HistoryRecord> getHistory()
    {
        return history;
    }
	
	/**
	 * This is set by rest when fetching/retrieving.
	 * @param history
	 */
	public void setHistory(List<HistoryRecord> history){
	  this.history=history;
	}

	public Map<String, XSimpleObject> getAllObjects()
	{
		return objects;
	}

	public XWikiPage getParentDocument()
	{
		return parent;
	}

	public List<Document> getChildrenDocuments()
	{
		return children;
	}

	public Map<Integer, Comment> getAllComments()
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

	public Set<String> getDeletedObjects()
	{
		Set<String> s = new HashSet<String>();
		s.addAll(deletedObjects);
		return s;
	}

	public List<Integer> listCommentDeletions()
	{
		return deletedCommetns;
	}

	public Set<Integer> getDeletedCommentSet()
	{
		Set<Integer> s = new HashSet<Integer>();
		s.addAll(deletedCommetns);
		return s;
	}

	public List<String> listAttachmentDeletions()
	{
		return deletedAttachments;
	}

	public Set<String> getDeletedAttachments()
	{
		Set<String> s = new HashSet<String>();
		s.addAll(deletedObjects);
		return s;
	}

	public List<String> listTagDeletions()
	{
		return deletedTags;
	}

	public void setParent(XWikiPage parent)
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
	
	public String toString(){
	    String s="";
	    s+= "wiki: "+wikiName+"space: "+spaceName+" name: "+name +"\n";
	    s+= " Objects new ["+newObjects.size()+"] , edited ["+editedObjects.size()+"] deleted ["+deletedObjects.size()+"]\n ------------------------------------ \n";
	     Set<String> keySet = objects.keySet();
	    for(String key:keySet){
	        s+= key +"\n" ; 
	    }
	    s+=" Comments ---> ids: ";
	    Set<Integer> keySet2 = comments.keySet();
	    for(Integer key:keySet2){
            s+= key +", " ; 
        }
	    
	    s+= "\n tags --->";
	    for(Tag t:tags){
	        s+=t+", ";
	    }
	    
	    s+= "\n attachments \n------------------\n";
	    
	    Collection<Attachment> values = attatchments.values();	  
	    for(Attachment a :values ){
	        s+=a+", ";
	    }	        
	    
	    return s;
	}

}

/**
 * NOTE: TODO: extract interface Document.l Always use XWikiApplicationContext
 * to create new.
 **/
