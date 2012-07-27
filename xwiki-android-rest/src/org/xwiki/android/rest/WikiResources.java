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

package org.xwiki.android.rest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Wikis;

/**
 * XWiki Android REST Wiki related source. Can get the Wiki details/list as a Wikis objects of Simple-xml object model
 */
public class WikiResources extends HttpConnector
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String WIKI_URL = "/xwiki/rest/wikis";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     */
    public WikiResources(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    /**
     * @return list of all the Wikis in the provided XWiki server hosted in XWiki URL
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Wikis getWikis() throws RestConnectionException, RestException
    {

        String Uri = "http://" + URLprefix + WIKI_URL;

        return buildWikis(super.getResponse(Uri));
    }

    /**
     * Parse xml into a Wikis object
     * 
     * @param content XML content
     * @return Wikis object deserialized from the xml content
     */
    private Wikis buildWikis(String content)
    {
        Wikis wikis = new Wikis();

        Serializer serializer = new Persister();

        try {
            wikis = serializer.read(Wikis.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return wikis;
    }
}
