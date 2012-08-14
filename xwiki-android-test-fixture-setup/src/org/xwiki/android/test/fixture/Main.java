package org.xwiki.android.test.fixture;

import org.xwiki.test.integration.utils.XWikiExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: sasindap
 * Date: 8/9/12
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
       System.setProperty("xwikiExecutionDirectory","C:\\Users\\admin\\Desktop\\xwiki-enterprise-jetty-10");
       XWikiExecutor executor=new XWikiExecutor(0);
       executor.start();
    }
}
