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

package org.xwiki.android.components.pageviewer;

import org.xwiki.android.components.IntentExtra;

import android.app.Activity;
import android.os.Bundle;

/**
 * UI Component for the XWiki Page viewing
 * */
public class XWikiPageViewerActivity extends Activity
{
    public static final String INTENT_EXTRA_PUT_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_PUT_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_PUT_PASSWORD = IntentExtra.PASSWORD;

    public static final String INTENT_EXTRA_PUT_WIKI_NAME = IntentExtra.WIKI_NAME;

    public static final String INTENT_EXTRA_PUT_SPACE_NAME = IntentExtra.SPACE_NAME;

    public static final String INTENT_EXTRA_PUT_PAGE_NAME = IntentExtra.PAGE_NAME;

    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD) != null
            && getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME) != null) {
            data = new String[6];
            data[4] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_USERNAME);
            data[5] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PASSWORD);
        } else {
            data = new String[4];
        }

        data[0] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_WIKI_NAME);
        data[1] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_SPACE_NAME);
        data[2] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_PAGE_NAME);
        data[3] = getIntent().getExtras().getString(INTENT_EXTRA_PUT_URL);

        setContentView(new XWikiPageViewerLayout(this, data));

    }

}
