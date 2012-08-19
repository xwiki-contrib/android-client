package org.xwiki.android.data.fileStore;

public class FileStoreException extends RuntimeException
{

    public FileStoreException()
    {
        super();
    }

    public FileStoreException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
    }

    public FileStoreException(String detailMessage)
    {
        super(detailMessage);
    }

    

    public static class DuplicateKey extends FileStoreException
    {

        public DuplicateKey()
        {
            super();
        }

        public DuplicateKey(String detailMessage, Throwable throwable)
        {
            super(detailMessage, throwable);
        }

        public DuplicateKey(String detailMessage)
        {
            super(detailMessage);
        }
    }
    
    public static class UnsupportedOperation extends FileStoreException{

        public UnsupportedOperation()
        {
            super();            
        }

        public UnsupportedOperation(String detailMessage, Throwable throwable)
        {
            super(detailMessage, throwable);           
        }

        public UnsupportedOperation(String detailMessage)
        {
            super(detailMessage);            
        }
        
        
    }

}
