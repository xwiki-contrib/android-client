package org.xwiki.android.test.utils.xmlrpc;
/**
 *
 * codes are equivelent to http response codes.
 * 
 */
public class RestException extends Exception
{

    int code;
//    public RestException()
//    {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//    public RestException(String detailMessage, Throwable throwable)
//    {
//        super(detailMessage, throwable);
//        // TODO Auto-generated constructor stub
//    }
//
//    public RestException(String detailMessage)
//    {
//        super(detailMessage);
//        // TODO Auto-generated constructor stub
//    }
//
//    public RestException(Throwable throwable)
//    {
//        super(throwable);
//        // TODO Auto-generated constructor stub
//    }
    
    public RestException(int code)
    {
        super();
        this.code=code;
    }

    public RestException(int code, String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);  
        this.code=code;
    }

    public RestException(int code, String detailMessage)
    {
        super(detailMessage); 
        this.code=code;
    }

    public RestException(int code, Throwable throwable)
    {
        super(throwable);
        this.code=code;
    }

   
    public int getCode()
    {
        return code;
    }

    @Override
    public String getMessage() {
        return "code: "+code;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
