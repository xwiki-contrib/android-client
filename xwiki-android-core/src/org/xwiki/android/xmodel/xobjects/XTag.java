package org.xwiki.android.xmodel.xobjects;

import java.util.List;

import android.text.StaticLayout;

public class XTag extends XSimpleObject
{
    
    public XTag()
    {
        super("XWiki.TagClass");
    }
    
    
    public void addTag(String tag)
    {
        XStaticListProperty<String> prop = (XStaticListProperty<String>) fields.get("category");
        if (prop == null) {
            prop=new XStaticListProperty<String>();
            fields.put("tags", prop);
        }   
        prop.getValue().add(tag);
    }

    public String getTag(int index)
    {
        XStaticListProperty<String> prop = (XStaticListProperty<String>) fields.get("category");
        if (prop != null) {
            return prop.getValue().get(index);
        }
        return null;
    }

    public List<String> getTags()
    {
        XStaticListProperty<String> prop = (XStaticListProperty<String>) fields.get("tags");
        if (prop != null) {
            return prop.getValue();
        }
        return null;
    }
    
    
    public void setpTags(XStaticListProperty<String> prop){
        setProperty("tags",prop);
    }
    public XStaticListProperty<String> getpTags(){
        return (XStaticListProperty<String>) fields.get("tags");
    }
    
}
