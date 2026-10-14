/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.device.wsdl.MiscCapabilities;
import org.onvif.ver10.device.wsdl.NetworkCapabilities;
import org.onvif.ver10.device.wsdl.SecurityCapabilities;
import org.onvif.ver10.device.wsdl.SystemCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DeviceServiceCapabilities", propOrder={"network", "security", "system", "misc"})
public class DeviceServiceCapabilities {
    @XmlElement(name="Network", required=true)
    protected NetworkCapabilities network;
    @XmlElement(name="Security", required=true)
    protected SecurityCapabilities security;
    @XmlElement(name="System", required=true)
    protected SystemCapabilities system;
    @XmlElement(name="Misc")
    protected MiscCapabilities misc;

    public NetworkCapabilities getNetwork() {
        return this.network;
    }

    public void setNetwork(NetworkCapabilities value) {
        this.network = value;
    }

    public SecurityCapabilities getSecurity() {
        return this.security;
    }

    public void setSecurity(SecurityCapabilities value) {
        this.security = value;
    }

    public SystemCapabilities getSystem() {
        return this.system;
    }

    public void setSystem(SystemCapabilities value) {
        this.system = value;
    }

    public MiscCapabilities getMisc() {
        return this.misc;
    }

    public void setMisc(MiscCapabilities value) {
        this.misc = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

