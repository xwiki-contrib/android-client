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
 * Java class for SearchResult complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageFullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wiki" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="space" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference = "http://www.xwiki.org")
public class SearchResult extends LinkCollection
{
    @Element(required = false)
    public String type;

    @Element(required = false)
    public String id;

    @Element(required = false)
    public String pageFullName;

    @Element(required = false)
    public String wiki;

    @Element(required = false)
    public String space;

    @Element(required = false)
    public String pageName;

    @Element(required = false)
    public String language;

    @Element(required = false)
    public String className;

    @Element(required = false)
    public Integer objectNumber;

    /**
     * Gets the value of the type property.
     * 
     * @return possible object is {@link String }
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setType(String value)
    {
        this.type = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link String }
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setId(String value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the pageFullName property.
     * 
     * @return possible object is {@link String }
     */
    public String getPageFullName()
    {
        return pageFullName;
    }

    /**
     * Sets the value of the pageFullName property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setPageFullName(String value)
    {
        this.pageFullName = value;
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
     * Gets the value of the pageName property.
     * 
     * @return possible object is {@link String }
     */
    public String getPageName()
    {
        return pageName;
    }

    /**
     * Sets the value of the pageName property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setPageName(String value)
    {
        this.pageName = value;
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

    /**
     * Gets the value of the className property.
     * 
     * @return possible object is {@link String }
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setClassName(String value)
    {
        this.className = value;
    }

    /**
     * Gets the value of the objectNumber property.
     * 
     * @return possible object is {@link Integer }
     */
    public Integer getObjectNumber()
    {
        return objectNumber;
    }

    /**
     * Sets the value of the objectNumber property.
     * 
     * @param value allowed object is {@link Integer }
     */
    public void setObjectNumber(Integer value)
    {
        this.objectNumber = value;
    }

    public SearchResult withType(String value)
    {
        setType(value);
        return this;
    }

    public SearchResult withId(String value)
    {
        setId(value);
        return this;
    }

    public SearchResult withPageFullName(String value)
    {
        setPageFullName(value);
        return this;
    }

    public SearchResult withWiki(String value)
    {
        setWiki(value);
        return this;
    }

    public SearchResult withSpace(String value)
    {
        setSpace(value);
        return this;
    }

    public SearchResult withPageName(String value)
    {
        setPageName(value);
        return this;
    }

    public SearchResult withLanguage(String value)
    {
        setLanguage(value);
        return this;
    }

    public SearchResult withClassName(String value)
    {
        setClassName(value);
        return this;
    }

    public SearchResult withObjectNumber(Integer value)
    {
        setObjectNumber(value);
        return this;
    }

    @Override
    public SearchResult withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public SearchResult withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
