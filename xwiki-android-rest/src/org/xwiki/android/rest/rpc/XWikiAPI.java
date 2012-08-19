package org.xwiki.android.rest.rpc;

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
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

/**
 * 
 * @author xwiki gsoc 2012
 * 
 */
public interface XWikiAPI
{

	/**
	 * Set the user credentials for accession
	 * 
	 * @param username
	 *            username of the user account in XWiki
	 * @param password
	 *            password of the user account in XWiki
	 */
	void setAuthentication(String username, String password);

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param keyword
	 *            text to search
	 * @return Search results of the searched keyword within Wiki contents
	 */
	SearchResults searchInWiki(String wikiName, String keyword) throws RestConnectionException, RestException,
			RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param keyword
	 *            text to search
	 * @return Search results of the searched keyword in provided space
	 */
	SearchResults searchInSpace(String wikiName, String spaceName, String keyword) throws RestConnectionException,
			RestException, RestException;

	/**
	 * @return list of wikis in the provided XWiki URL domain
	 */
	Wikis getWikis() throws RestConnectionException, RestException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @return all the tags used in the wiki
	 */
	Tags getWikiTags(String wikiName) throws RestConnectionException, RestException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @return lists all the classes in the specified Wiki
	 */
	Classes getWikiClasses(String wikiName) throws RestConnectionException, RestException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param classname
	 *            name of the class
	 * @return Class of the provided classname in the specified Wiki
	 */
	Class getWikiClasses(String wikiName, String classname) throws RestConnectionException, RestException,
			RestException;

	/**
	 * @param wikiName
	 *            name of the wiki
	 * @return list of spaces in the provided Wiki
	 */
	Spaces getSpaces(String wikiName) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the wiki
	 * @param spaceName
	 *            name of the space
	 * @return list of all the pages in specific Wiki and Space
	 */
	Pages getAllPages(String wikiName, String spaceName) throws RestConnectionException, RestException;
	
	boolean existsPage(String wikiName, String spaceName, String pageName) throws RestConnectionException,RestException;

