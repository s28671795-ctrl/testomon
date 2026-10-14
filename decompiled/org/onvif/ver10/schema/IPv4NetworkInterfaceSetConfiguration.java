/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PrefixedIPv4Address;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPv4NetworkInterfaceSetConfiguration", propOrder={"enabled", "manual", "dhcp"})
public class IPv4NetworkInterfaceSetConfiguration {
    @XmlElement(name="Enabled")
    protected Boolean enabled;
    @XmlElement(name="Manual")
    protected List<PrefixedIPv4Address> manual;
    @XmlElement(name="DHCP")
    protected Boolean dhcp;

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public List<PrefixedIPv4Address> getManual() {
        if (this.manual == null) {
            this.manual = new ArrayList<PrefixedIPv4Address>();
        }
        return this.manual;
    }

    public Boolean getDHCP() {
        return this.dhcp;
    }

    public void setDHCP(Boolean value) {
        this.dhcp = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

