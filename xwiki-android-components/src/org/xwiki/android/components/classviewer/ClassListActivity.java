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

package org.xwiki.android.components.classviewer;

import java.util.List;
import java.util.Vector;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.components.commenteditor.CommentEditorActivity;
import org.xwiki.android.resources.Class;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.rest.Requests;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * UI Component which shows the list of classes of the wiki or in the page.F
 */
public class ClassListActivity extends ListActivity
{
    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    private String wikiName, spaceName, pageName, username, password, url;

    private boolean isSecured = false, isWiki = true;

    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME) != null) {
            spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
            pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
            isWiki = false;
        }

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        initDataArray();

    }

    private void initDataArray()
    {
        Requests request = new Requests(url);
        if (isSecured) {
            request.setAuthentication(username, password);
        }

        if (isWiki) {
            Classes classes = request.requestWikiClasses(wikiName);
            List<Class> classList = classes.getClazzs();
            data = new String[classList.size()];

            for (int i = 0; i < data.length; i++) {
                data[i] = classList.get(i).getName();
            }
        } else {
            Objects pageObjects = request.requestAllObjects(wikiName, spaceName, pageName);

            Vector<String> temp_classNames = new Vector<String>();

            for (int i = 0; i < pageObjects.getObjectSummaries().size(); i++) {
                String currentClassName = pageObjects.getObjectSummaries().get(i).getClassName();

                if (!temp_classNames.contains(currentClassName)) {
                    temp_classNames.add(currentClassName);
                }
            }

            data = new String[temp_classNames.size()];

            for (int i = 0; i < data.length; i++) {
                data[i] = temp_classNames.get(i);
            }
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.class_list_item, data));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener()
        {

            public void onItemClick(AdapterView< ? > arg0, View view, int arg2, long arg3)
            {

                String selectedClassName = ((TextView) view).getText().toString();

                Intent intentClassView = new Intent(getApplicationContext(), ClassViewerActivity.class);

                intentClassView.putExtra(ClassViewerActivity.INTENT_EXTRA_PUT_CLASS_NAME, selectedClassName);

                if (isSecured) {
                    intentClassView.putExtra(ClassViewerActivity.INTENT_EXTRA_PUT_USERNAME, username);
                    intentClassView.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
                }

                intentClassView.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_URL, url);
                intentClassView.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);

                startActivity(intentClassView);

                Log.d("class selected", selectedClassName);
            }
        });
    }
}
