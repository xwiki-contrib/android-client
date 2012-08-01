package org.xwiki.android.rest.transformation;

import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.ObjectSummary;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentBuilder
{

    /**
     * puts a XSimpleObject representing objRes. May be a general XSimpleObject when there does not exist a special
     * XSimpleObject (like XBlogPost) for the object resource's className.
     * 
     * @param res
     * @return builder; so you can chain method calls.
     */
    public abstract DocumentBuilder withObject(Object res);

    public abstract DocumentBuilder withObjectSummary(ObjectSummary res);

    public abstract DocumentBuilder withComments(Comments cmnts);

    public abstract DocumentBuilder withComment(Comment cmntRes);

    public abstract DocumentBuilder withTags(Tags tags);

    public abstract DocumentBuilder withAttachments(Attachments attachments);

    public abstract DocumentBuilder withAttachment(Attachment attachment);

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
    public abstract Document build();

}