	/**
	 * @param wikiName
	 *            name of the wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return Page object according to the field values
	 */
	Page getPage(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException;

	/**
	 * Add page to the server according to the provided field values
	 * 
	 * @param wikiName
	 *            name of the wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param page
	 *            Page object which is to be added
	 * @return status of the HTTP response
	 */
	String addPage(String wikiName, String spaceName, String pageName, Page page) throws RestConnectionException,
			RestException;

	/**
	 * Delete page in the server
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return status of the HTTP response
	 */
	String deletePage(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException;

	/**
	 * Delete page translation
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param language
	 *            specific language to delete
	 * @return status of the HTTP response
	 */
	String deletePage(String wikiName, String spaceName, String pageName, String language)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return history of the page according to the field values
	 */
	History getPageHistory(String wikiName, String spaceName, String pageName) throws RestConnectionException,
			RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param version
	 *            version of the page
	 * @return Page of the specified version
	 */
	Page getPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return Children pages of the specific page
	 */
	Pages getPageChildren(String wikiName, String spaceName, String pageName) throws RestConnectionException,
			RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return Tags of the page
	 */
	Tags getPageTags(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException;

	/**
	 * Adds page tag to the provided page
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param tag
	 *            tag to be added
	 * @return status of the HTTP response
	 */
	String addPageTag(String wikiName, String spaceName, String pageName, Tag tag) throws RestConnectionException,
			RestException;

	/**
	 * 
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @param tags set of tags to replace with
	 * @return
	 * @throws RestConnectionException
	 * @throws RestException
	 */
	public String setTags(String wikiName, String spaceName, String pageName,Tags tags) throws RestConnectionException, RestException;

	/**
	 * get all comments in page
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return all the comments in the specific page
	 */
	Comments getPageComments(String wikiName, String spaceName, String pageName) throws RestConnectionException,
			RestException;

	/**
	 * check whether a comment exists for the given page.
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @param commentId
	 * @return
	 * @throws RestConnectionException
	 * @throws RestException
	 */
	public boolean existsPageComment(String wikiName, String spaceName, String pageName,int commentId) throws RestConnectionException, RestException;
    
	/**
	 * get a comment.
     * @param commentId ID of the comment in the page
     * @return comment of a page selected using comment ID
     * @throws RestConnectionException
     * @throws RestException
     */
    public Comment getPageComment(String wikiName, String spaceName, String pageName, int commentId) throws RestConnectionException, RestException;
	
	/**
	 * Add comment to the page
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param comment
	 *            Comment object to be added
	 * @return status of the HTTP response
	 */	
	
	String addPageComment(String wikiName, String spaceName, String pageName, Comment comment)
			throws RestConnectionException, RestException;

	
	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param version
	 *            version of the page
	 * @return All the comments of the page specific version
	 */
	Comments getPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param version
	 *            version of the page
	 * @param commentId
	 *            ID of the comment
	 * @return comments Comments of specific comment ID in specific page history
	 *         version
	 */
	Comments getPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version,
			String commentId) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return list all the attachments in a specific page
	 */
	Attachments getAllPageAttachments(String wikiName, String spaceName, String pageName)
			throws RestConnectionException, RestException;

	/**
	 * Gets a stream of data of a attachment
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param attachmentName
	 *            filename of the attachment.
	 * @return InputStream of the attachment raw data
	 */
	InputStream getPageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
			throws RestConnectionException, RestException;

	/**
	 * Add attachment to a page
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param filePath
	 *            absolute path to the attachment in internal/external memory of
	 *            the device
	 * @param attachmentName
	 *            filename of the attachment
	 * @return status of the HTTP response
	 */
	String putPageAttachment(String wikiName, String spaceName, String pageName, String filePath, String attachmentName)
			throws RestConnectionException, RestException;

	/**
	 * Delete the page attachment by its name
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param attachmentName
	 *            name of the attachment
	 * @return status of the HTTP response
	 */
	String deletePageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param pageVersion
	 *            version of the page
	 * @return list of all attachments in the specified page history version
	 */
	Attachments getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName, String pageVersion)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param pageVersion
	 *            version of the page
	 * @param attachmentName
	 *            filename of the attachment
	 * @return InputStream of attachment raw data in a specific page history
	 *         version
	 */
	InputStream getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName, String pageVersion,
			String attachmentName) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param attachmentName
	 *            filename of the attachment
	 * @return list of attachment version history of a specific attachment
	 */
	Attachments getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
			String attachmentName) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param attachmentName
	 *            filename of the attachment
	 * @param attachmentVersion
	 *            version of the attachment
	 * @return Stream of attachment raw data in a specific attachment version of
	 *         a specified page
	 */
	InputStream getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
			String attachmentName, String attachmentVersion) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return list all the Translations of a specific page
	 */
	Translations getAllPageTranslations(String wikiName, String spaceName, String pageName)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param language
	 *            language name of the page translations
	 * @return Page of the geted language
	 */
	Page getPageTranslation(String wikiName, String spaceName, String pageName, String language)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param language
	 *            language of the page
	 * @return Page history versions in a given language
	 */
	History getPageHistoryInLanguage(String wikiName, String spaceName, String pageName, String language)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param language
	 *            language of the page
	 * @param version
	 *            version of the page
	 * @return Translated page of the given history version
	 */
	Page getPageTranslationInHistory(String wikiName, String spaceName, String pageName, String language, String version)
			throws RestConnectionException, RestException;

	
	boolean existsObject(String wikiName, String spaceName, String pageName, String objectClassname,
            int objectNumber)throws RestConnectionException,RestException;
	/**
	 * Adds provided object to the given page
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param object
	 *            Object to be added to page
	 * @return status of the HTTP response
	 */
	String addObject(String wikiName, String spaceName, String pageName, Object object) throws RestConnectionException,
			RestException;

	/**
	 * Update object in a page
	 * 
	 * @param objectClassname
	 *            name of the class of the object
	 * @param objectNumber
	 *            number of the object in the class
	 * @param object
	 *            Object object to be updated in the page
	 * @return status of the HTTP put request
	 * @throws RestConnectionException
	 * @throws RestException
	 */
	public String updateObject(String wikiName, String spaceName, String pageName, String objectClassname,
			int objectNumber, Object object) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @return All the objects of the specified page
	 */
	Objects getAllObjects(String wikiName, String spaceName, String pageName) throws RestConnectionException,
			RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of the objects
	 * @return All the objects which have given class name of the specified page
	 */
	Objects getObjectsInClass(String wikiName, String spaceName, String pageName, String objectClassname)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of the objects
	 * @param objectNumber
	 *            number of the object in the specific class
	 * @return Object of the given class name and object number of the specified
	 *         page
	 */
	Object getObject(String wikiName, String spaceName, String pageName, String objectClassname, int objectNumber)
			throws RestConnectionException, RestException;

	/**
	 * Deletes the given object. Object can be selected using class name and the
	 * object number
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of the objects
	 * @param objectNumber
	 *            number of the object in the specified class
	 * @return status of the HTTP response of the delete operation
	 */
	String deleteObject(String wikiName, String spaceName, String pageName, String objectClassname, String objectNumber)
			throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of the objects
	 * @param objectNumber
	 *            number of the object in the specified class
	 * @return lists all the properties of a specific object
	 */
	Properties getObjectAllProperties(String wikiName, String spaceName, String pageName, String objectClassname,
			String objectNumber) throws RestConnectionException, RestException;

	/**
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of objects
	 * @param objectNumber
	 *            number of the object in the page
	 * @param propertyName
	 *            name of the property in the object
	 * @return Property out of list of properties in a given object of a page
	 */
	Property getObjectProperty(String wikiName, String spaceName, String pageName, String objectClassname,
			String objectNumber, String propertyName) throws RestConnectionException, RestException;
	//TODO: refactor number to int

	/**
	 * Adds the property to the object of a page
	 * 
	 * @param wikiName
	 *            name of the Wiki
	 * @param spaceName
	 *            name of the space
	 * @param pageName
	 *            name of the page
	 * @param objectClassname
	 *            class name of the objects
	 * @param objectNumber
	 *            number of the object
	 * @param property
	 *            Property object to be added to the object in a page
	 * @return status of the HTTP response
	 */
	String addObjectProperty(String wikiName, String spaceName, String pageName, String objectClassname,
			String objectNumber, Property property) throws RestConnectionException, RestException;

}