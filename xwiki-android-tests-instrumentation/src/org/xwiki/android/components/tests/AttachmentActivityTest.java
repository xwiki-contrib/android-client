/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
    // public void testGetActivty() throws Throwable
    // {
    // Intent intent=new Intent();
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_WIKI_NAME, TestResources.WIKI_NAME);
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_SPACE_NAME, TestResources.SPACE_NAME);
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PAGE_NAME, TestResources.PAGE_NAME);
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_URL, TestResources.URL);
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_USERNAME, TestResources.USERNAME);
    // intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PASSWORD, TestResources.PASSWORD);
    //
    //
    // launchActivityWithIntent("org.xwiki.android.tests", AttachmentActivity.class, intent);
    //
    // // assertNotNull(testActivity);
    //
    // }

}
