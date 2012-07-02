package org.xwiki.android.rest;

import java.io.InputStream;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Class;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.resources.Wikis;
/**
 * 
 * @author xwiki gsoc 2012
 * 
 */
public interface XWikiRestfulAPI {

	/**
	 * Set the user credentials for accession
	 * 
	 * @param username username of the user account in XWiki
	 * @param password password of the user account in XWiki
	 */
	void setAuthentication(String username, String password);

	/**
	 * @param wikiName name of the Wiki
	 * @param keyword text to search
	 * @return Search results of the searched keyword within Wiki contents
	 */
	SearchResults requestSearch(String wikiName, String keyword);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param keyword text to search
	 * @return Search results of the searched keyword in provided space
	 */
	SearchResults requestSearch(String wikiName,
			String spaceName, String keyword);

	/**
	 * @return list of wikis in the provided XWiki URL domain
	 */
	Wikis requestWikis();

	/**
	 * @param wikiName name of the wiki
	 * @return list of spaces in the provided Wiki
	 */
	Spaces requestSpaces(String wikiName);

	/**
	 * @param wikiName name of the wiki
	 * @param spaceName name of the space
	 * @return list of all the pages in specific Wiki and Space
	 */
	Pages requestAllPages(String wikiName, String spaceName);

	/**
	 * @param wikiName name of the wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return Page object according to the field values
	 */
	Page requestPage(String wikiName, String spaceName,
			String pageName);

	/**
	 * Add page to the server according to the provided field values
	 * 
	 * @param wikiName name of the wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param page Page object which is to be added
	 * @return status of the HTTP response
	 */
	String addPage(String wikiName, String spaceName,
			String pageName, Page page);

	/**
	 * Delete page in the server
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return status of the HTTP response
	 */
	String deletePage(String wikiName, String spaceName,
			String pageName);

