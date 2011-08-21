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
 *         &lt;element name="searchResult" type="{http://www.xwiki.org}SearchResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class SearchResults
{

    @ElementList(name = "searchResult", inline=true,required= false)
    public List<SearchResult> searchResults;

    @Attribute(required=false)
    public String template;

    /**
     * Gets the value of the searchResults property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the Simple-xml object. This is why there is not a <CODE>set</CODE> method for
     * the searchResults property.
     * <p>
     * For example, to add a new item, do as follows:s
     * 
     * <pre>
     * getSearchResults().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SearchResult }
     */
    public List<SearchResult> getSearchResults()
    {
        if (searchResults == null) {
            searchResults = new ArrayList<SearchResult>();
        }
        return this.searchResults;
    }

    /**
     * Gets the value of the template property.
     * 
     * @return possible object is {@link String }
     */
    public String getTemplate()
    {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setTemplate(String value)
    {
        this.template = value;
    }

    public SearchResults withSearchResults(SearchResult... values)
    {
        if (values != null) {
            for (SearchResult value : values) {
                getSearchResults().add(value);
            }
        }
        return this;
    }

    public SearchResults withSearchResults(Collection<SearchResult> values)
    {
        if (values != null) {
            getSearchResults().addAll(values);
        }
        return this;
    }

    public SearchResults withTemplate(String value)
    {
        setTemplate(value);
        return this;
    }
}
