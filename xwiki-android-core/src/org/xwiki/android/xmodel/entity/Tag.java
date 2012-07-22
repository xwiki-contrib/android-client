package org.xwiki.android.xmodel.entity;

import java.util.List;

import org.xwiki.android.ral.reference.Link;
import org.xwiki.android.xmodel.xobjects.XTag;

import com.j256.ormlite.table.DatabaseTable;

public class Tag
{
    private List<Link> links;
    private String name;
//    private XTag xobj;
//    private int index;

    public Tag(String name)
    {
        this.name=name;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public void addLink(Link link)
    {
        links.add(link);
    }

    public void removeLink(int index)
    {
        links.remove(index);
    }

    public void clearLinks()
    {
        links.clear();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
