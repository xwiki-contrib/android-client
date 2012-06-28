package org.xwiki.android.xmodel.entity;

import java.util.List;
import java.util.Map;
import org.xwiki.android.xmodel.xobjects.XObject;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;
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
		private Map<String,XSimpleObject> deletedObjects; //key= ClassName/number
		private List<Comment> deletedComments; //key = int index in the list
		private Map<String,Attachment> deletedAttachments;//key = resource id. ex: xwiki:Blog.BlogPost1@mypic can have space.png
		private List<Tag> deletedTags; //search by key not needed
		
		
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
		 * Update a existing object in the doc.
		 * @param key
		 * @param object
		 */
		public void setObject(String key, XSimpleObject object) {
			if(!objects.containsKey(key)){
				throw new IllegalArgumentException("you can only set objects for an existing key");
			}
			if(object.isAltered()){
				editedObjects.put(key, object);
			}
			objects.put(key, object);	
		}


		
		public void addObject(XSimpleObject obj) {	
			String key="objclass/x";			
			//TODO: implement
			objects.put(key, obj);
			newObjects.add(obj);
		}


		
		public void deleteObject(String key) {
			// TODO Auto-generated method stub
			
		}
		
		
		

}

/**
 * NOTE:
 * TODO: extract interface SimpleDocument.l
 * Always use XWikiApplicationContext to create new.
 * **/
