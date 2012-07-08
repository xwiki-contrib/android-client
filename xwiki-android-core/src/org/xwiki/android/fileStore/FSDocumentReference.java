package org.xwiki.android.fileStore;

import java.io.File;

import org.xwiki.android.ral.reference.DocumentReference;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "FS_DOC_REF")
public class FSDocumentReference extends DocumentReference
{
    @DatabaseField(generatedId=true)
    private int _id;// local id for persistence. "_" to comply with android adapters. No need to set. Set by ORMlite

    /**
     * file:// sections
     */
    protected File file;

    @DatabaseField(unique=true)
    protected String filePath;

    @DatabaseField
    protected String tag;

    public void setFile(File f)
    {
        this.file = f;
        filePath = f.getAbsolutePath();
    }

    public File getFile()
    {
        if(file==null){
            file=new File(filePath);
        }            
        return this.file;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public void copyfrom(DocumentReference docref)
    {
        serverName = docref.getServerName();
        host = docref.getHost();
        port = docref.getPort();
        wikiName = docref.getWikiName();
        spaceName = docref.getSpaceName();
        pageName = docref.getPageName();
    }

}
