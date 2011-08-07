package org.xwiki.android.components.listnavigator;

import org.xwiki.android.components.R;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Wikis;
import org.xwiki.android.rest.HttpConnector;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class XWikiListNavigatorActivity extends Activity
{
    private String url, wikiName, spaceName, pageName, username, password, cachedWikiName, cachedSpaceName,
        cachedPageName;

    private Requests request;

    // state 0 = wiki 1=space 2= pages
    private int state;

    private boolean isSecured;

    private String[] data;

    private String[] wikiList, spaceList, pageList;

    private Button backButton;

    private TextView titleTextView;

    ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xwikilistnavigator);

        titleTextView = (TextView) findViewById(R.id.textView_listnavigator_title);

        state = 0;

        url = getIntent().getExtras().getString("url");

        if (getIntent().getExtras().getString("password") != null
            && getIntent().getExtras().getString("username") != null) {
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");
            isSecured = true;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        myProgressDialog = new ProgressDialog(XWikiListNavigatorActivity.this);
        myProgressDialog.setTitle("Loading");
        myProgressDialog.setMessage("Please wait while connecting...");

        backButton = (Button) findViewById(R.id.button_listnavigator_back);

        backButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                state--;
                addItemsToList(state, null);
            }
        });

        wikiName = "";
        spaceName = "";
        pageName = "";

        addItemsToList(state, null);

    }

    private void loadWikis()
    {
        if (wikiList == null) {
            Wikis wikis = request.requestWikis();

            data = new String[wikis.getWikis().size()];

            for (int i = 0; i < data.length; i++) {
                data[i] = wikis.getWikis().get(i).getName();
            }

            wikiList = data;
        } else {
            data = wikiList;
            Log.d("Optimize", "optimized wikiList");
        }

    }

    private void loadSpaces()
    {
        if (wikiName.equals(cachedWikiName)) {
            data = spaceList;
            Log.d("Optimize", "optimized spaceList");
        } else {
            Spaces spaces = request.requestSpaces(wikiName);

            data = new String[spaces.getSpaces().size()];

            for (int i = 0; i < data.length; i++) {
                data[i] = spaces.getSpaces().get(i).getName();
            }

            spaceList = data;
            cachedWikiName = wikiName;

        }

    }

    private void loadPages()
    {
        if (wikiName.equals(cachedWikiName) && spaceName.equals(cachedSpaceName)) {
            data = pageList;
            Log.d("Optimize", "optimized pageList");
        } else {
            Pages pages = request.requestAllPages(wikiName, spaceName);

            data = new String[pages.getPageSummaries().size()];

            for (int i = 0; i < data.length; i++) {
                data[i] = pages.getPageSummaries().get(i).getName();
            }

            pageList = data;
            cachedWikiName = wikiName;
            cachedSpaceName = spaceName;
        }

    }

    private void addItemsToList(final int stateID, final String selectedItem)
    {
        myProgressDialog.show();

        if (stateID == 0) {
            titleTextView.setText("");
            loadWikis();

        } else if (stateID == 1) {

            if (selectedItem != null && !wikiName.equals(selectedItem)) {
                wikiName = selectedItem;
                spaceName = "";
                pageName = "";
            }

            titleTextView.setText(wikiName);
            loadSpaces();

        } else if (stateID == 2) {
            if (selectedItem != null && !spaceName.equals(selectedItem)) {
                spaceName = selectedItem;
                pageName = "";
            }
            titleTextView.setText(wikiName + " > " + spaceName);
            loadPages();
        } else {
            pageName = selectedItem;
            completeProcess();
        }

        myProgressDialog.dismiss();

        setUIElements();
        Log.d("Variables", "wikiName,spaceName,pagename=" + wikiName + "," + spaceName + "," + pageName);
    }

    private void setUIElements()
    {
        ArrayAdapter<String> adapt =
            new ArrayAdapter<String>(getApplicationContext(), R.layout.xwikilistnavigator_list_item, data);

        ListView lv = (ListView) findViewById(R.id.listView_xwikilistnavigator);
        lv.setAdapter(adapt);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView< ? > arg0, View view, int arg2, long arg3)
            {
                state++;
                addItemsToList(state, ((TextView) view).getText().toString());
            }
        });
    }

    private void completeProcess()
    {
        getIntent().putExtra("wikiname", wikiName);
        getIntent().putExtra("spacename", spaceName);
        getIntent().putExtra("pagename", pageName);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
