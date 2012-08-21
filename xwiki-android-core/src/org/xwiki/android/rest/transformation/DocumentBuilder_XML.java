package org.xwiki.android.rest.transformation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.ObjectSummary;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translation;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.rest.ral.wrappers.XSimpleObjectWrapper_RAL;
import org.xwiki.android.rest.reference.Link;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.XWikiPage;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;
import org.xwiki.android.xmodel.xobjects.XUtils;

import android.util.Log;

public class DocumentBuilder_XML implements DocumentBuilder
{
    private final String TAG = this.getClass().getSimpleName();

    Document d;
    XObjectFactory xofac;
    XPropertyFactory xpfac;

    public DocumentBuilder_XML(Page page)
    {
        initWithPage(page);
        xofac = new XObjectFactory();
        xpfac = new XPropertyFactory();
    }

    public DocumentBuilder_XML(Document doc)
    {
        d = doc;
        xofac = new XObjectFactory();
        xpfac = new XPropertyFactory();
    }

    /**
     * Create document without page data. Build it wrapped with a DocumentWrapper.
     * 
     * @param wikiName
     * @param spaceName
     * @param pageName
     */
    public DocumentBuilder_XML(String wikiName, String spaceName, String pageName)
    {
        d = new Document(wikiName, spaceName, pageName);
    }

    /**
     * start off with a new document. The earlier build will be lost.
     * 
     * @param p
     * @return
     */
    public DocumentBuilder initWithPage(Page p)
    {
        d = new Document(p.wiki, p.space, p.name);
        return withPage(p);
    }

    public DocumentBuilder withPage(Page p)
    {
        List<org.xwiki.android.resources.Link> linksRes = p.links;
        if (linksRes != null) {
            List<Link> links = new ArrayList<Link>();
            for (org.xwiki.android.resources.Link lres : linksRes) {
                Link l = new Link();
                l.setHref(lres.href);
                l.setRelType(lres.getRel());
                links.add(l);
            }
            d.setLinks(links);
        }

        d.setId(p.id);
        d.setFullName(p.fullName);
        d.setWikiName(p.wiki);
        d.setSpaceName(p.space);
        d.setPageName(p.name);
        d.setTitle(p.title);
        d.setParentFullName(p.parent);
        d.setParentId(p.parentId);
        d.setXwikiRelativeUrl(p.xwikiRelativeUrl);
        d.setXwikiAbsoluteUrl(p.xwikiAbsoluteUrl);

        Translations trsRes = p.getTranslations();
        if (!trsRes.getTranslations().isEmpty()) {
            List<String> ts = new ArrayList<String>();
            for (Translation tres : trsRes.translations) {
                String t = tres.getLanguage();
                ts.add(t);
            }
            d.setTranslations(ts);
            d.setDefalutTranslation(trsRes.getDefault());
        }

        d.setSyntax(p.syntax);
        d.setLanguage(p.language);
        d.setVersion(p.version);
        d.setMajorVersion(p.majorVersion);
        d.setMinorVersion(p.minorVersion);
        d.setCreated(XUtils.toDate(p.created));
        d.setCreator(p.creator);
        d.setModified(XUtils.toDate(p.modified));
        d.setModifier(p.modifier);
        d.setContent(p.content);

        return this;
    }

