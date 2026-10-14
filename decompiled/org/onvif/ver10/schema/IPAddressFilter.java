/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPAddressFilterExtension;
import org.onvif.ver10.schema.IPAddressFilterType;
import org.onvif.ver10.schema.PrefixedIPv4Address;
import org.onvif.ver10.schema.PrefixedIPv6Address;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPAddressFilter", propOrder={"type", "iPv4Address", "iPv6Address", "extension"})
public class IPAddressFilter {
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected IPAddressFilterType type;
    @XmlElement(name="IPv4Address")
    protected List<PrefixedIPv4Address> iPv4Address;
    @XmlElement(name="IPv6Address")
    protected List<PrefixedIPv6Address> iPv6Address;
    @XmlElement(name="Extension")
    protected IPAddressFilterExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IPAddressFilterType getType() {
        return this.type;
    }

    public void setType(IPAddressFilterType value) {
        this.type = value;
    }

    public List<PrefixedIPv4Address> getIPv4Address() {
        if (this.iPv4Address == null) {
            this.iPv4Address = new ArrayList<PrefixedIPv4Address>();
        }
        return this.iPv4Address;
    }

    public List<PrefixedIPv6Address> getIPv6Address() {
        if (this.iPv6Address == null) {
            this.iPv6Address = new ArrayList<PrefixedIPv6Address>();
        }
        return this.iPv6Address;
    }

    public IPAddressFilterExtension getExtension() {
        return this.extension;
    }

    public void setExtension(IPAddressFilterExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

