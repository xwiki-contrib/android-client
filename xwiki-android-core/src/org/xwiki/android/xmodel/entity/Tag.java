package org.xwiki.android.xmodel.entity;

public class Tag extends XWikiResource
{
   
    private static final long serialVersionUID = 3416603502937027872L;
   
    private String name;
//    private XTag xobj;
//    private int index;

    public Tag(String name)
    {
        this.name=name;
    }
    

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public String toString()
    {        
        return name;
    }

}
