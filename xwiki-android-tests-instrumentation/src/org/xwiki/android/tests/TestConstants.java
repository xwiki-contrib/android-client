package org.xwiki.android.tests;

/**
 * Environment variables for tests.
 * How to setup external text setup.
 */

public class TestConstants {

    public static final String DEFAULT_EXECUTION_DIRECTORY; //see static block to change default.
    public static final int SERVER_INDEX;
    public static final String HOST = "10.0.2.2"; // do not put local host as this is run on AVD. It'll call AVDs nic.
    public static final int PORT = 8080; //actual port is port+server_index
    public static final String SERVER_URL;  //SERVER NAME set in static block.
    public static final String USERNAME = "Admin";
    public static final String PASSWORD = "admin";

    //external test fixture.

    public static final String WIKI_NAME = "xwiki";

    public static final String SPACE_NAME = "Blog";

    public static final String PAGE_NAME = "test2";

    public static final String PAGE_VERSION = "1.1";

    public static final String COMMENT_ID = "0";
    public static final String COMMENT_TEXT = "hi";
    public static final String COMMENT_REPLY_ID = "1";
    public static final int COMMENT_REPLY_REPLY_TO = 0;
    public static final String COMMENT_PAGE_HISTORY_VERSION="5.1";
    public static final String COMMENT_REPLY_TEXT = "huy";


    public static final String LANGUAGE = "fr";
    public static final String TRANSLATION_VERSION="2.1";

    public static final String CLASS_NAME = "Blog.BlogPostClass";
    public static final String OBJECT_NUMBER = "0";
    public static final String OBJECT_PROPERTY_NAME = "content";
    public static final String OBJECT_PROPERTY_VALUE = "test blog content";

    public static final String SEARCH_KEWORD = "test";

    public static final String TAG_WORD = "testTag";

    public static final String ATTACHMENT_NAME = "a.png";
    public static final String ATTACHMENT_DEL_NAME = "del.png";
    public static final String ATTACHMENT_PATH = "./";
    public static final String ATTACHMENT_VERSION = "1.1";
    public static final String ATTACHMENT_PAGE_HISTORY_VERSION = "9.1";

    static {
        SERVER_INDEX = new Integer(System.getProperty("xwikiExecutionIndex", "0"));
        DEFAULT_EXECUTION_DIRECTORY = System.getProperty("xwikiExecutionDirectory", "C:\\Users\\admin\\Desktop\\xwiki-enterprise-jetty");
        SERVER_URL = HOST + ":" + (PORT + SERVER_INDEX);
        if(System.getProperty("xwikiExecutionDirectory"+SERVER_INDEX)==null){
            System.out.println("xwiki execution dir:"+ DEFAULT_EXECUTION_DIRECTORY +" index: "+SERVER_INDEX + " server Url: "+SERVER_URL);
        }else{
            System.out.println("xwiki execution dir:"+ System.getProperty("xwikiExecutionDirectory"+SERVER_INDEX) +" index: "+SERVER_INDEX + " server Url: "+SERVER_URL);
        }
        System.setProperty("xwikiExecutionDirectory", DEFAULT_EXECUTION_DIRECTORY);//set it up to default val above, if not set by pom.else rewrite.
    }

}
