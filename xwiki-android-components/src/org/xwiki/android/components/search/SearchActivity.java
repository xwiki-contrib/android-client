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

package org.xwiki.android.components.search;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Wikis;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Search Activity UI Component which provides facility to select wiki/ space and do a search
 * */
public class SearchActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_GET_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_GET_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_GET_PAGE_NAME = IntentExtra.PAGE_NAME;

    private final int REQUEST_CODE_SEARCH_RESULTS = 1000;

    private String url, wikiName, spaceName, pageName, username, password;

    private String[] wikiList;

    private String[] spaceList;

    private boolean isSecured;

    Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        final EditText searchText = (EditText) findViewById(R.id.editText_searchKeyword);

        Button searchButton = (Button) findViewById(R.id.button_searchButton);

        searchButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                Log.d("Search", "Search button clicked");

                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);

                intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_URL, url);
                intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
                intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
                intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_KEYWORD, searchText.getText().toString());

                if (isSecured) {
                    intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_USERNAME, username);
                    intent.putExtra(SearchResultsActivity.INTENT_EXTRA_PUT_PASSWORD, password);
                }

                startActivityForResult(intent, REQUEST_CODE_SEARCH_RESULTS);
            }

        });

        loadWikiList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SEARCH_RESULTS) {
            if (resultCode == Activity.RESULT_OK) {
                pageName = data.getExtras().getString(SearchResultsActivity.INTENT_EXTRA_GET_PAGE_NAME);
                Log.d("Activity Result", "OK pageName=" + pageName);
                getIntent().putExtra(INTENT_EXTRA_GET_WIKI_NAME, wikiName);
                getIntent().putExtra(INTENT_EXTRA_GET_SPACE_NAME, spaceName);
                getIntent().putExtra(INTENT_EXTRA_GET_PAGE_NAME, pageName);
                setResult(Activity.RESULT_OK, getIntent());
                finish();

            }
        }
    }

    private void loadWikiList()
    {

        Wikis ws = request.requestWikis();

        wikiList = new String[ws.getWikis().size()];

        for (int i = 0; i < wikiList.length; i++) {
            wikiList[i] = ws.getWikis().get(i).getName();
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner_wikilist);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, wikiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView< ? > parent, View view, int pos, long id)
            {
                wikiName = parent.getItemAtPosition(pos).toString();
                loadSpaceList(wikiName);
            }

            @Override
            public void onNothingSelected(AdapterView< ? > arg0)
            {

            }

        });
    }

    private void loadSpaceList(String wikiName)
    {
        Log.d("loadspacelist", "wikiName=" + wikiName);

        Spaces spaces = request.requestSpaces(wikiName);

        spaceList = new String[spaces.getSpaces().size()];

        for (int i = 0; i < spaceList.length; i++) {
            spaceList[i] = spaces.getSpaces().get(i).getName();
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner_spacelist);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spaceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView< ? > parent, View view, int pos, long id)
            {
                spaceName = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView< ? > arg0)
            {

            }

        });
    }

}
