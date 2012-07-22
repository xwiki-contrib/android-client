package org.xwiki.android.ral.transformation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xwiki.android.ral.reference.Link;
import org.xwiki.android.ral.wrappers.XSimpleObjectWrapper_RAL;
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
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public class DocumentBuilder_XML
{
    Document d;
    XObjectFactory xofac;
    XPropertyFactory xpfac;

    public DocumentBuilder_XML(Page page)
    {
        initWithPage(page);
        xofac = new XObjectFactory();
        xpfac = new XPropertyFactory();
    }

    private DocumentBuilder_XML initWithPage(Page p)
    {

        d = new Document(p.wiki, p.space, p.name);

        List<org.xwiki.android.resources.Link> linksRes = p.links;
        List<Link> links = new ArrayList<Link>();
        for (org.xwiki.android.resources.Link lres : linksRes) {
            Link l = new Link();
            l.setHref(lres.href);
            l.setRelType(lres.getRel());
            links.add(l);
        }
        d.setLinks(links);

        d.setRemoteId(p.id);
        d.setFullName(p.fullName);
        d.setWikiName(p.wiki);
        d.setSpaceName(p.space);
        d.setSimpleName(p.name);
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
        d.setCreated(new Date(p.created));
        d.setCreator(p.creator);
        d.setModified(new Date(p.modified));
        d.setModifier(p.modifier);
        d.setContent(p.content);

        return this;
    }

    /**
     * puts a XSimpleObject representing objRes. May be a general XSimpleObject when there does not exist a special
     * XSimpleObject (like XBlogPost) for the object resource's className.
     * 
     * @param res
     * @return builder; so you can chain method calls.
     */
    public DocumentBuilder_XML withObject(Object res)
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
        }
        return this;
    }

    public DocumentBuilder_XML withObjectSummary(ObjectSummary res)
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
        d.setObject(res.className + "/" + res.number, xowrap);

        return this;
    }

    public DocumentBuilder_XML withComments(Comments cmnts)
    {
        for (Comment cr : cmnts.comments) {
            withComment(cr);
        }
        return this;
    }

    public DocumentBuilder_XML withComment(Comment cmntRes)
    {
        org.xwiki.android.xmodel.entity.Comment c = new org.xwiki.android.xmodel.entity.Comment();
        c.setId(cmntRes.id);
        c.setAuthor(cmntRes.author);
        c.setDate(new Date(cmntRes.date));
        c.setText(cmntRes.text);
        c.setReplyTo(cmntRes.replyTo);
        d.addComment(c);
        // TODO: keep link if needed. c.addLink(new Link...cmntRes.getLinks().get(0) ...);
        return this;
    }

    public DocumentBuilder_XML withTags(Tags tags)
    {
        for (Tag tr : tags.getTags()) {
            org.xwiki.android.xmodel.entity.Tag t = new org.xwiki.android.xmodel.entity.Tag(tr.name);
            d.addTag(t);
        }
        return this;
    }

    public DocumentBuilder_XML withAttachments(Attachments attachments)
    {
        for(Attachment ares:attachments.getAttachments()){
            withAttachment(ares);
        }
        return this;
    }

    public DocumentBuilder_XML withAttachment(Attachment attachment)
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
        a.setDate(new Date(attachment.date));
        
        URL absUrl=null;
        URL relUrl=null;
        try {
            absUrl = new URL(attachment.xwikiAbsoluteUrl);
            relUrl = new URL(attachment.xwikiRelativeUrl);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        a.setXwikiAbsoluteUrl(absUrl);// can convert to URL to make android independant.
        a.setXwikiRelativeUrl(relUrl);
        
        return this;
    }

    // public DocumentBuilder_XML withLinks(List<Link> links)
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
    public Document build()
    {
        return d;
    }

}
