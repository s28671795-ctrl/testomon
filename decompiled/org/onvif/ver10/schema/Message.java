/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ItemList;
import org.onvif.ver10.schema.MessageExtension;
import org.onvif.ver10.schema.PropertyOperation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"source", "key", "data", "extension"})
@XmlRootElement(name="Message")
public class Message {
    @XmlElement(name="Source")
    protected ItemList source;
    @XmlElement(name="Key")
    protected ItemList key;
    @XmlElement(name="Data")
    protected ItemList data;
    @XmlElement(name="Extension")
    protected MessageExtension extension;
    @XmlAttribute(name="UtcTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar utcTime;
    @XmlAttribute(name="PropertyOperation")
    protected PropertyOperation propertyOperation;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ItemList getSource() {
        return this.source;
    }

    public void setSource(ItemList value) {
        this.source = value;
    }

    public ItemList getKey() {
        return this.key;
    }

    public void setKey(ItemList value) {
        this.key = value;
    }

    public ItemList getData() {
        return this.data;
    }

    public void setData(ItemList value) {
        this.data = value;
    }

    public MessageExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MessageExtension value) {
        this.extension = value;
    }

    public XMLGregorianCalendar getUtcTime() {
        return this.utcTime;
    }

    public void setUtcTime(XMLGregorianCalendar value) {
        this.utcTime = value;
    }

    public PropertyOperation getPropertyOperation() {
        return this.propertyOperation;
    }

    public void setPropertyOperation(PropertyOperation value) {
        this.propertyOperation = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

