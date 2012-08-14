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

package org.xwiki.android.test.rest;

import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.rest.Requests;
import org.xwiki.android.test.rest.env.TestConstants;

import android.test.AndroidTestCase;

public class SearchTest extends AndroidTestCase
{
    private String wikiName, spaceName, url, username, password, keyword;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        url = TestConstants.SERVER_URL;
        keyword = TestConstants.SEARCH_KEWORD;

        if (isSecured) {
            username = TestConstants.USERNAME;
            password = TestConstants.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testWikiSearch() throws Throwable
    {
        SearchResults sr = request.requestSearch(wikiName, keyword);
        assertNotNull(sr);
    }

    public void testSpaceSearch() throws Throwable
    {
        SearchResults sr = request.requestSearch(wikiName, spaceName, keyword);
        assertNotNull(sr);
    }

}
