/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPv6DHCPConfiguration;
import org.onvif.ver10.schema.PrefixedIPv6Address;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPv6NetworkInterfaceSetConfiguration", propOrder={"enabled", "acceptRouterAdvert", "manual", "dhcp"})
public class IPv6NetworkInterfaceSetConfiguration {
    @XmlElement(name="Enabled")
    protected Boolean enabled;
    @XmlElement(name="AcceptRouterAdvert")
    protected Boolean acceptRouterAdvert;
    @XmlElement(name="Manual")
    protected List<PrefixedIPv6Address> manual;
    @XmlElement(name="DHCP")
    @XmlSchemaType(name="string")
    protected IPv6DHCPConfiguration dhcp;

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public Boolean getAcceptRouterAdvert() {
        return this.acceptRouterAdvert;
    }

    public void setAcceptRouterAdvert(Boolean value) {
        this.acceptRouterAdvert = value;
    }

    public List<PrefixedIPv6Address> getManual() {
        if (this.manual == null) {
            this.manual = new ArrayList<PrefixedIPv6Address>();
        }
        return this.manual;
    }

    public IPv6DHCPConfiguration getDHCP() {
        return this.dhcp;
    }

    public void setDHCP(IPv6DHCPConfiguration value) {
        this.dhcp = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

