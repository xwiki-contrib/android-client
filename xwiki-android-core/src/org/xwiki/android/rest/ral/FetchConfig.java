package org.xwiki.android.rest.ral;


/**
 * Configure how much things you need to fetch.
 * Note: Some fetchX() methods like fetchObjectSummaries(ClassName, offset, len) act as hints to fetch only the specified summaries.
 * But the DocumentRao may fetch all object summaries.
 * @author xwiki gsoc 2012
 *
 */
public class FetchConfig
{
  //parts of a doc.
    public static int PAGE = 1;
    public static int PAGE_SUMMARY=512;
    /**
     * all Objects except comment and tag objects.
     */
    public static int OBJECTS = 2;
    public static int OBJECT_SUMMARIES=1024;
    public static int COMMENTS = 4;
    public static int TAGS = 8;
    public static int ATTACHMENT_SUMMARIES = 16;
    public static int COMMENT_OBJECTS=32;
    public static int TAG_OBJECT=64;
    public static int HISTORY=128;
    public static int TRANSLATIONS=256;    
    public static int ALL = 2147483647;
    
    
    int parts;
    
    public FetchConfig(){        
    }
    
    /**
     * quick constructor to add parts. parts=PAGE || OBJECTS mean the retreived document will be filled with the objects + the page data.
     * @param parts
     */
    public FetchConfig(int parts)
    {
       this.parts=parts;   
    }
    
    
    public FetchConfig fetchObjects(){
        return this;
    }
    public FetchConfig fetchObjects(String className)
    {
        return null;
    }
    
    public FetchConfig fetchObjects(String className, String... properties)
    {
        return null;
    }
    public FetchConfig fetchObjects(String className, int offset, int len)
    {
        return this;
    }
    
    public FetchConfig fetchObjects(String className, int offset, int len,String... properties)
    {
        return this;
    }
    
    public FetchConfig fetchObjectSummaries()
    {
        return this;
    } 
    
    public FetchConfig fetchObjectSummaries(String className)
    {
        return this;
    }
    
    public FetchConfig fetchObjectSummaries(String className, int offset, int len)
    {
        return this;
    }
    
    public FetchConfig fetchComments()
    {
        return null;
    }
    public FetchConfig fetchComments(int offset, int len)
    {
        return null;
    }
    public FetchConfig fetchAttachmentSummaries()
    {
        return null;
    }
    public FetchConfig fetchAttachmentSummaries(int offset, int len)
    {
        return null;
    }
    
    public FetchConfig fetchAttachments(){
        return this;
    }
    public FetchConfig fetchAttachment(String name){
        return this;
    }

}
