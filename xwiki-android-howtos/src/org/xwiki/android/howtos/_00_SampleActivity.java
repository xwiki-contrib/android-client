package org.xwiki.android.howtos;

import org.xwiki.android.howtos.R;

import android.app.Activity;
import android.os.Bundle;

public class _00_SampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}