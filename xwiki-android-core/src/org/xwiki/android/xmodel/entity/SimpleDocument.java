package org.xwiki.android.xmodel.entity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xwiki.android.xmodel.reference.DocumentReference;
import org.xwiki.android.xmodel.xobjects.XObject;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.text.AlteredCharSequence;
/**
 * 
 * @author xwiki gsoc 2012
 * A document that supports only SimpleObjects. Simple Objects are shallow objects that can have only XProperties for property fields.
 * 
 */
public class SimpleDocument extends Document {
	
	//things in a retreived document.
		private Document parent;
		private List<Document> children;
		private Map<String,XSimpleObject> objects; //key= ClassName/number
		private List<Comment> comments; //key = int index in the list
		private Map<String,Attachment> attatchments;//key = resource id. ex: xwiki:Blog.BlogPost1@mypic can have space.png
		private List<Tag> tags; //search by key not needed
		
		//resources that get newly added.
		//no keys. These are to be posted to server.Server will define the keys after these resources are posted.
		private List<XSimpleObject> newObjects;
		private List<Comment> newComments;
		private List<Attachment> newAttachments;
		private List<Tag> newTags;	
		
		//resources to update
		
		private Map<String,XSimpleObject> editedObjects; //key= ClassName/number
		private List<Comment> editedComments; //key = int index in the list
		private Map<String,Attachment> editedAttachments;//key = resource id. ex: xwiki:Blog.BlogPost1@mypic can have space.png
		private List<Tag> editedTags; //search by key not needed
		
		//resources to delete
		private List<String> deletedObjects; //value= of the deleted obj. ClassName/number
		private List<Comment> deletedComments; //key = int index in the list
		private List<String> deletedAttachments;//key = resource id. ex: xwiki:Blog.BlogPost1@mypic can have space.png
		private List<Tag> deletedTags; //search by key not needed
		
		
		public SimpleDocument(String wikiName, String spaceName, String pageName){
			super(wikiName, spaceName, pageName);
			
			objects=new Hashtable<String, XSimpleObject>();
			newObjects=new ArrayList<XSimpleObject>();
			editedObjects=new Hashtable<String, XSimpleObject>();
			deletedObjects=new ArrayList<String>();
			
			//TODO: implement
			comments=new ArrayList<Comment>();
			newComments=new ArrayList<Comment>();			
						
		}
		
		
		/**
		 * When retrieving the object through get it will return the reference to the object in the list. 
		 * Warning!
		 * For the current implementation,
		 * alterations done to the object will not get affected in the server unless the edited object is reset
		 * explicitly through setObject(String key, XSimpleObject object).
		 * @param key
		 * @return
		 */
		public XSimpleObject getObject(String key){
			//TODO: When retreived by a RAL layer obj::- use isAltered method in Resource. use this to automatically identify altered objects
			// and add them to editedObjects Map at the "ral.transformation" package.
			XSimpleObject obj=objects.get(key);
			obj.setAltered(true);
			return obj;
		}


		/**
		 * Update a existing object in the doc. The update is done if the Object.isAltered returns true.
		 * This  property is set by defalut, when the object is retrieved using getObject(key) 
		 * @param key
		 * @param object
		 */
		public void setObject(String key, XSimpleObject object) {
			if(!objects.containsKey(key)){
				throw new IllegalArgumentException("you can only set objects which already exist in the map");
			}
			if(object.isAltered()){
				editedObjects.put(key, object);
			}
			objects.put(key, object);	
		}

		private int _addNum=0;
		
		public void addObject(XSimpleObject obj) {	
			String key=obj.getType()+"/new/"+_addNum++;	
			obj.setNew(true);
			objects.put(key, obj);
			newObjects.add(obj);
		}	
				
		public void deleteObject(String key) {
			XSimpleObject obj=objects.get(key);
			if(obj.isNew()){
				newObjects.remove(obj);
			}else{
				deletedObjects.add(key);
			}
			if(obj.isAltered()){
				editedObjects.remove(key);
			}
			objects.remove(key);
			
		}
		
		public Map<String,XSimpleObject> getAllObjects(){
			return objects;
		}


		public Document getParentDocument() {
			return parent;
		}


		public List<Document> getChildrenDocuments() {
			return children;
		}


		public List<Comment> getAllComments() {
			return comments;
		}


		public Map<String, Attachment> getAllAttatchments() {
			return attatchments;
		}


		public List<Tag> getAllTags() {
			return tags;
		}


		public List<XSimpleObject> getAllNewObjects() {
			return newObjects;
		}


		public List<Comment> getAllNewComments() {
			return newComments;
		}


		public List<Attachment> getAllNewAttachments() {
			return newAttachments;
		}


		public List<Tag> getAllNewTags() {
			return newTags;
		}


		public Map<String, XSimpleObject> getAllEditedObjects() {
			return editedObjects;
		}


		public List<Comment> getAllEditedComments() {
			return editedComments;
		}


		public Map<String, Attachment> getAllEditedAttachments() {
			return editedAttachments;
		}


		public List<Tag> getAllEditedTags() {
			return editedTags;
		}


		public List<String> getAllDeletedObjects() {
			return deletedObjects;
		}


		public List<Comment> getAllDeletedComments() {
			return deletedComments;
		}


		public List<String> getAllDeletedAttachments() {
			return deletedAttachments;
		}


		public List<Tag> getAllDeletedTags() {
			return deletedTags;
		}


//setters
		
		
		public void setParent(Document parent) {
			if(parentFullName==null){
				parentFullName=parent.fullName;
			}
			if(parentId==null){
				
			}
			this.parent = parent;
		}


		public void setChildren(List<Document> children) {
			this.children = children;
		}

		

}

/**
 * NOTE:
 * TODO: extract interface SimpleDocument.l
 * Always use XWikiApplicationContext to create new.
 * 
 * **/
