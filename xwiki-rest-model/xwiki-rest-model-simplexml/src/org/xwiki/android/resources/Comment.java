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
 * Java class for Comment complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Comment">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="highlight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="replyTo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class Comment extends LinkCollection
{
    @Element(required = false)
    public int id;

    @Element(required = false)
    public String pageId;

    @Element(required = false)
    public String author;

    @Element(required = false)
    public String date;
    //public Calendar date;

    @Element(required = false)
    public String highlight;

    @Element(required = false)
    public String text;

    @Element(required = false)
    public Integer replyTo;

    /**
     * Gets the value of the id property.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value)
    {
        this.id = value;
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
     * Gets the value of the author property.
     * 
     * @return possible object is {@link String }
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setAuthor(String value)
    {
        this.author = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return possible object is {@link String }
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setDate(String value)
    {
        this.date = value;
    }

    /**
     * Gets the value of the highlight property.
     * 
     * @return possible object is {@link String }
     */
    public String getHighlight()
    {
        return highlight;
    }

    /**
     * Sets the value of the highlight property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setHighlight(String value)
    {
        this.highlight = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return possible object is {@link String }
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setText(String value)
    {
        this.text = value;
    }

    /**
     * Gets the value of the replyTo property.
     * 
     * @return possible object is {@link Integer }
     */
    public Integer getReplyTo()
    {
        return replyTo;
    }

    /**
     * Sets the value of the replyTo property.
     * 
     * @param value allowed object is {@link Integer }
     */
    public void setReplyTo(Integer value)
    {
        this.replyTo = value;
    }

    public Comment withId(int value)
    {
        setId(value);
        return this;
    }

    public Comment withPageId(String value)
    {
        setPageId(value);
        return this;
    }

    public Comment withAuthor(String value)
    {
        setAuthor(value);
        return this;
    }

    public Comment withDate(String value)
    {
        setDate(value);
        return this;
    }

    public Comment withHighlight(String value)
    {
        setHighlight(value);
        return this;
    }

    public Comment withText(String value)
    {
        setText(value);
        return this;
    }

    public Comment withReplyTo(Integer value)
    {
        setReplyTo(value);
        return this;
    }

    @Override
    public Comment withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public Comment withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
