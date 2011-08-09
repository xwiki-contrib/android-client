//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.02 at 07:04:45 PM IST 
//

package org.xwiki.android.resources;

import java.util.Collection;

/*
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 */

/**
 * <p>
 * Java class for XWiki complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XWiki">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xwiki.org}LinkCollection">
 *       &lt;sequence>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="syntaxes" type="{http://www.xwiki.org}Syntaxes"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
/*
 * //@XmlAccessorType(XmlAccessType.FIELD) //@XmlType(name = "XWiki", propOrder = { "version", "syntaxes" })
 * //@XmlRootElement(name = "xwiki")
 */
public class Xwiki extends LinkCollection
{

    // @XmlElement(required = true)
    public String version;

    // @XmlElement(required = true)
    public Syntaxes syntaxes;

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
     * Gets the value of the syntaxes property.
     * 
     * @return possible object is {@link Syntaxes }
     */
    public Syntaxes getSyntaxes()
    {
        return syntaxes;
    }

    /**
     * Sets the value of the syntaxes property.
     * 
     * @param value allowed object is {@link Syntaxes }
     */
    public void setSyntaxes(Syntaxes value)
    {
        this.syntaxes = value;
    }

    public Xwiki withVersion(String value)
    {
        setVersion(value);
        return this;
    }

    public Xwiki withSyntaxes(Syntaxes value)
    {
        setSyntaxes(value);
        return this;
    }

    @Override
    public Xwiki withLinks(Link... values)
    {
        if (values != null) {
            for (Link value : values) {
                getLinks().add(value);
            }
        }
        return this;
    }

    @Override
    public Xwiki withLinks(Collection<Link> values)
    {
        if (values != null) {
            getLinks().addAll(values);
        }
        return this;
    }

}