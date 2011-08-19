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

package org.xwiki.android.components;

import org.xwiki.android.components.attachments.AttachmentActivity;
import org.xwiki.android.components.commenteditor.CommentEditorActivity;
import org.xwiki.android.components.listnavigator.XWikiListNavigatorActivity;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.components.navigator.XWikiNavigatorActivity;
import org.xwiki.android.components.objecteditor.ObjectEditorActivity;
import org.xwiki.android.components.objectnavigator.ObjectNavigatorActivity;
import org.xwiki.android.components.pageviewer.XWikiPageViewerActivity;
import org.xwiki.android.components.propertyeditor.PropertyEditorActivity;
import org.xwiki.android.components.search.SearchActivity;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.SearchResults;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * See {@link com.xpn.xwiki.plugin.zipexplorer.ZipExplorerPluginAPI} for documentation.
 * 
 * @version $Id: ZipExplorerPlugin.java 29435 2010-06-12 10:17:42Z vmassol $
 */
public class Main extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);

        // startActivity(new Intent(this, SampleListActivity.class));
        // startActivity(new Intent(this, PropertyEditorActivity.class));
        // startActivity(new Intent(this, ObjectEditorActivity.class));
        // startActivity(new Intent(this, ObjectNavigatorActivity.class));
        // startActivity(new Intent(this, LoginActivity.class));
        // startActivityForResult(new Intent(this, LoginActivity.class), 0);

        startActivityForResult(testIntent(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Log.d("Activity", "returned");
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Activity Result", "OK");
            }
        }

    }

    
    private Intent testIntent()
    {
        Intent intent = new Intent(this, ObjectNavigatorActivity.class);

        intent.putExtra(IntentExtra.USERNAME, "Admin");
        intent.putExtra(IntentExtra.PASSWORD, "admin");
        intent.putExtra(IntentExtra.URL, "10.0.2.2:8080");

        intent.putExtra(IntentExtra.WIKI_NAME, "xwiki");
        intent.putExtra(IntentExtra.SPACE_NAME, "Blog");
        intent.putExtra(IntentExtra.PAGE_NAME, "test3");
        
        intent.putExtra(IntentExtra.CLASS_NAME, "Blog.BlogPostClass");
        intent.putExtra(IntentExtra.OBJECT_ID, "0");

        return intent;
    }

}
