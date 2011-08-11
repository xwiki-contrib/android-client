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
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Class;

/**
 * XWiki Android REST Class related source. Can get the XWiki class details/list as a Classes objects or Class objects
 * of Simple-xml object model
 */
public class ClassResources extends HttpConnector
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
     * Name of Wiki for acquiring classes
     */
    private String wikiName;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     */
    public ClassResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
    }

    /**
     * @return list of all the Classes in the Wiki
     */
    public Classes getWikiClasses()
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes";

        return buildClasses(super.getResponse(Uri));
    }

    /**
     * @param classname name of the Class
     * @return Specific class with the provided class name
     */
    public Class getWikiClasses(String classname)
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes/" + classname;

        return buildClass(super.getResponse(Uri));
    }

    /**
     * Parse xml into a Classes object
     * 
     * @param content XML content
     * @return Classes object deserialized from the xml content
     */
    private Classes buildClasses(String content)
    {
        Classes classes = new Classes();

        Serializer serializer = new Persister();

        try {
            classes = serializer.read(Classes.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * Parse xml into a Class object
     * 
     * @param content XML content
     * @return Class object deserialized from the xml content
     */
    private Class buildClass(String content)
    {
        Class clas = new Class();

        Serializer serializer = new Persister();

        try {
            clas = serializer.read(Class.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return clas;
    }
}
