package org.xwiki.android.components.search;

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

public class SearchActivity extends Activity
{
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

        url = getIntent().getExtras().getString("url");
        
        if (getIntent().getExtras().getString("password") != null
            && getIntent().getExtras().getString("username") != null) {
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");
            isSecured = true;
        }
        
        request = new Requests(url);
        
        if(isSecured){
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

                intent.putExtra("url", url);
                intent.putExtra("wikiname", wikiName);
                intent.putExtra("spacename", spaceName);
                intent.putExtra("keyword", searchText.getText().toString());

                if (isSecured) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                }

                startActivityForResult(intent, REQUEST_CODE_SEARCH_RESULTS);
                // startActivity(intent);
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
                pageName = data.getExtras().getString("pagename");
                Log.d("Activity Result", "OK pageName=" + pageName);

            }
        }
    }

    private void loadWikiList()
    {
        
        Wikis ws= request.requestWikis();

        wikiList = new String[ws.getWikis().size()];
        
        for(int i=0; i< wikiList.length ; i ++){
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

        Spaces spaces= request.requestSpaces(wikiName);

        spaceList = new String[spaces.getSpaces().size()];
        
        for(int i=0; i< spaceList.length ; i ++){
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
