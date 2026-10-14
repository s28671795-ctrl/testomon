/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DynamicDNSInformationExtension;
import org.onvif.ver10.schema.DynamicDNSType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DynamicDNSInformation", propOrder={"type", "name", "ttl", "extension"})
public class DynamicDNSInformation {
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected DynamicDNSType type;
    @XmlElement(name="Name")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String name;
    @XmlElement(name="TTL")
    protected Duration ttl;
    @XmlElement(name="Extension")
    protected DynamicDNSInformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public DynamicDNSType getType() {
        return this.type;
    }

    public void setType(DynamicDNSType value) {
        this.type = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Duration getTTL() {
        return this.ttl;
    }

    public void setTTL(Duration value) {
        this.ttl = value;
    }

    public DynamicDNSInformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(DynamicDNSInformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

