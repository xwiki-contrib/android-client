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

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Translations;

/**
 * XWiki Android REST Translation related source. Can get the translation details/list as a Translations objects of
 * Simple-xml object model. Translated pages will not requested through this class and it will provide from
 * PageResources class
 */
public class TranslationResources extends HttpConnector
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
     * Name of Wiki for acquiring translations
     */
    private String wikiName;

    /**
     * Name of space for acquiring translations
     */
    private String spaceName;

    /**
     * Name of page for acquiring translations
     */
    private String pageName;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the Wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     */
    public TranslationResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    /**
     * @return list of all the translations are included in Translations object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Translations getAllTranslations() throws RestConnectionException, RestException
    {

        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations";

        return buildTranslations(super.getResponse(Uri));
    }

    /**
     * Parse xml into a Translations object
     * 
     * @param content XML content
     * @return Translations object deserialized from the xml content
     */
    private Translations buildTranslations(String content)
    {
        Translations translations = new Translations();

        Serializer serializer = new Persister();

        try {
            translations = serializer.read(Translations.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return translations;
    }

    /**
     * Generate XML content from the Translations object
     * 
     * @param translations Translations object to be serialized into XML
     * @return XML content of the provided Translations object
     */
    private String buildXmlTranslations(Translations translations)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(translations, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }
}
