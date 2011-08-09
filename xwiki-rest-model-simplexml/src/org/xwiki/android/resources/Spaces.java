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
 *         &lt;element name="space" type="{http://www.xwiki.org}Space" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@Root
@Namespace(reference="http://www.xwiki.org")
public class Spaces
{
    @ElementList(name = "space", inline=true)
    public List<Space> spaces;

    /**
     * Gets the value of the spaces property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the spaces property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSpaces().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Space }
     */
    public List<Space> getSpaces()
    {
        if (spaces == null) {
            spaces = new ArrayList<Space>();
        }
        return this.spaces;
    }

    public Spaces withSpaces(Space... values)
    {
        if (values != null) {
            for (Space value : values) {
                getSpaces().add(value);
            }
        }
        return this;
    }

    public Spaces withSpaces(Collection<Space> values)
    {
        if (values != null) {
            getSpaces().addAll(values);
        }
        return this;
    }
}
