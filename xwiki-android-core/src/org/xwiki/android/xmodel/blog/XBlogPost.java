package org.xwiki.android.xmodel.blog;

import java.util.Date;

import org.xwiki.android.xmodel.xobjects.XBooleanProperty;
import org.xwiki.android.xmodel.xobjects.XDBTreeListProperty;
import org.xwiki.android.xmodel.xobjects.XDateProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;
import org.xwiki.android.xmodel.xobjects.XStringProperty;
import org.xwiki.android.xmodel.xobjects.XTextAreaProperty;

/**
 * @author sasinda corresponds to XWiki Enterprise BlogPostClass. Naming convention is BlogPostClass --> XBlogPost (add
 *         prefix 'X', drop suffix 'Class')
 */
public class XBlogPost extends XSimpleObject
{

    public XBlogPost()
    {
       super("Blog.BlogPostClass");// set Type.
    }
    
    public static String getType(){
        return "Blog.BlogPostClass";
    }

    public String getCategory()
    {
        XDBTreeListProperty prop = (XDBTreeListProperty) fields.get("category");
        if (prop != null) {
            return prop.getValue().toString();
        }
        return null;
    }

    public void setCategory(String category)
    {
        XDBTreeListProperty prop = (XDBTreeListProperty) fields.get("category");
        if (prop == null) {
            prop = new XDBTreeListProperty();
            setProperty("category", prop);
        }
        prop.getValue().clear();
        prop.getValue().add(category);
    }

    public String getContent()
    {
        XTextAreaProperty prop = (XTextAreaProperty) fields.get("content");
        if (prop != null) {
            return prop.getValue();
        }
        return null;
    }

    public void setContent(String content)
    {
        XTextAreaProperty prop = (XTextAreaProperty) fields.get("content");
        if (prop == null) {
            prop = new XTextAreaProperty();
            setProperty("content", prop);
        }
        prop.setValue(content);
    }

    public String getExtract()
    {
        XTextAreaProperty prop = (XTextAreaProperty) fields.get("extract");
        if (prop != null) {
            return prop.getValue();
        }
        return null;
    }

    public void setExtract(String extract)
    {
        XTextAreaProperty prop = (XTextAreaProperty) fields.get("extract");
        if (prop == null) {
            prop = new XTextAreaProperty();
            setProperty("extract", prop);
        }
        prop.setValue(extract);
    }

    /**
     * @return value of the "hidden" property. Or 'false' if the property is not yet set. Use getpHidden() to check
     *         whether the property is set.
     */
    public boolean isHidden()
    {
        XBooleanProperty prop = (XBooleanProperty) fields.get("hidden");
        if (prop != null) {
            return prop.getValue();
        }
        return false;
    }

    public void setHidden(boolean hidden)
    {
        XBooleanProperty prop = (XBooleanProperty) fields.get("hidden");
        if (prop == null) {
            prop = new XBooleanProperty();
            setProperty("hidden", prop);
        }
        prop.setValue(hidden);
    }

    public Date getPublishDate()
    {
        XDateProperty prop = (XDateProperty) fields.get("publishDate");
        if (prop != null) {
            return prop.getValue();
        }
        return null;
    }

    public void setPublishDate(Date publishDate)
    {
        XDateProperty prop = (XDateProperty) fields.get("publishDate");
        if (prop == null) {
            prop = new XDateProperty();
            setProperty("publishDate", prop);
        }
        prop.setValue(publishDate);
    }

    /**
     * @return value of the "published" property. Or 'false' even if the property is not set. Use getpPublished() to
     *         check whether the property is set.
     */
    public boolean isPublished()
    {
        XBooleanProperty prop = (XBooleanProperty) fields.get("published");
        if (prop != null) {
            return prop.getValue();
        }
        return false;
    }

    public void setPublished(boolean published)
    {
        XBooleanProperty prop = (XBooleanProperty) fields.get("published");
        if (prop == null) {
            prop = new XBooleanProperty();
            setProperty("published", prop);
        }
        prop.setValue(published);
    }

    public String getTitle()
    {
        XStringProperty prop = (XStringProperty) fields.get("title");
        if (prop != null) {
            return prop.getValue();
        }
        return null;
    }

    public void setTitle(String title)
    {
        XStringProperty prop = (XStringProperty) fields.get("title");
        if (prop == null) {
            prop = new XStringProperty();
            setProperty("title", prop);
        }
        prop.setValue(title);
    }

    public XDBTreeListProperty getPCategory()
    {
        return (XDBTreeListProperty) fields.get("category");
    }

    public void setpCategory(XDBTreeListProperty category)
    {
        setProperty("category", category);
    }

    public XTextAreaProperty getpContent()
    {
        return (XTextAreaProperty) fields.get("content");
    }

    public void setpContent(XTextAreaProperty content)
    {
        setProperty("content", content);
    }

    public XTextAreaProperty getpExtract()
    {
        return (XTextAreaProperty) fields.get("extract");
    }

    public void setpExtract(XTextAreaProperty extract)
    {
        setProperty("extract", extract);
    }

    public XBooleanProperty getpHidden()
    {
        return (XBooleanProperty) fields.get("hidden");
    }

    public void setpHidden(XBooleanProperty hidden)
    {
        setProperty("hidden", hidden);
    }

    public XDateProperty getpPublishDate()
    {
        return (XDateProperty) fields.get("publishDate");
    }

    public void setpPublishDate(XDateProperty publishDate)
    {
        setProperty("publishDate", publishDate);
    }

    public XBooleanProperty getpPublished()
    {
        return (XBooleanProperty) fields.get("published");
    }

    public void setpPublished(XBooleanProperty published)
    {
        setProperty("published", published);
    }

    public XStringProperty getpTitle()
    {
        return (XStringProperty) fields.get("title");
    }

    public void setpTitle(XStringProperty title)
    {
        setProperty("title", title);
    }

}