    public DocumentBuilder withTranslatedPage(Page trns)
    {
        XWikiPage page = new XWikiPage(null, null, null)
        {
        };
        List<org.xwiki.android.resources.Link> linksRes = trns.links;
        if (linksRes != null) {
            List<Link> links = new ArrayList<Link>();
            for (org.xwiki.android.resources.Link lres : linksRes) {
                Link l = new Link();
                l.setHref(lres.href);
                l.setRelType(lres.getRel());
                links.add(l);
            }
            page.setLinks(links);
        }

        page.setId(trns.id);
        page.setFullName(trns.fullName);
        page.setWikiName(trns.wiki);
        page.setSpaceName(trns.space);
        page.setPageName(trns.name);
        page.setTitle(trns.title);
        page.setParentFullName(trns.parent);
        page.setParentId(trns.parentId);
        page.setXwikiRelativeUrl(trns.xwikiRelativeUrl);
        page.setXwikiAbsoluteUrl(trns.xwikiAbsoluteUrl);

        Translations trsRes = trns.getTranslations();
        if (!trsRes.getTranslations().isEmpty()) {
            List<String> ts = new ArrayList<String>();
            for (Translation tres : trsRes.translations) {
                String t = tres.getLanguage();
                ts.add(t);
            }
            page.setTranslations(ts);
            page.setDefalutTranslation(trsRes.getDefault());
        }

        page.setSyntax(trns.syntax);
        page.setLanguage(trns.language);
        page.setVersion(trns.version);
        page.setMajorVersion(trns.majorVersion);
        page.setMinorVersion(trns.minorVersion);
        page.setCreated(XUtils.toDate(trns.created));
        page.setCreator(trns.creator);
        page.setModified(XUtils.toDate(trns.modified));
        page.setModifier(trns.modifier);
        page.setContent(trns.content);

        page.setEdited(false);
        d.setTranslationPage(page);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withObject(org.xwiki.android.resources.Object)
     */
    @Override
    public DocumentBuilder withObject(Object res)
    {
        XSimpleObject xo = xofac.newXSimpleObject(res.className);
        // object's links not supported yet.For performance enhancements.

        // String xokey=res.getClassName()+"/"+res.getNumber();
        // Log.d(TAG, "creating xobject for "+xokey);

        xo.setId(res.id);
        xo.setGuid(res.guid);
        xo.setPageid(res.pageId);
        xo.setWiki(res.wiki);
        xo.setSpace(res.space);
        xo.setPageName(res.pageName);
        // xo.setClassName(res.className); //not needed. set by factory.
        xo.setNumber(res.number);
        xo.setHeadline(res.headline);

        for (Property pres : res.properties) {
            String key = pres.getName();
            XProperty xp = xpfac.newXProperty(pres.type);
            // set the attributes of the prop
            for (Attribute a : pres.getAttributes()) {
                xp.setAttribute(a.getName(), a.getValue());
            }
            // xp.setType(pres.getType());//implicit for the XProperty obj.
            xp.setName(pres.name);
            xp.setValueFromString(pres.getValue());
            xo.setProperty(key, xp);
            Log.d(TAG, "added xproperty " + key);
        }
        xo.setEdited(false);
        d.setObject(xo);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withObjectSummary(org.xwiki.android.resources.ObjectSummary
     * )
     */
    @Override
    public DocumentBuilder withObjectSummary(ObjectSummary res)
    {
        XSimpleObject xo = xofac.newXSimpleObject(res.className);
        // object's links not supported yet.For performance enhancements.

        xo.setId(res.id);
        xo.setGuid(res.guid);
        xo.setPageid(res.pageId);
        xo.setWiki(res.wiki);
        xo.setSpace(res.space);
        xo.setPageName(res.pageName);
        // xo.setClassName(res.className); //not needed. set by factory.
        xo.setNumber(res.number);
        xo.setHeadline(res.headline);
        XSimpleObjectWrapper_RAL xowrap = new XSimpleObjectWrapper_RAL(xo);

        xo.setEdited(false);
        d.setObject(xowrap);

        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withComments(org.xwiki.android.resources.Comments)
     */
    @Override
    public DocumentBuilder withComments(Comments cmnts)
    {
        for (Comment cr : cmnts.comments) {
            withComment(cr);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withComment(org.xwiki.android.resources.Comment)
     */
    @Override
    public DocumentBuilder withComment(Comment cmntRes)
    {
        org.xwiki.android.xmodel.entity.Comment c = new org.xwiki.android.xmodel.entity.Comment();

        c.setId(-1);
        c.setId(cmntRes.id);// if u send a comment resource without seting id it will always have id =0; Though
                            // entity.comment has def val -1 it will be overriden
        c.setAuthor(cmntRes.author);
        c.setDate(XUtils.toDate(cmntRes.date));
        c.setText(cmntRes.text);
        if (cmntRes.replyTo != null) {
            c.setReplyTo(cmntRes.replyTo);
        }

        c.setEdited(false);
        d.setComment(c);

        // TODO: keep link if needed. c.addLink(new Link...cmntRes.getLinks().get(0) ...);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withTags(org.xwiki.android.resources.Tags)
     */
    @Override
    public DocumentBuilder withTags(Tags tags)
    {
        for (Tag tr : tags.getTags()) {
            org.xwiki.android.xmodel.entity.Tag t = new org.xwiki.android.xmodel.entity.Tag(tr.name);
            t.setEdited(false);
            d.addTag(t);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withAttachments(org.xwiki.android.resources.Attachments)
     */
    @Override
    public DocumentBuilder withAttachments(Attachments attachments)
    {
        for (Attachment ares : attachments.getAttachments()) {
            withAttachment(ares);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.xwiki.android.xmlrpc.transformation.DocumentBuilder#withAttachment(org.xwiki.android.resources.Attachment)
     */
    @Override
    public DocumentBuilder withAttachment(Attachment attachment)
    {
        org.xwiki.android.xmodel.entity.Attachment a = new org.xwiki.android.xmodel.entity.Attachment();
        a.setId(attachment.id);
        a.setName(attachment.name);
        a.setSize(attachment.size);
        a.setVersion(attachment.version);
        a.setPageId(attachment.pageId);
        a.setPageVersion(attachment.version);
        a.setMimeType(attachment.mimeType);
        a.setAuthor(attachment.author);
        a.setDate(XUtils.toDate(attachment.date));

        URL absUrl = null;
        URL relUrl = null;
        try {
            absUrl = new URL(attachment.xwikiAbsoluteUrl);
            relUrl = new URL(attachment.xwikiRelativeUrl);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        a.setXwikiAbsoluteUrl(absUrl);// can convert to URL to make android independant.
        a.setXwikiRelativeUrl(relUrl);

        a.setEdited(false);
        a.setNew(false);
        d.setAttachment(a);
        return this;
    }
    
//    @Override
//    public DocumentBuilder withHistory(Attachments attachments)
//    {
//        for (Attachment ares : attachments.getAttachments()) {
//            withAttachment(ares);
//        }
//        return this;
//    }

    // 
    // These methods are too fine grain to be worth it. 
    //
    //public DocumentBuilder_XML withLinks(List<Link> links)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withRemoteId(String id)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withFullName(String fullName)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withWikiName(String wiki)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withSpaceName(String space)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withSimpleName(String name)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withTitle(String title)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withParentFullName(String parent)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withParentId(String parentId)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withXwikiRelativeUrl(String xwikiRelativeUrl)
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withXwikiAbsoluteUrl()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withTranslations(Translations translations)
    // {
    // //set String defalutTranslation; from the Translation element attr.
    // return this;
    // }
    //
    // public DocumentBuilder_XML withSyntax()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withLanguage()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withVersion()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withMajorVersion()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withMinorVersion()
    // {
    // return this;
    // }
    //
    // public DocumentBuilder_XML withCreated()
    // {
    // return this;
    // }
    // public DocumentBuilder_XML withCreator()
    // {
    // return this;
    // }
    // public DocumentBuilder_XML withModified()
    // {
    // return this;
    // }
    // public DocumentBuilder_XML withModifier()
    // {
    // return this;
    // }
    // public DocumentBuilder_XML withContent()
    // {
    // return this;
    // }
    //
    
    /*
     * (non-Javadoc)
     * @see org.xwiki.android.xmlrpc.transformation.DocumentBuilder#build()
     */
    @Override
    public Document build()
    {
        return d;
    }

}
