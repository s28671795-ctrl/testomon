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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigDescription;
import org.onvif.ver10.schema.ItemListDescription;
import org.onvif.ver10.schema.MessageDescriptionExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MessageDescription", propOrder={"source", "key", "data", "extension"})
@XmlSeeAlso(value={ConfigDescription.Messages.class})
public class MessageDescription {
    @XmlElement(name="Source")
    protected ItemListDescription source;
    @XmlElement(name="Key")
    protected ItemListDescription key;
    @XmlElement(name="Data")
    protected ItemListDescription data;
    @XmlElement(name="Extension")
    protected MessageDescriptionExtension extension;
    @XmlAttribute(name="IsProperty")
    protected Boolean isProperty;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ItemListDescription getSource() {
        return this.source;
    }

    public void setSource(ItemListDescription value) {
        this.source = value;
    }

    public ItemListDescription getKey() {
        return this.key;
    }

    public void setKey(ItemListDescription value) {
        this.key = value;
    }

    public ItemListDescription getData() {
        return this.data;
    }

    public void setData(ItemListDescription value) {
        this.data = value;
    }

    public MessageDescriptionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MessageDescriptionExtension value) {
        this.extension = value;
    }

    public Boolean getIsProperty() {
        return this.isProperty;
    }

    public void setIsProperty(Boolean value) {
        this.isProperty = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