	/**
	 * Delete page translation
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param language specific language to delete
	 * @return status of the HTTP response
	 */
	String deletePage(String wikiName, String spaceName,
			String pageName, String language);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return history of the page according to the field values
	 */
	History requestPageHistory(String wikiName,
			String spaceName, String pageName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param version version of the page
	 * @return Page of the specified version
	 */
	Page requestPageInVersionHistory(String wikiName,
			String spaceName, String pageName, String version);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return Children pages of the specific page
	 */
	Pages requestPageChildren(String wikiName,
			String spaceName, String pageName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return Tags of the page
	 */
	Tags requestPageTags(String wikiName, String spaceName,
			String pageName);

	/**
	 * Adds page tag to the provided page
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param tag tag to be added
	 * @return status of the HTTP response
	 */
	String addPageTag(String wikiName, String spaceName,
			String pageName, Tag tag);

	/**
	 * @param wikiName name of the Wiki
	 * @return all the tags used in the wiki
	 */
	Tags requestWikiTags(String wikiName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return all the comments in the specific page
	 */
	Comments requestPageComments(String wikiName,
			String spaceName, String pageName);

	/**
	 * Add comment to the page
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param comment Comment object to be added
	 * @return status of the HTTP response
	 */
	String addPageComment(String wikiName, String spaceName,
			String pageName, Comment comment);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param commentId ID of the comment
	 * @return page comment according to the comment ID
	 */
	Comment requestPageComments(String wikiName,
			String spaceName, String pageName, String commentId);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param version version of the page
	 * @return All the comments of the page specific version
	 */
	Comments requestPageCommentsInHistory(String wikiName,
			String spaceName, String pageName, String version);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param version version of the page
	 * @param commentId ID of the comment
	 * @return comments Comments of specific comment ID in specific page history version
	 */
	Comments requestPageCommentsInHistory(String wikiName,
			String spaceName, String pageName, String version, String commentId);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return list all the attachments in a specific page
	 */
	Attachments requestAllPageAttachments(String wikiName,
			String spaceName, String pageName);

	/**
	 * Gets a stream of data of a attachment
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param attachmentName filename of the attachment.
	 * @return InputStream of the attachment raw data
	 */
	InputStream requestPageAttachment(String wikiName,
			String spaceName, String pageName, String attachmentName);

	/**
	 * Add attachment to a page
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param filePath absolute path to the attachment in internal/external memory of the device
	 * @param attachmentName filename of the attachment
	 * @return status of the HTTP response
	 */
	String addPageAttachment(String wikiName, String spaceName,
			String pageName, String filePath, String attachmentName);

	/**
	 * Delete the page attachment by its name
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param attachmentName name of the attachment
	 * @return status of the HTTP response
	 */
	String deletePageAttachment(String wikiName,
			String spaceName, String pageName, String attachmentName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param pageVersion version of the page
	 * @return list of all attachments in the specified page history version
	 */
	Attachments requestPageAttachmentsInHistory(
			String wikiName, String spaceName, String pageName,
			String pageVersion);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param pageVersion version of the page
	 * @param attachmentName filename of the attachment
	 * @return InputStream of attachment raw data in a specific page history version
	 */
	InputStream requestPageAttachmentsInHistory(
			String wikiName, String spaceName, String pageName,
			String pageVersion, String attachmentName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param attachmentName filename of the attachment
	 * @return list of attachment version history of a specific attachment
	 */
	Attachments requestPageAttachmentsInAttachmentHistory(
			String wikiName, String spaceName, String pageName,
			String attachmentName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param attachmentName filename of the attachment
	 * @param attachmentVersion version of the attachment
	 * @return Stream of attachment raw data in a specific attachment version of a specified page
	 */
	InputStream requestPageAttachmentsInAttachmentHistory(
			String wikiName, String spaceName, String pageName,
			String attachmentName, String attachmentVersion);

	/**
	 * @param wikiName name of the Wiki
	 * @return lists all the classes in the specified Wiki
	 */
	Classes requestWikiClasses(String wikiName);

	/**
	 * @param wikiName name of the Wiki
	 * @param classname name of the class
	 * @return Class of the provided classname in the specified Wiki
	 */
	Class requestWikiClasses(String wikiName, String classname);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return list all the Translations of a specific page
	 */
	Translations requestAllPageTranslations(String wikiName,
			String spaceName, String pageName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param language language name of the page translations
	 * @return Page of the requested language
	 */
	Page requestPageTranslation(String wikiName,
			String spaceName, String pageName, String language);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param language language of the page
	 * @return Page history versions in a given language
	 */
	History requestPageHistoryInLanguage(String wikiName,
			String spaceName, String pageName, String language);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param language language of the page
	 * @param version version of the page
	 * @return Translated page of the given history version
	 */
	Page requestPageTranslationInHistory(String wikiName,
			String spaceName, String pageName, String language, String version);

	/**
	 * Adds provided object to the given page
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param object Object to be added to page
	 * @return status of the HTTP response
	 */
	String addObject(String wikiName, String spaceName,
			String pageName, Object object);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @return All the objects of the specified page
	 */
	Objects requestAllObjects(String wikiName,
			String spaceName, String pageName);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of the objects
	 * @return All the objects which have given class name of the specified page
	 */
	Objects requestObjectsInClass(String wikiName,
			String spaceName, String pageName, String objectClassname);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of the objects
	 * @param objectNumber number of the object in the specific class
	 * @return Object of the given class name and object number of the specified page
	 */
	Object requestObject(String wikiName, String spaceName,
			String pageName, String objectClassname, String objectNumber);

	/**
	 * Deletes the given object. Object can be selected using class name and the object number
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of the objects
	 * @param objectNumber number of the object in the specified class
	 * @return status of the HTTP response of the delete operation
	 */
	String deleteObject(String wikiName, String spaceName,
			String pageName, String objectClassname, String objectNumber);

	/** 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of the objects
	 * @param objectNumber number of the object in the specified class
	 * @return lists all the properties of a specific object
	 */
	Properties requestObjectAllProperties(String wikiName,
			String spaceName, String pageName, String objectClassname,
			String objectNumber);

	/**
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of objects
	 * @param objectNumber number of the object in the page
	 * @param propertyName name of the property in the object
	 * @return Property out of list of properties in a given object of a page
	 */
	Property requestObjectProperty(String wikiName,
			String spaceName, String pageName, String objectClassname,
			String objectNumber, String propertyName);

	/**
	 * Adds the property to the object of a page
	 * 
	 * @param wikiName name of the Wiki
	 * @param spaceName name of the space
	 * @param pageName name of the page
	 * @param objectClassname class name of the objects
	 * @param objectNumber number of the object
	 * @param property Property object to be added to the object in a page
	 * @return status of the HTTP response
	 */
	String addProperty(String wikiName, String spaceName,
			String pageName, String objectClassname, String objectNumber,
			Property property);

}