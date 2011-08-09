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

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * <p>
 * Java class for Link complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Link">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="href" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rel" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="hrefLang" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
public class Link
{
    @Attribute(required = false)
    public String href;

    @Attribute(required = false)
    public String rel;

    @Attribute(required = false)
    public String type;

    @Attribute(required = false)
    public String hrefLang;

    /**
     * Gets the value of the href property.
     * 
     * @return possible object is {@link String }
     */
    public String getHref()
    {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setHref(String value)
    {
        this.href = value;
    }

    /**
     * Gets the value of the rel property.
     * 
     * @return possible object is {@link String }
     */
    public String getRel()
    {
        return rel;
    }

    /**
     * Sets the value of the rel property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setRel(String value)
    {
        this.rel = value;
    }

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
     * Gets the value of the hrefLang property.
     * 
     * @return possible object is {@link String }
     */
    public String getHrefLang()
    {
        return hrefLang;
    }

    /**
     * Sets the value of the hrefLang property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setHrefLang(String value)
    {
        this.hrefLang = value;
    }

    public Link withHref(String value)
    {
        setHref(value);
        return this;
    }

    public Link withRel(String value)
    {
        setRel(value);
        return this;
    }

    public Link withType(String value)
    {
        setType(value);
        return this;
    }

    public Link withHrefLang(String value)
    {
        setHrefLang(value);
        return this;
    }

}
