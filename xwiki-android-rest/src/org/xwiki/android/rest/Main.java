package org.xwiki.android.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.resources.Wikis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main extends Activity
{

    // IP of the development machine in Android emulator 10.0.2.2
    // These variables are uses for testing
    String domain = "10.0.2.2:8080";

    String searchKeyword = "test";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
    }

    public void searchButtonClick(View v)
    {

        // TextView tv_result = (TextView) findViewById(R.id.textView1);
        // TextView et_domain = (TextView) findViewById(R.id.editText_domain);
        // TextView et_searchText = (TextView) findViewById(R.id.editText_SearchText);
        //
        // domain = et_domain.getText().toString();
        // searchKeyword = et_searchText.getText().toString();
        //
        // Log.d("Info", "domain=" + domain + " text=" + searchKeyword);
//         Requests request = new Requests(domain);
//         request.setAuthentication("Admin", "admin");
         
         //Add Property
         //http://localhost:8080/xwiki/rest/wikis/xwiki/spaces/Blog/pages/test3/objects/Blog.BlogPostClass/0/properties/content?media=xml
//         Property property = request.requestObjectProperty("xwiki", "Blog", "test3", "Blog.BlogPostClass", "0", "content");
//         Log.d("prop value", property.value);
//         property.setValue("This is the new Content");
//         request.addProperty("xwiki", "Blog", "test3", "Blog.BlogPostClass", "0", property);

        // Test wiki search implementation
        // SearchResults sr = request.requestSearch("xwiki",searchKeyword);
        // Log.d("Info", "worked");

        // Test space search implementation
        // SearchResults sr = request.requestSearch("xwiki","Blog",searchKeyword);
        // Log.d("Info", "worked");

        // Test wiki implementation
        // Wikis ws= request.requestWikis();
        // Log.d("Info", "worked");
        // tv_result.setText("Wiki Results\n" + result);

        // Test space implementation
        // Spaces s = request.requestSpaces("xwiki");
        // Log.d("Spaces", "It worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Space Results\n" + result);

        // [ok]
        // Test all pages implementation
        // Pages ps = request.requestAllPages("xwiki", "Blog");
        // for (int i = 0; i < ps.pageSummaries.size(); i++) {
        // Log.d("id", "id=" + i + "=" + ps.pageSummaries.get(i).id);
        // }
        // Log.d("Info", "worked");
        // tv_result.setText("All Pages Results\n" + result);

        // [ok]
        // Test one page implementation
        // Page p = request.requestPage("xwiki", "Blog", "test");
        // Log.d("page", "page details=" + p.name + "," + p.title + "," +
        // p.xwikiRelativeUrl + "," + p.content);
        // Log.d("Info", "worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // [ok]
        // Test one page adding
        // Page p = request.requestPage("xwiki", "Blog", "test");
        // p.setContent("Content is changed by android client");
        // String s= request.addPage("xwiki", "Blog", "test", p);
        // Log.d("Info", "worked");
        
        // Test one page delete implementation
        // request.setAuthentication("Admin", "admin");
        // String result = request.deletePage("xwiki", "Blog", "This is test");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // [ok]
        // Test one page history implementation
        // History h = request.requestPageHistory("xwiki", "Blog",
        // "test");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // [ok]
        // Test page children implementation
        // Pages ps=request.requestPageChildren("xwiki", "Blog",
        // "This is test 2");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        
        // Test page tags implementation
        // Tags ts= request.requestPageTags("xwiki", "Blog", "test");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test wiki tags implementation
        // Tags ts = request.requestWikiTags("xwiki");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // Test wiki tags put method
        // Tag tag=new Tag();
        // tag.name = "working2";
        // String rep = request.addPageTag("xwiki", "Blog", "test", tag);
        // Log.d("Info","worked");

        // [ok]
        // Test page comments implementation
        // Comments cs = request.requestPageComments("xwiki", "Blog",
        // "test");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // [ok]
        // Test page comment implementation with id
        // Comment c = request.requestPageComments("xwiki", "Blog",
        // "This is test 2","0");
        // Log.d("Info","worked");
        // Log.d("Final result:", "result=" + result);
        // tv_result.setText("Page Results\n" + result);

        // [ok]
        // Test page comments implementation in history
        // Comments cs= request.requestPageCommentsInHistory("xwiki", "Blog",
        // "This is test 2", "6.2");
        // Log.d("Info","worked");

        // [ok]
        // Test page comments implementation in history and version
        // Comments cs= request.requestPageCommentsInHistory("xwiki", "Blog",
        // "This is test 2", "6.2","0");
        // Log.d("Info","worked");

        // Test page attachments implementation
        // Attachments as=request.requestAllPageAttachments("xwiki", "Blog",
        // "test");
        // Log.d("Info","worked");

        // Test page attachment implementation by attachment name

        /*
         * request.setAuthentication("Admin", "admin"); InputStream in = request.requestPageAttachment("xwiki", "Blog",
         * "test", "device.png"); FileOutputStream f; try { f = new FileOutputStream(new File("/sdcard/", "temp"));
         * byte[] buffer = new byte[1024]; int len1 = 0; while ((len1 = in.read(buffer)) > 0) { f.write(buffer, 0,
         * len1); } f.close(); Log.d("Info", "worked"); // Intent i = new Intent(android.content.Intent.ACTION_VIEW);
         * //i.setDataAndType(Uri.parse("file://" + "/sdcard/temp.png"), "image/png");
         * //i.setDataAndType((Uri.parse("file://" + "/sdcard/temp"), "text/plain"); //i.setData(Uri.parse("file://" +
         * "/sdcard/temp.png")); startActivity(i); } catch (FileNotFoundException e) { // TODO Auto-generated catch
         * block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
         * }
         */

        // Test page attachment upload
        // request.setAuthentication("Admin", "admin");
        // "xwiki", "Blog", "test", "device.png");
        // request.addPageAttachment("xwiki", "Blog", "test", "/sdcard/", "classeditor.png");

        // Test page attachments in version implementation by attachment name
        // Attachments as=request.requestPageAttachmentsInHistory("xwiki",
        // "Blog", "This is test 2","7.1");
        // Log.d("Info","worked");

        // Test page attachment implementation by attachment name
        // String s=request.requestPageAttachmentsInHistory("xwiki", "Blog",
        // "This is test 2","7.1","snapshot2.png");
        // Log.d("Info","worked");

        // Test page attachment versions in a page
        // Attachments as =
        // request.requestPageAttachmentsInAttachmentHistory("xwiki", "Blog",
        // "This is test 2", "snapshot2.png");
        // Log.d("Info", "worked");

        // Test translations in a page
        // Translations ts=request.requestAllPageTranslations("xwiki", "Blog",
        // "This is test 2");
        // Log.d("Info", "worked");

        // Test page translation implementation
        // Page p = request.requestPageTranslation("xwiki", "Blog",
        // "This is test 2","null");
        // Log.d("Info","worked");

        // [ok]
        // Test one page history implementation in language
        // History h = request.requestPageHistoryInLanguage("xwiki", "Blog",
        // "This is test 2", "null");
        // Log.d("Info","worked");

        // Test page translation with history implementation
        // Page p = request.requestPageTranslationInHistory("xwiki", "Blog",
        // "This is test 2","null","6.1");
        // Log.d("Info","worked");

        // [ok]
        // Test page objects in a page
        // Objects os = request.requestAllObjects("xwiki", "Blog", "test");
        // Log.d("Info", "worked");

        // [ok]
        // Test page object put
        // org.xwiki.android.resources.Object obj=new org.xwiki.android.resources.Object();
        //
        // Attribute att = new Attribute();
        // att.setName("name");
        // att.setValue("category");
        //
        // Property prop = new Property();
        // prop.setType("com.xpn.xwiki.objects.classes.TextAreaClass");
        // prop.setName("content");
        // prop.setValue("Blog.Other");
        // prop.getAttributes().add(att);
        //
        // obj.getProperties().add(prop);
        //
        // String s= request.addObject("xwiki", "Blog", "test", obj);
        // Log.d("Info", "worked");

        // [ok]
        // Test page objects in a specific class in a page
        // Objects os = request.requestObjectsInClass("xwiki", "Blog", "test",
        // "Blog.BlogPostClass");
        // Log.d("Info", "worked");

        // [ok]
        // Test object in a specific obejct number in a objects
        // Object o = request.requestObject("xwiki", "Blog", "test",
        // "Blog.BlogPostClass", "0");
        // Log.d("Info", "worked");

        // [ok]
        // Test properties of a object in a specific obejct number in a objects
        // Properties ps = request.requestObjectAllProperties("xwiki", "Blog",
        // "test", "Blog.BlogPostClass", "0");
        // Log.d("Info", "worked");

        // [ok]
        // Test property of a object in a specific obejct number in a objects
        // Property p = request.requestObjectProperty("xwiki", "Blog", "test",
        // "Blog.BlogPostClass", "0", "content");
        // Log.d("Info", "worked");

        // Test for authentication
        // HttpConnector hc=new HttpConnector();
        // Log.d("Reply", String.valueOf(hc.checkLogin("chamika", "chamikaya", domain)));

        // [ok]
        // Test for adding page comment
        // Comment comment = new Comment();
        // comment.setText("This is tesing comment in android rest");
        // String s = request.addPageComment("xwiki", "Blog", "test", comment);
        // Log.d("Info","It works");

        // Test for classes in wiki
        // Classes cs = request.requestWikiClasses("xwiki");
        // Log.d("Info","It works");

        // Test for classes in wiki with classname
        // org.xwiki.android.resources.Class c = request.requestWikiClasses("xwiki", "Blog.BlogPostClass");
        // Log.d("Info","It works");
    }
}
