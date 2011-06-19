package org.xwiki.android.rest;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Wikis;

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

        // Test wiki search implementation
        // SearchResults sr = request.requestSearch("xwiki",searchKeyword);
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Search Results\n" + result);

        // Test space search implementation
        // SearchResults sr = request.requestSearch("xwiki","Blog",searchKeyword);
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Search Results\n" + result);

        // Test wiki implementation
        // Wikis w= request.requestWikis();
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Wiki Results\n" + result);

        // Test space implementation
        // Spaces s = request.requestSpaces("xwiki");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Space Results\n" + result);

        // Test all pages implementation
        // Pages ps = request.requestAllPages("xwiki", "Blog");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("All Pages Results\n" + result);

        // Test one page implementation
         Page p = request.requestPage("xwiki", "Blog", "This is test 2");
         Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page delete implementation
        // String result = request.deletePage("xwiki", "Blog", "This is test");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page history implementation
        // History h = request.requestPageHistory("xwiki", "Blog", "This is test 2");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test one page history with version implementation
        // String result = request.requestPageHistory("xwiki", "Blog", "This is test 2","2.1");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page children implementation
        // Pages ps=request.requestPageChildren("xwiki", "Blog", "This is test 2");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page tags implementation
        // Tags ts= request.requestPageTags("xwiki", "Blog", "This is test 2");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test wiki tags implementation
        // Tags ts = request.requestWikiTags("xwiki");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page comments implementation
        // Comments cs = request.requestPageComments("xwiki", "Blog", "This is test 2");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page comments implementation with id
        // Comment c = request.requestPageComments("xwiki", "Blog", "This is test 2","0");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test page comments implementation in history
        // Comments cs= request.requestPageCommentsInHistory("xwiki", "Blog", "This is test 2", "6.2");
        // Log.d("Info","worked");

        // Test page comments implementation in history and version
        // Comments cs= request.requestPageCommentsInHistory("xwiki", "Blog", "This is test 2", "6.2","0");
        // Log.d("Info","worked");

        // Test page attachments implementation
        // Attachments as=request.requestAllPageAttachments("xwiki", "Blog", "This is test 2");
        // Log.d("Info","worked");

        // Test page attachment implementation by attachment name
        // String s=request.requestPageAttachment("xwiki", "Blog", "This is test 2","snapshot2.png");
        // Log.d("Info","worked");

        // Test page attachments in version implementation by attachment name
        // Attachments as=request.requestPageAttachmentsInHistory("xwiki", "Blog", "This is test 2","7.1");
        // Log.d("Info","worked");

        // Test page attachment implementation by attachment name
        // String s=request.requestPageAttachmentsInHistory("xwiki", "Blog", "This is test 2","7.1","snapshot2.png");
        // Log.d("Info","worked");

        // Test page attachment versions in a page
//        Attachments as =
//            request.requestPageAttachmentsInAttachmentHistory("xwiki", "Blog", "This is test 2", "snapshot2.png");
//        Log.d("Info", "worked");
        
        // Test page objects in a page
//      Objects os =request.requestAllObjects("xwiki", "Blog", "This is test 2");
//      Log.d("Info", "worked");
    }
}
