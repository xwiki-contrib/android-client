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

package org.xwiki.android.resources;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <p>
 * Java class for HistorySummary complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistorySummary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="pageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wiki" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="space" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="majorVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minorVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="modified" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class HistorySummary extends LinkCollection
{

    @Element(required = false)
    public String pageId;

    @Element(required = false)
    public String wiki;

    @Element(required = false)
    public String space;

    @Element(required = false)
    public String name;

    @Element(required = false)
    public String version;

    @Element(required = false)
    public int majorVersion;

    @Element(required = false)
    public int minorVersion;

    @Element(required = false)
    public String modified;

    @Element(required = false)
    public String modifier;

    @Element(required = false)
    public String language;

    /**
     * Gets the value of the pageId property.
     * 
     * @return possible object is {@link String }
     */
    public String getPageId()
    {
        return pageId;
    }

    /**
     * Sets the value of the pageId property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setPageId(String value)
    {
        this.pageId = value;
    }

    /**
     * Gets the value of the wiki property.
     * 
     * @return possible object is {@link String }
     */
    public String getWiki()
    {
        return wiki;
    }

    /**
     * Sets the value of the wiki property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setWiki(String value)
    {
        this.wiki = value;
    }

    /**
     * Gets the value of the space property.
     * 
     * @return possible object is {@link String }
     */
    public String getSpace()
    {
        return space;
    }

    /**
     * Sets the value of the space property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setSpace(String value)
    {
        this.space = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setVersion(String value)
    {
        this.version = value;
    }

    /**
     * Gets the value of the majorVersion property.
     */
    public int getMajorVersion()
    {
        return majorVersion;
    }

    /**
     * Sets the value of the majorVersion property.
     */
    public void setMajorVersion(int value)
    {
        this.majorVersion = value;
    }

    /**
     * Gets the value of the minorVersion property.
     */
    public int getMinorVersion()
    {
        return minorVersion;
    }

    /**
     * Sets the value of the minorVersion property.
     */
    public void setMinorVersion(int value)
    {
        this.minorVersion = value;
    }

    /**
     * Gets the value of the modified property.
     * 
     * @return possible object is {@link String }
     */
    public String getModified()
    {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setModified(String value)
    {
        this.modified = value;
    }

    /**
     * Gets the value of the modifier property.
     * 
     * @return possible object is {@link String }
     */
    public String getModifier()
    {
        return modifier;
    }

    /**
     * Sets the value of the modifier property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setModifier(String value)
    {
        this.modifier = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return possible object is {@link String }
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setLanguage(String value)
    {
        this.language = value;
    }

    public HistorySummary withPageId(String value)
    {
        setPageId(value);
        return this;
    }

    public HistorySummary withWiki(String value)
    {
        setWiki(value);
        return this;
    }

    public HistorySummary withSpace(String value)
    {
        setSpace(value);
        return this;
    }

    public HistorySummary withName(String value)
    {
        setName(value);
        return this;
    }

    public HistorySummary withVersion(String value)
    {
        setVersion(value);
        return this;
    }

    public HistorySummary withMajorVersion(int value)
    {
        setMajorVersion(value);
        return this;
    }

    public HistorySummary withMinorVersion(int value)
    {
        setMinorVersion(value);
        return this;
    }

    public HistorySummary withModified(String value)
    {
        setModified(value);
        return this;
    }

    public HistorySummary withModifier(String value)
    {
        setModifier(value);
        return this;
    }

    public HistorySummary withLanguage(String value)
    {
        setLanguage(value);
        return this;
    }

    @Override
    public HistorySummary withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public HistorySummary withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
