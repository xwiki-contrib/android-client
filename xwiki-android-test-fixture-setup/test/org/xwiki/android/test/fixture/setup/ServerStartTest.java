package org.xwiki.android.test.fixture.setup;


import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xwiki.android.test.fixture.setup.env.TestEnv;
import org.xwiki.test.integration.utils.XWikiExecutor;

public class ServerStartTest extends Assert {
    XWikiExecutor exec;

    boolean failed;
   @Before
   public void setup(){

       exec=new XWikiExecutor(TestEnv.SERVER_INDEX);
       try {
           exec.start();
       } catch (Exception e) {
           throw new AssertionFailedError(e.getMessage());
       }
   }

    @Test
    public void setupProperly(){
        try {
            exec.isXWikiStarted(TestEnv.SCHEME + TestEnv.SERVER_URL, 10000);
        } catch (Exception e) {
           // failed=true;
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @After
    public void cleanup() throws Throwable{
        if(failed){
            exec.stop();//error if cant stop
        }
    }

}
