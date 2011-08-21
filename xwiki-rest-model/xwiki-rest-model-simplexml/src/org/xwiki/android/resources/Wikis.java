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

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="wiki" type="{http://www.xwiki.org}Wiki" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference = "http://www.xwiki.org")
public class Wikis
{
    @ElementList(name = "wiki", inline = true)
    public List<Wiki> wikis;

    /**
     * Gets the value of the wikis property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the Simple-xml object. This is why there is not a <CODE>set</CODE>
     * method for the wikis property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getWikis().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Wiki }
     */
    public List<Wiki> getWikis()
    {
        if (wikis == null) {
            wikis = new ArrayList<Wiki>();
        }
        return this.wikis;
    }

    public Wikis withWikis(Wiki... values)
    {
        if (values != null) {
            for (Wiki value : values) {
                getWikis().add(value);
            }
        }
        return this;
    }

    public Wikis withWikis(Collection<Wiki> values)
    {
        if (values != null) {
            getWikis().addAll(values);
        }
        return this;
    }
}
