package org.xwiki.android.components.navigator;

import org.xwiki.android.components.R;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;

public class XWikiNavigatorActivity extends ExpandableListActivity
{
    private XWikiExpandListAdapter xwikiExpandListAdapter;
    
    private Handler handler;
    
    private String wikiName, spaceName, pageName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.xwikinavigator);
        // xwikiExpandListAdapter =
        // new XWikiExpandListAdapter(this, getExpandableListView(),"www.xwiki.org");

        if (getIntent().getExtras().getString("username") != null
            && getIntent().getExtras().getString("password") != null) {
            xwikiExpandListAdapter =
                new XWikiExpandListAdapter(this, getExpandableListView(), getIntent().getExtras().getString("url"),
                    getIntent().getExtras().getString("username"), getIntent().getExtras().getString("password"));
            
        }
        
       

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                
                if(msg.arg1 == 0){
                    if(xwikiExpandListAdapter.getIsSelected()){
                        wikiName = xwikiExpandListAdapter.wikiName;
                        spaceName = xwikiExpandListAdapter.spaceName;
                        pageName = xwikiExpandListAdapter.pageName;
                        
                        getIntent().putExtra("wikiname",wikiName);
                        getIntent().putExtra("spacename", spaceName);
                        getIntent().putExtra("pagename", pageName);
                        setResult(Activity.RESULT_OK, getIntent());
                        finish();
                    }
                }
            }
            
        };
        
        xwikiExpandListAdapter.setHandler(handler);
        
        setListAdapter(xwikiExpandListAdapter);
    }

}
