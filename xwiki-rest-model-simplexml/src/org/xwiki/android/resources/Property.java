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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * <p>
 * Java class for Property complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Property">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="attribute" type="{http://www.xwiki.org}Attribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Element(required = false)
public class Property extends LinkCollection
{

    @ElementList(name = "attribute", inline = true)
    public List<org.xwiki.android.resources.Attribute> attributes;

    @Element(required = false)
    public String value;

    @Attribute(required = false)
    public String name;

    @Attribute(required = false)
    public String type;

    /**
     * Gets the value of the attributes property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the Simple-xml object. This is why there is not a <CODE>set</CODE>
     * method for the attributes property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAttributes().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Attribute }
     */
    public List<org.xwiki.android.resources.Attribute> getAttributes()
    {
        if (attributes == null) {
            attributes = new ArrayList<org.xwiki.android.resources.Attribute>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setValue(String value)
    {
        this.value = value;
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

    public Property withAttributes(Collection<org.xwiki.android.resources.Attribute> values)
    {
        if (values != null) {
            getAttributes().addAll(values);
        }
        return this;
    }

    public Property withValue(String value)
    {
        setValue(value);
        return this;
    }

    public Property withName(String value)
    {
        setName(value);
        return this;
    }

    public Property withType(String value)
    {
        setType(value);
        return this;
    }

    @Override
    public Property withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public Property withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}
