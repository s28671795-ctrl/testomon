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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPv4NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.IPv6NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.NetworkInterfaceConnectionSetting;
import org.onvif.ver10.schema.NetworkInterfaceSetConfigurationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceSetConfiguration", propOrder={"enabled", "link", "mtu", "iPv4", "iPv6", "extension"})
public class NetworkInterfaceSetConfiguration {
    @XmlElement(name="Enabled")
    protected Boolean enabled;
    @XmlElement(name="Link")
    protected NetworkInterfaceConnectionSetting link;
    @XmlElement(name="MTU")
    protected Integer mtu;
    @XmlElement(name="IPv4")
    protected IPv4NetworkInterfaceSetConfiguration iPv4;
    @XmlElement(name="IPv6")
    protected IPv6NetworkInterfaceSetConfiguration iPv6;
    @XmlElement(name="Extension")
    protected NetworkInterfaceSetConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public NetworkInterfaceConnectionSetting getLink() {
        return this.link;
    }

    public void setLink(NetworkInterfaceConnectionSetting value) {
        this.link = value;
    }

    public Integer getMTU() {
        return this.mtu;
    }

    public void setMTU(Integer value) {
        this.mtu = value;
    }

    public IPv4NetworkInterfaceSetConfiguration getIPv4() {
        return this.iPv4;
    }

    public void setIPv4(IPv4NetworkInterfaceSetConfiguration value) {
        this.iPv4 = value;
    }

    public IPv6NetworkInterfaceSetConfiguration getIPv6() {
        return this.iPv6;
    }

    public void setIPv6(IPv6NetworkInterfaceSetConfiguration value) {
        this.iPv6 = value;
    }

    public NetworkInterfaceSetConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkInterfaceSetConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

