package org.xwiki.android.components.tests;



import org.xwiki.android.components.attachments.AttachmentActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class AttachmentActivityTest extends ActivityInstrumentationTestCase2<AttachmentActivity>
{
    
    private Activity testActivity;

    public AttachmentActivityTest()
    {
        super("org.xwiki.android.tests", AttachmentActivity.class);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        // TODO Auto-generated method stub
        super.setUp();
        testActivity = getActivity();
        
       
        
        
    }
    
//    
//    public void testGetActivty() throws Throwable
//    {
//        Intent intent=new Intent();
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_WIKI_NAME, TestResources.WIKI_NAME);
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_SPACE_NAME, TestResources.SPACE_NAME);
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PAGE_NAME, TestResources.PAGE_NAME);
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_URL, TestResources.URL);
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_USERNAME, TestResources.USERNAME);
//        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PASSWORD, TestResources.PASSWORD);
//        
//        
//        launchActivityWithIntent("org.xwiki.android.tests", AttachmentActivity.class, intent);
//        
//       // assertNotNull(testActivity);
//        
//    }
    
    
}
