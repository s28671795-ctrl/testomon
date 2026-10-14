/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkInterfaceSetConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"interfaceToken", "networkInterface"})
@XmlRootElement(name="SetNetworkInterfaces")
public class SetNetworkInterfaces {
    @XmlElement(name="InterfaceToken", required=true)
    protected String interfaceToken;
    @XmlElement(name="NetworkInterface", required=true)
    protected NetworkInterfaceSetConfiguration networkInterface;

    public String getInterfaceToken() {
        return this.interfaceToken;
    }

    public void setInterfaceToken(String value) {
        this.interfaceToken = value;
    }

    public NetworkInterfaceSetConfiguration getNetworkInterface() {
        return this.networkInterface;
    }

    public void setNetworkInterface(NetworkInterfaceSetConfiguration value) {
        this.networkInterface = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

