package org.xwiki.android.client;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.components.attachments.AttachmentActivity;
import org.xwiki.android.components.classviewer.ClassListActivity;
import org.xwiki.android.components.classviewer.ClassViewerActivity;
import org.xwiki.android.components.commenteditor.CommentEditorActivity;
import org.xwiki.android.components.objectnavigator.ObjectNavigatorActivity;
import org.xwiki.android.components.pageviewer.XWikiPageViewerActivity;
import org.xwiki.android.rest.ClassResources;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PageViewActivity extends TabActivity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    private String url, username, password, wikiName, spaceName, pageName;

    private boolean isSecured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pageview);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabSpec TabSpecView = tabHost.newTabSpec("view");
        TabSpec TabSpecObjects = tabHost.newTabSpec("objects");
        TabSpec TabSpecClasses = tabHost.newTabSpec("classes");

        TabSpecView.setIndicator("View").setContent(setupPageViewerIntent());
        TabSpecObjects.setIndicator("Objects").setContent(setupObjectsViewerIntent());
        TabSpecClasses.setIndicator("Classes").setContent(setupClassViewerIntent());

        tabHost.addTab(TabSpecView);
        tabHost.addTab(TabSpecObjects);
        tabHost.addTab(TabSpecClasses);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pageview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.item_add_comment:
                addComment();
                return true;
            case R.id.item_add_attachment:
                addAttachment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void addComment(){
        Intent intent = new Intent(this, CommentEditorActivity.class);

        if (isSecured) {
            intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_USERNAME, username);
            intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        }

        intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_URL, url);
        intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        intent.putExtra(CommentEditorActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);
        
        startActivity(intent);
    }

    private void addAttachment(){
        Intent intent = new Intent(this, AttachmentActivity.class);

        if (isSecured) {
            intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_USERNAME, username);
            intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        }

        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_URL, url);
        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        intent.putExtra(AttachmentActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);
        
        startActivity(intent);
    }
    
    private Intent setupObjectsViewerIntent()
    {
        Intent intent = new Intent(this, ObjectNavigatorActivity.class);

        if (isSecured) {
            intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_USERNAME, username);
            intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        }

        intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_URL, url);
        intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        intent.putExtra(ObjectNavigatorActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);

        return intent;
    }

    private Intent setupPageViewerIntent()
    {
        Intent intent = new Intent(this, XWikiPageViewerActivity.class);

        if (isSecured) {
            intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_USERNAME, username);
            intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        }

        intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_URL, url);
        intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        intent.putExtra(XWikiPageViewerActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);

        return intent;
    }

    private Intent setupClassViewerIntent()
    {
        Intent intent = new Intent(this, ClassListActivity.class);

        if (isSecured) {
            intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_USERNAME, username);
            intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_PASSWORD, password);
        }

        intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_URL, url);
        intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_WIKI_NAME, wikiName);
        intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_SPACE_NAME, spaceName);
        intent.putExtra(ClassListActivity.INTENT_EXTRA_PUT_PAGE_NAME, pageName);

        return intent;
    }
}
