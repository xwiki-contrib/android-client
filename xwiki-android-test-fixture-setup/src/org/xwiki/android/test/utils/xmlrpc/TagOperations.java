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

package org.xwiki.android.test.utils.xmlrpc;

import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;

import java.io.IOException;
import java.io.StringWriter;

public class TagOperations
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring tags
     */
    private String wikiName;

    /**
     * Name of space for acquiring tags
     */
    private String spaceName;

    /**
     * Name of page for acquiring tags
     */
    private String pageName;

    /**
     * Type of the tag.
     * <p>
     * 0= Wiki tag 1=Page tag </>
     */
    private int tagType;
    private RestClient rpc;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     */
    TagOperations(String URLprefix, String wikiName, RestClient rpc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        tagType = 0;
        this.rpc=rpc;
    }

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     */
    TagOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc)
    {
        this.rpc=rpc;
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        tagType = 1;
    }

    /**
     * Gets all the tags either from wiki or from the page. If only the URL & Wiki name fields are provided then method
     * will return wiki tags and if space name and page name is provided it will return page tags
     * 
     * @return list of all tags as a Tags object
     * @throws IOException 
     * @throws RestException 
     */
    public Tags getTags() throws IOException, RestException
    {

        String Uri;

        if (tagType == 0) {
            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags/";
        } else if (tagType == 1) {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags";
        } else {
            Uri = "";
        }

        return buildTags(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * Adds tag to the wiki or the page depending on the fields provided in the constructor
     * 
     * @param tag Tag object to be added
     * @return statsu of the HTTP put request
     * @throws IOException 
     * @throws RestException 
     */
    public String addTag(Tag tag) throws IOException, RestException
    {

        String Uri = null;        
        if (tagType == 0) {

            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags";
            String response = EntityUtils.toString(rpc.getRequest(Uri).getEntity());
            Tags temp_tags;

            temp_tags = buildTags(response);

            temp_tags.getTags().add(tag);

            return rpc.putRequest(Uri, buildXml(temp_tags)).getStatusLine().toString();

        } else if (tagType == 1) {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags";
            String response = EntityUtils.toString(rpc.getRequest(Uri).getEntity());
            Tags temp_tags;

            temp_tags = buildTags(response);

            temp_tags.getTags().add(tag);

            return rpc.putRequest(Uri, buildXml(temp_tags)).getStatusLine().toString();
        } 
        throw new IllegalStateException("tagType should be =0 or 1");
    }
    
    public String setTags(Tags tags)throws IOException, RestException
    {
    	String Uri = null;
        
        if (tagType == 0) {

            Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/tags"; 
            
            return rpc.putRequest(Uri, buildXml(tags)).getStatusLine().toString();

        } else {
            Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                    + "/tags";
            return rpc.putRequest(Uri, buildXml(tags)).getStatusLine().toString();
        }
       
    }

    /**
     * Parse xml into a Tags object
     * 
     * @param content XML content
     * @return Tags object deserialized from the xml content
     */
    private Tags buildTags(String content)
    {
        Tags tags = new Tags();

        Serializer serializer = new Persister();

        try {
            tags = serializer.read(Tags.class, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tags;
    }

    /**
     * Generate XML content from the Tags object
     * 
     * @param tags Tags object to be serialized into XML
     * @return XML content of the provided Tags object
     */
    private String buildXml(Tags tags)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(tags, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
