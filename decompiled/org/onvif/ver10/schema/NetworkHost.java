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
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkHostExtension;
import org.onvif.ver10.schema.NetworkHostType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkHost", propOrder={"type", "iPv4Address", "iPv6Address", "dnSname", "extension"})
public class NetworkHost {
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected NetworkHostType type;
    @XmlElement(name="IPv4Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String iPv4Address;
    @XmlElement(name="IPv6Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String iPv6Address;
    @XmlElement(name="DNSname")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String dnSname;
    @XmlElement(name="Extension")
    protected NetworkHostExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public NetworkHostType getType() {
        return this.type;
    }

    public void setType(NetworkHostType value) {
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

    public String getDNSname() {
        return this.dnSname;
    }

    public void setDNSname(String value) {
        this.dnSname = value;
    }

    public NetworkHostExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkHostExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

