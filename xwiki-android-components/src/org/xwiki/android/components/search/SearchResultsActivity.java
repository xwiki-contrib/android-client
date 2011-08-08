package org.xwiki.android.components.search;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsActivity extends ListActivity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_KEYWORD = IntentExtra.KEYWORD;

    public static final String INTENT_EXTRA_GET_PAGE_NAME = IntentExtra.PAGE_NAME;

    private String url, wikiname, spacename, username, password, keyword;

    private boolean isSecured;

    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        wikiname = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spacename = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        keyword = getIntent().getExtras().getString(INTENT_EXTRA_PUT_KEYWORD);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        loadSearchResults();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.searchresults_list_item, data));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView< ? > arg0, View view, int arg2, long arg3)
            {
                completeProcess(((TextView) view).getText().toString());
            }
        });
    }

    private void loadSearchResults()
    {

        Requests request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        SearchResults sr = request.requestSearch(wikiname, spacename, keyword);

        data = new String[sr.getSearchResults().size()];

        for (int i = 0; i < data.length; i++) {
            data[i] = sr.getSearchResults().get(i).getPageName();
        }
    }

    private void completeProcess(String pageName)
    {

        getIntent().putExtra(INTENT_EXTRA_GET_PAGE_NAME, pageName);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
