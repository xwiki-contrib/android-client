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

package org.xwiki.android.components.propertyeditor;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Property Editor UI Component
 * */
public class PropertyEditorActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    public static final String INTENT_EXTRA_PUT_OBJECT_ID = IntentExtra.OBJECT_ID;

    public static final String INTENT_EXTRA_PUT_CLASS_NAME = IntentExtra.CLASS_NAME;

    public static final String INTENT_EXTRA_PUT_PROPERTY_NAME = IntentExtra.PROPERTY_NAME;

    private String url, wikiName, spaceName, pageName, username, password, className, objectID, propertyName;

    private boolean isSecured;

    private Property property;

    private Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        spaceName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        pageName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
        className = getIntent().getExtras().getString(INTENT_EXTRA_PUT_CLASS_NAME);
        objectID = getIntent().getExtras().getString(INTENT_EXTRA_PUT_OBJECT_ID);
        propertyName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PROPERTY_NAME);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        setContentView(new PropertyEditor(this, setupProperty()));
    }

    private Property setupProperty()
    {
        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        property = request.requestObjectProperty(wikiName, spaceName, pageName, className, objectID, propertyName);

        return property;
    }

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        addPropertyToXWiki();
        super.onDestroy();
    }

    private void addPropertyToXWiki()
    {
        String s = request.addProperty(wikiName, spaceName, pageName, className, objectID, property);
        Log.d("Object edit", s);
    }

}
