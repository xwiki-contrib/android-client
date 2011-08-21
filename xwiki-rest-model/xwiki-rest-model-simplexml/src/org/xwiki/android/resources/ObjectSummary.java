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
 * Java class for ObjectSummary complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectSummary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="guid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wiki" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="space" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="headline" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class ObjectSummary extends LinkCollection
{

    @Element(required = false)
    public String id;

    @Element(required = false)
    public String guid;

    @Element(required = false)
    public String pageId;

    @Element(required = false)
    public String wiki;

    @Element(required = false)
    public String space;

    @Element(required = false)
    public String pageName;

    @Element(required = false)
    public String className;

    @Element(required = false)
    public int number;

    @Element(required = false)
    public String headline;

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
     * Gets the value of the guid property.
     * 
     * @return possible object is {@link String }
     */
    public String getGuid()
    {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setGuid(String value)
    {
        this.guid = value;
    }

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
     * Gets the value of the number property.
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * Sets the value of the number property.
     */
    public void setNumber(int value)
    {
        this.number = value;
    }

    /**
     * Gets the value of the headline property.
     * 
     * @return possible object is {@link String }
     */
    public String getHeadline()
    {
        return headline;
    }

    /**
     * Sets the value of the headline property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setHeadline(String value)
    {
        this.headline = value;
    }

    public ObjectSummary withId(String value)
    {
        setId(value);
        return this;
    }

    public ObjectSummary withGuid(String value)
    {
        setGuid(value);
        return this;
    }

    public ObjectSummary withPageId(String value)
    {
        setPageId(value);
        return this;
    }

    public ObjectSummary withWiki(String value)
    {
        setWiki(value);
        return this;
    }

    public ObjectSummary withSpace(String value)
    {
        setSpace(value);
        return this;
    }

    public ObjectSummary withPageName(String value)
    {
        setPageName(value);
        return this;
    }

    public ObjectSummary withClassName(String value)
    {
        setClassName(value);
        return this;
    }

    public ObjectSummary withNumber(int value)
    {
        setNumber(value);
        return this;
    }

    public ObjectSummary withHeadline(String value)
    {
        setHeadline(value);
        return this;
    }

    @Override
    public ObjectSummary withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public ObjectSummary withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
