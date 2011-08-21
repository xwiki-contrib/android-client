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

package org.xwiki.android.components.classviewer;

import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.resources.Class;
import org.xwiki.android.rest.Requests;

import android.app.Activity;
import android.os.Bundle;

/**
 * Class properties viewomg UI component
 */
public class ClassViewerActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_CLASS_NAME = IntentExtra.CLASS_NAME;

    private String url, wikiName, username, password, className;

    private boolean isSecured = false;

    private Class classObject;

    private Requests request;

    private ClassViewer classEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        url = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);
        wikiName = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        className = getIntent().getExtras().getString(INTENT_EXTRA_PUT_CLASS_NAME);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            username = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            password = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
            isSecured = true;
        }

        classObject = setupClass();
        classEditor = new ClassViewer(this, classObject);

        setContentView(classEditor);
    }

    private Class setupClass()
    {
        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }

        Class temp_class = request.requestWikiClasses(wikiName, className);

        return temp_class;
    }
}
