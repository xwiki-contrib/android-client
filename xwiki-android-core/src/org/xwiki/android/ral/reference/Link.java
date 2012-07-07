package org.xwiki.android.ral.reference;

import java.io.Serializable;

/**
 * @author xwiki gsoc 2012 http://www.xwiki.org/rel/wikis The representation containing the list of virtual wikis.
 *         http://www.xwiki.org/rel/spaces The representation containing the list of spaces in a wiki.
 *         http://www.xwiki.org/rel/pages The representation containing the list of pages in a space.
 *         http://www.xwiki.org/rel/translation The representation containing a translation of a page.
 *         http://www.xwiki.org/rel/page The representation for a page. http://www.xwiki.org/rel/space The
 *         representation for a space. http://www.xwiki.org/rel/parent The representation for the page that is parent of
 *         the current resource. http://www.xwiki.org/rel/home The representation for the page that is the home of the
 *         current resource. http://www.xwiki.org/rel/attachmentData The representation of the actual attachment data.
 *         http://www.xwiki.org/rel/comments The representation of the list of comments associated to the current
 *         resource. http://www.xwiki.org/rel/attachments The representation of the list of attachments associated to
 *         the current resource. http://www.xwiki.org/rel/objects The representation of the list of objects associated
 *         to the current resource. http://www.xwiki.org/rel/object The representation for an object.
 *         http://www.xwiki.org/rel/classes The representation of the list of classes associated to the current
 *         resource. http://www.xwiki.org/rel/history The representation of the list of history information associated
 *         to the current resource. http://www.xwiki.org/rel/class The representation for a class.
 *         http://www.xwiki.org/rel/property The representation for a property. http://www.xwiki.org/rel/properties The
 *         representation of the list of properties associated to the current resource.
 *         http://www.xwiki.org/rel/modifications The representation of the list of modifications associated to the
 *         current resource. http://www.xwiki.org/rel/children The representation of the list of children associated to
 *         the current resource. http://www.xwiki.org/rel/tags The representation of the list of tags associated to the
 *         current resource. http://www.xwiki.org/rel/tag The representation of a tag. http://www.xwiki.org/rel/search
 *         The representation for a search resource. http://www.xwiki.org/rel/syntaxes The representation for a syntax
 *         resource.
 */

// TODO: implement all above.

public class Link implements Serializable
{
    private static final String REL_SPACE = "space";

    private static final String REL_PARENT = "parent";

    private static final String REL_CHILDREN = "children";

    private static final String REL_HISTORY = "history";

    private static final String REL_COMMENTS = "comments";

    private static final String REL_OBJECTS = "objects";

    private static final String REL_TAGS = "tags";

    private static final String REL_ATTACHMENTS = "attachments";

    private static final String REL_SYNTAXES = "syntaxes";

    private static final String REL_SELF = "self";

    private static final String REL_CLASS = "class";

    private String type;

    private String href;

    /**
     * truncates the fully qualified type name and sets ex: http://www.xwiki.org/rel/space --> space
     * 
     * @param fullTypeid
     */
    public void setType(String fqtypeName)
    {
        if (fqtypeName.startsWith("http://")) {
            type = fqtypeName.substring(25);
        } else {
            this.type = fqtypeName;
        }
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getType()
    {
        return type;
    }

    public String getHref()
    {
        return href;
    }

}
