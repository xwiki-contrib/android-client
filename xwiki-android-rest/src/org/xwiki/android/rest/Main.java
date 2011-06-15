package org.xwiki.android.rest;

import org.xwiki.rest.model.jaxb.Page;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main extends Activity
{

    // IP of the development machine in Android emulator 10.0.2.2
    // These variables are uses for testing
    String domain = "10.0.2.2";

    String searchKeyword = "test";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void searchButtonClick(View v)
    {

        TextView tv_result = (TextView) findViewById(R.id.textView1);
        TextView et_domain = (TextView) findViewById(R.id.editText_domain);
        TextView et_searchText = (TextView) findViewById(R.id.editText_SearchText);

        domain = et_domain.getText().toString();
        searchKeyword = et_searchText.getText().toString();

        Log.d("Info", "domain=" + domain + " text=" + searchKeyword);
        Requests request = new Requests(domain);

        Page x;
        
        
        
        
        // Test wiki search implementation
        // String result = request.requestSearch("xwiki",searchKeyword);
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Search Results\n" + result);

        // Test space search implementation
        // String result = request.requestSearch("xwiki","Blog",searchKeyword);
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Search Results\n" + result);

        // Test wiki implementation
        // String result = request.requestWiki();
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Wiki Results\n" + result);

        // Test space implementation
        // String result = request.requestSpaces("xwiki");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Space Results\n" + result);

        // Test all pages implementation
        // String result = request.requestAllPages("xwiki", "Blog");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("All Pages Results\n" + result);

        // Test one page implementation
        // String result = request.requestPage("xwiki", "Blog", "This is test 2");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page delete implementation
        // String result = request.deletePage("xwiki", "Blog", "This is test");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page history implementation
        // String result = request.requestPageHistory("xwiki", "Blog", "This is test 2");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page history with version implementation
        // String result = request.requestPageHistory("xwiki", "Blog", "This is test 2","2.1");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page children implementation
        // String result = request.requestPageChildren("xwiki", "Blog", "This is test 2");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page tags implementation
        // String result = request.requestPageTags("xwiki", "Blog", "This is test 2");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test wiki tags implementation
        // String result = request.requestWikiTags("xwiki");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

    }
}
