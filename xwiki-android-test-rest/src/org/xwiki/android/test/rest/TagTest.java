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

import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class TagTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, tagText;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestResources.WIKI_NAME;
        spaceName = TestResources.SPACE_NAME;
        pageName = TestResources.PAGE_NAME;
        url = TestResources.URL;
        tagText = TestResources.TAG_WORD;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestPageTags() throws Throwable
    {
        Tags tags = request.requestPageTags(wikiName, spaceName, pageName);
        assertNotNull(tags);
    }

    public void testAddPageTags() throws Throwable
    {
        Tag tag = new Tag();
        tag.name = tagText;
        String s = request.addPageTag(wikiName, spaceName, pageName, tag);
        assertNotNull(s);
    }

    public void testRequestWikiTags() throws Throwable
    {
        Tags tags = request.requestWikiTags(wikiName);
        assertNotNull(tags);
    }
}
