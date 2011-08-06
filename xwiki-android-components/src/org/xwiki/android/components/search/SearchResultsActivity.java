package org.xwiki.android.components.search;

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
    private String url, wikiname, spacename, username, password, keyword;

    private boolean isSecured;
    
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        wikiname = getIntent().getExtras().getString("wikiname");
        spacename = getIntent().getExtras().getString("spacename");
        url = getIntent().getExtras().getString("url");
        keyword = getIntent().getExtras().getString("keyword");

        if (getIntent().getExtras().getString("password") != null
            && getIntent().getExtras().getString("username") != null) {
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");
            isSecured = true;
        }

        loadSearchResults();
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.searchresults_list_item, data));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener()
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
        
        for(int i=0; i < data.length; i++){
            data[i] = sr.getSearchResults().get(i).getPageName();
        }
        
       

    }
    
    private void completeProcess(String pageName){
                
        getIntent().putExtra("pagename", pageName);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
