/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPAddress", propOrder={"type", "iPv4Address", "iPv6Address"})
public class IPAddress {
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected IPType type;
    @XmlElement(name="IPv4Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String iPv4Address;
    @XmlElement(name="IPv6Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String iPv6Address;

    public IPType getType() {
        return this.type;
    }

    public void setType(IPType value) {
        this.type = value;
    }

    public String getIPv4Address() {
        return this.iPv4Address;
    }

    public void setIPv4Address(String value) {
        this.iPv4Address = value;
    }

    public String getIPv6Address() {
        return this.iPv6Address;
    }

    public void setIPv6Address(String value) {
        this.iPv6Address = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

