package org.xwiki.android.test.fixture;


import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xwiki.android.test.fixture.teardown.env.TestEnv;
import org.xwiki.test.integration.XWikiExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: sasindap
 * Date: 8/10/12
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */

public class ServerShutdownTest extends Assert {
    Logger logger= LoggerFactory.getLogger(ServerShutdownTest.class.getSimpleName());

    XWikiExecutor exec;
    boolean failed;

    @Before
    public void shutdownServer() {


        exec = new XWikiExecutor(new Integer(System.getProperty("xwikiExecutionIndex")));

        try {
            exec.stop();
        } catch (Exception e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @Test(expected = Exception.class)
    public void shutdownProperly() throws Exception {
        exec.isXWikiStarted(TestEnv.SERVER_URL, 1000);
    }

    @After
    public void cleanup() throws Throwable {
        logger.info("server shutdown. You can check out the pages , objects , etc created by the test cases by manually starting the server again." +
                "\n please note that running tests on xwiki-androidt-test-fixtures-setup will clean up all above.");
    }

}
