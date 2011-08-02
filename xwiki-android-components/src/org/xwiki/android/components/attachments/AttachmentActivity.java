package org.xwiki.android.components.attachments;

import java.util.List;

import org.xwiki.android.components.R;
import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.Requests;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AttachmentActivity extends ListActivity
{
    private String[] data;

    private String wikiName, spaceName, pageName, username, password, url;

    private boolean isSecured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        wikiName = getIntent().getExtras().getString("wikiname");
        spaceName = getIntent().getExtras().getString("spacename");
        pageName = getIntent().getExtras().getString("pagename");
        url = getIntent().getExtras().getString("url");

        if (getIntent().getExtras().getString("password") != null
            && getIntent().getExtras().getString("username") != null) {
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");
            isSecured = true;
        }

        initDataArray();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.attachment_list_item, data));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView< ? > arg0, View view, int arg2, long arg3)
            {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataArray()
    {
        Requests request = new Requests(url);
        if (isSecured) {
            request.setAuthentication(username, password);
        }
        Attachments attachments = request.requestAllPageAttachments(wikiName, spaceName, pageName);

        List<Attachment> attachmentList = attachments.getAttachments();

        data = new String[attachmentList.size()];

        for (int i = 0; i < data.length; i++) {
            data[i] = attachmentList.get(i).getName();
        }
    }
}
