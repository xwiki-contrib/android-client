package org.xwiki.android.rest.rpc;


public interface RestClient
{  
    
    XWikiAPI getBaseAPI();
   
    public PageOperations pageOperations(String wikiName, String spaceName);
    
    public ObjectOperations objectOperations(String wikiName, String spaceName, String pageName);
    
    public CommentOperations commentOperations(String wikiName, String spaceName, String pageName);
    
    public TagOperations tagOperations(String wikiName, String spaceName, String pageName);
    
    public AttachmentOperations attachmentOperations(String wikiName, String spaceName, String pageName);
    
    public HistoryOperations historyOperations(String wikiName, String spaceName, String pageName);
}


