package org.xwiki.android.components.navigator;


import org.xwiki.android.components.R;

import android.app.ExpandableListActivity;
import android.os.Bundle;

public class SampleListActivity extends ExpandableListActivity
{
    private XwikiExpandListAdapter xwikiExpandListAdapter;
    
    static final String xwikiData[][][][] = 
	{
        { // Wiki 1
          {  // Space 1
            { "Wiki 1", "Space 1" },
            { "Page 1","status 1" },
		    { "Page 2","status 2" },
		    { "Page 3","status 3"}
          },
          {  // Space 2
            { "Wiki 1", "Space 2" },
		    { "Page 4","status 4" },
		    { "Page 5", "status 5" }
          }
        },
        
        { // Wiki 2
          {  // Space 3
            { "Wiki 2", "Space 3" },
            { "Page 6","status 6" }
          },
          {  // Space 4
              { "Wiki 2", "Space 4" },
              { "Page 7","status 7" }
          }
        },
        
        { // Wiki 3
            {  // Space 5
              { "Wiki 3", "Space 3" },
            
            },
            {  // Space 5
              { "Wiki 3", "Space 4" },
              
            }
          }
	};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);
		xwikiExpandListAdapter =
			new XwikiExpandListAdapter(
				this,
                getExpandableListView(),
                xwikiData
			);
		setListAdapter( xwikiExpandListAdapter);
		
		
    }

   
}
