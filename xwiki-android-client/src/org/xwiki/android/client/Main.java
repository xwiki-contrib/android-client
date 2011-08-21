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

package org.xwiki.android.client;

import org.xwiki.android.components.listnavigator.XWikiListNavigatorActivity;
import org.xwiki.android.components.login.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity
{

    private final int REQUEST_CODE_LOGINACTIVITY = 100;

    private final int REQUEST_CODE_XWIKI_NAVIGATOR = 101;

    private final int REQUEST_CODE_XWIKI_PAGEVIEWER = 102;

    private final int REQUEST_CODE_OBJECTNAVIGATOR = 110;

    private String username;

    private String password;

    private String url;

    private String wikiName;

    private String spaceName;

    private String pageName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_CODE_LOGINACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGINACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Activity Result", "OK");
                username = data.getExtras().getString(LoginActivity.INTENT_EXTRA_GET_USERNAME);
                password = data.getExtras().getString(LoginActivity.INTENT_EXTRA_GET_PASSWORD);
                url = data.getExtras().getString(LoginActivity.INTENT_EXTRA_GET_URL);
                Log.d("data", "username=" + username + " password=" + password + " url=" + url);
                startXWikiNavigator();
            } else {
                finish();
            }
        } else if (requestCode == REQUEST_CODE_XWIKI_NAVIGATOR) {
            if (resultCode == Activity.RESULT_OK) {
                wikiName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_WIKI_NAME);
                spaceName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_SPACE_NAME);
                pageName = data.getExtras().getString(XWikiListNavigatorActivity.INTENT_EXTRA_GET_PAGE_NAME);

                Intent intent = new Intent(this, PageViewActivity.class);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_USERNAME, username);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_PASSWORD, password);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_URL, url);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
                intent.putExtra(PageViewActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);

                startActivityForResult(intent, REQUEST_CODE_XWIKI_PAGEVIEWER);
            } else {
                startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
                    REQUEST_CODE_LOGINACTIVITY);
            }

        } else if (requestCode == REQUEST_CODE_XWIKI_PAGEVIEWER) {
            startXWikiNavigator();
        } else if (requestCode == REQUEST_CODE_OBJECTNAVIGATOR) {

        }
    }

    private void startXWikiNavigator()
    {
        // Tree based XWiki Navigator
        // Intent intent = new Intent(this, XWikiNavigatorActivity.class);
        // intent.putExtra("username", username);
        // intent.putExtra("password", password);
        // intent.putExtra("url", url);
        
        // List based XWiki Navigator
        Intent intent = new Intent(this, XWikiListNavigatorActivity.class);
        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_USERNAME, username);
        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        intent.putExtra(XWikiListNavigatorActivity.INTENT_EXTRA_PUT_URL, url);

        startActivityForResult(intent, REQUEST_CODE_XWIKI_NAVIGATOR);
    }
}
