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
import org.onvif.ver10.schema.DeviceEntity;
import org.onvif.ver10.schema.IPv4NetworkInterface;
import org.onvif.ver10.schema.IPv6NetworkInterface;
import org.onvif.ver10.schema.NetworkInterfaceExtension;
import org.onvif.ver10.schema.NetworkInterfaceInfo;
import org.onvif.ver10.schema.NetworkInterfaceLink;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterface", propOrder={"enabled", "info", "link", "iPv4", "iPv6", "extension"})
public class NetworkInterface
extends DeviceEntity {
    @XmlElement(name="Enabled")
    protected boolean enabled;
    @XmlElement(name="Info")
    protected NetworkInterfaceInfo info;
    @XmlElement(name="Link")
    protected NetworkInterfaceLink link;
    @XmlElement(name="IPv4")
    protected IPv4NetworkInterface iPv4;
    @XmlElement(name="IPv6")
    protected IPv6NetworkInterface iPv6;
    @XmlElement(name="Extension")
    protected NetworkInterfaceExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public NetworkInterfaceInfo getInfo() {
        return this.info;
    }

    public void setInfo(NetworkInterfaceInfo value) {
        this.info = value;
    }

    public NetworkInterfaceLink getLink() {
        return this.link;
    }

    public void setLink(NetworkInterfaceLink value) {
        this.link = value;
    }

    public IPv4NetworkInterface getIPv4() {
        return this.iPv4;
    }

    public void setIPv4(IPv4NetworkInterface value) {
        this.iPv4 = value;
    }

    public IPv6NetworkInterface getIPv6() {
        return this.iPv6;
    }

    public void setIPv6(IPv6NetworkInterface value) {
        this.iPv6 = value;
    }

    public NetworkInterfaceExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkInterfaceExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

