/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkInterfaceConnectionSetting;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceLink", propOrder={"adminSettings", "operSettings", "interfaceType"})
public class NetworkInterfaceLink {
    @XmlElement(name="AdminSettings", required=true)
    protected NetworkInterfaceConnectionSetting adminSettings;
    @XmlElement(name="OperSettings", required=true)
    protected NetworkInterfaceConnectionSetting operSettings;
    @XmlElement(name="InterfaceType")
    protected int interfaceType;

    public NetworkInterfaceConnectionSetting getAdminSettings() {
        return this.adminSettings;
    }

    public void setAdminSettings(NetworkInterfaceConnectionSetting value) {
        this.adminSettings = value;
    }

    public NetworkInterfaceConnectionSetting getOperSettings() {
        return this.operSettings;
    }

    public void setOperSettings(NetworkInterfaceConnectionSetting value) {
        this.operSettings = value;
    }

    public int getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceType(int value) {
        this.interfaceType = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

