package org.xwiki.android.rest.transformation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translation;
import org.xwiki.android.resources.Translations;

import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.XWikiPage;
import org.xwiki.android.xmodel.xobjects.XComment;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public class XModelTranslator_XML
{
    public  Page toPage(XWikiPage doc)
    {
        Page page = new Page();

        page.id = doc.getId();
        page.fullName = doc.getFullName();
        page.wiki = doc.getWikiName();
        page.space = doc.getSpaceName();
        page.name = doc.getPageName();
        page.title = doc.getTitle();
        page.parent = doc.getParentFullName();
        page.parentId = doc.getParentId();
        page.xwikiRelativeUrl = doc.getXwikiRelativeUrl();
        page.xwikiAbsoluteUrl = doc.getXwikiAbsoluteUrl();
        page.translations = null;
        { // set page.translations
            List<String> ts = doc.getTranslations();
            if (ts != null) {
                List<Translation> trnList = new ArrayList<Translation>();
                for (String t : ts) {
                    Translation trn = new Translation();
                    trn.setLanguage(t);
                    trnList.add(trn);
                }
                Translations trsElement = new Translations();
                trsElement.translations = trnList;
                page.translations = trsElement;
            }
        }
        page.syntax = doc.getSyntax();
        page.language = doc.getLanguage();
        page.version = doc.getVersion();
        page.majorVersion = doc.getMajorVersion();
        page.minorVersion = doc.getMinorVersion();
        if (doc.getCreated() != null) {
            page.created = doc.getCreated().toGMTString();
        }
        page.creator = doc.getCreator();
        if (doc.getModified() != null) {
            page.modified = doc.getModified().toGMTString();
        }
        page.modifier = doc.getModifier();
        page.content = doc.getContent();

        return page;
    }
    
    public  Object toObject(XSimpleObject xso)
    {

        Object obj = new Object();
        obj.id = xso.getId();
        obj.guid = xso.getGuid();
        obj.pageId = xso.getPageid();
        obj.wiki = xso.getWiki();
        obj.space = xso.getSpace();
        obj.pageName = xso.getPageName();
        obj.number = xso.getNumber();
        obj.className = xso.getClassName();
        obj.headline = xso.getHeadline();

        Map<String, XProperty> xprops = xso.getProperties();
        Set<Entry<String, XProperty>> entrySet = xprops.entrySet();

        List<Property> plist = new ArrayList<Property>();
        for (Entry<String, XProperty> entry : entrySet) {

            XProperty xp = entry.getValue();
            String pName = entry.getKey();
            // transfer fields to resource.Property
            if(xp!=null){
            	Property p = new Property();
                p.setName(pName);
                p.setType(xp.getType());
                p.setValue(xp.toString());

                Map<String, java.lang.Object> attrs = xp.getAllAttributes();
                Set<Entry<String, java.lang.Object>> atrEntrySet = attrs.entrySet();
                List<Attribute> attrList = new ArrayList();
                for (Entry<String, java.lang.Object> atrEn : atrEntrySet) {
                    Attribute atr = new Attribute();
                    atr.setName(atrEn.getKey());
                    atr.setValue(atrEn.getValue().toString());
                    attrList.add(atr);
                }
                plist.add(p.withAttributes(attrList));
            }       
        }
        return obj.withProperties(plist);

    }
    
    public Object toObject(org.xwiki.android.xmodel.entity.Comment c){
    	XComment xc=new XComment();    	
    	xc.setNumber((c.getId()));
    	if(c.getAuthor()!=null){
    	    xc.setAuthor(c.getAuthor());
    	}
    	if(c.getDate()!=null){
    	    xc.setDate(c.getDate());
    	}		
		xc.setComment(c.getText());
		if(c.getReplyTo()!=-1){
		    xc.setReplyto(c.getReplyTo());        
		}		
		return toObject(xc);
    }
    
    

    public Comment toComment(org.xwiki.android.xmodel.entity.Comment c)
    {
        Comment cres = new Comment();
        cres.id = c.getId();
        cres.author = c.getAuthor();
        if(c.getDate()!=null){
        	cres.date = c.getDate().toGMTString();
        }        
        cres.highlight = c.getHighlight();
        cres.text = c.getText();
        if(!(c.getReplyTo()==-1)){
            cres.replyTo =c.getReplyTo();
        }        
        return cres;

    }

   
    
   

    
	public Tags toTags(List<org.xwiki.android.xmodel.entity.Tag> tags)
    {
        Tags tgsres = new Tags();
        List<org.xwiki.android.resources.Tag> tgResList = new ArrayList<org.xwiki.android.resources.Tag>();
        for (org.xwiki.android.xmodel.entity.Tag t : tags) {
            org.xwiki.android.resources.Tag tres = new org.xwiki.android.resources.Tag();
            tres.setName(t.getName());
            tgResList.add(tres);
        }
        tgsres.tags = tgResList;
        return tgsres;
    }

    public Tag toTag(org.xwiki.android.xmodel.entity.Tag tag)
    {
        Tag tres = new Tag();
        tres.setName(tag.getName());
        return tres;
    }

    public Attachment toAttachment(org.xwiki.android.xmodel.entity.Attachment a)
    {
        Attachment atres = new Attachment();
        atres.setId(a.getId());
        atres.setName(a.getName());
        atres.setSize(a.getSize());
        atres.setVersion(a.getVersion());
        atres.setPageId(a.getPageId());
        atres.setPageVersion(a.getVersion());
        atres.setMimeType(a.getMimeType());
        atres.setAuthor(a.getAuthor());
        if(atres.getDate()!=null){
        	atres.setDate(a.getDate().toGMTString());
        }
        if(a.getXwikiAbsoluteUrl()!=null){
        	atres.setXwikiAbsoluteUrl(a.getXwikiAbsoluteUrl().toString());
        }
        if(a.getXwikiRelativeUrl()!=null){
        	atres.setXwikiRelativeUrl(a.getXwikiAbsoluteUrl().toString());
        }
        return null;
    }

}

//TODO overload above methods with ex: toComment(Comment c, boolean minimal) where minimal true means 
// only essential data to create a comment in the server are put to resources.Comment DTO.
