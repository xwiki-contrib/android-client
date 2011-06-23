package org.xwiki.android.components;

import org.xwiki.android.components.navigator.SampleListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        startActivity(new Intent(this, SampleListActivity.class));
    }
}