/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.components.attachments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.resources.Attachment;
import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.Requests;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * UI Component which can be started for adding and viewing XWiki page attachments
 */
public class AttachmentActivity extends ListActivity
{
    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    private final int SELECT_FILE = 100;

    private final int VIEW_FILE = 101;

    private File currentFile;

    private String[] data;

    private String wikiName, spaceName, pageName, username, password, url;

    private boolean isSecured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        initDataArray();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.attachment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.add_attachment:
                addAttachment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

        setListAdapter(new ArrayAdapter<String>(this, R.layout.attachment_list_item, data));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView< ? > arg0, View view, int arg2, long arg3)
            {
                downloadAndOpenFile(((TextView) view).getText().toString());
            }
        });
    }

    private void addAttachment()
    {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedFileUri = data.getData();
                String selectedFilePath = getPath(selectedFileUri);
                uploadFile(selectedFilePath);
            }
        } else if (requestCode == VIEW_FILE) {
            currentFile.delete();
            Log.d("File delete", currentFile.getName() + " deleted");
        }
    }

    private String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void uploadFile(String file)
    {

        final File uploadingFile = new File(file);
        final TextView outputTextView = new TextView(this);

        outputTextView.setText("You are about to upload file " + uploadingFile.getName() + " which is "
            + uploadingFile.length()
            + "bytes in size. Data charges will apply for this process. Are you sure you want to upload this file? ");

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setView(outputTextView);

        alert.setTitle("File Upload");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Requests request = new Requests(url);

                if (isSecured) {
                    request.setAuthentication(username, password);
                }

                String path =
                    uploadingFile.getPath().substring(0,
                        uploadingFile.getPath().length() - uploadingFile.getName().length());

                Log.d("path", "final path=" + path);

                request.addPageAttachment(wikiName, spaceName, pageName, path, uploadingFile.getName());
                initDataArray();

                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });
        alert.show();

    }

    private void downloadAndOpenFile(final String filename)
    {

        final TextView outputTextView = new TextView(this);

        outputTextView.setText("You are about to download file " + filename
            + ".Data charges will apply for this process. Are you sure you want to download this file? ");

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setView(outputTextView);

        alert.setTitle("File Download");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
                Requests request = new Requests(url);

                if (isSecured) {
                    request.setAuthentication(username, password);
                }

                InputStream in = request.requestPageAttachment(wikiName, spaceName, pageName, filename);
                FileOutputStream f;
                try {

                    boolean exists = (new File(Environment.getExternalStorageDirectory() + "/xwikicache/").exists());
                    if (!exists) {
                        new File(Environment.getExternalStorageDirectory() + "/xwikicache/").mkdirs();
                    }

                    currentFile = new File(Environment.getExternalStorageDirectory() + "/xwikicache/", filename);

                    f = new FileOutputStream(currentFile);
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                    }
                    f.close();

                    Log.d("download", "filepath=" + currentFile.getPath());

                    MimeTypeMap map = MimeTypeMap.getSingleton();
                    String extension = map.getFileExtensionFromUrl(currentFile.getPath().toLowerCase());
                    String type = map.getMimeTypeFromExtension(extension);

                    Log.d("MIME type", "type=" + type);

                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse("file://" + currentFile.getPath()), type);

                    startActivityForResult(i, VIEW_FILE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ActivityNotFoundException e) {

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(AttachmentActivity.this);
                    alertbox.setMessage("No native or installed application found for viewing this file");
                    alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {
                        }
                    });
                    alertbox.show();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });
        alert.show();

    }
}
