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
 * Java class for Wiki complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Wiki">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class Wiki extends LinkCollection
{
    @Element(required = false)
    public String id;

    @Element(required = false)
    public String name;

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

    public Wiki withId(String value)
    {
        setId(value);
        return this;
    }

    public Wiki withName(String value)
    {
        setName(value);
        return this;
    }

    @Override
    public Wiki withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public Wiki withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
