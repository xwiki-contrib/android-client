package org.xwiki.android.xmodel.entity;

import java.io.File;
import java.util.Date;

import java.net.URL;

public class Attachment extends Resource
{
    // remote
    private String id;
    private String name;
    private int size;
    private String version;
    private String PageId;
    private String pageVersion;
    private String mimeType;
    private String author;
    private Date date;
    private URL xwikiRelativeUrl;
    private URL xwikiAbsoluteUrl;
    // local
    private File file;
    
    public Attachment()
    {
        
    }
    

    public Attachment(String name, File file)
    {
        super();
        this.name = name;
        this.file = file;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getPageId()
    {
        return PageId;
    }

    public void setPageId(String pageId)
    {
        PageId = pageId;
    }

    public String getPageVersion()
    {
        return pageVersion;
    }

    public void setPageVersion(String pageVersion)
    {
        this.pageVersion = pageVersion;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public URL getXwikiRelativeUrl()
    {
        return xwikiRelativeUrl;
    }

    public void setXwikiRelativeUrl(URL xwikiRelativeUrl)
    {
        this.xwikiRelativeUrl = xwikiRelativeUrl;
    }

    public URL getXwikiAbsoluteUrl()
    {
        return xwikiAbsoluteUrl;
    }

    public void setXwikiAbsoluteUrl(URL xwikiAbsoluteUrl)
    {
        this.xwikiAbsoluteUrl = xwikiAbsoluteUrl;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Attachment))
            return false;
        return ((Attachment) o).getName().equals(getName());
    }

}
