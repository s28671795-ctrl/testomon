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
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DeviceCapabilitiesExtension;
import org.onvif.ver10.schema.IOCapabilities;
import org.onvif.ver10.schema.NetworkCapabilities;
import org.onvif.ver10.schema.SecurityCapabilities;
import org.onvif.ver10.schema.SystemCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DeviceCapabilities", propOrder={"xAddr", "network", "system", "io", "security", "extension"})
public class DeviceCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="Network")
    protected NetworkCapabilities network;
    @XmlElement(name="System")
    protected SystemCapabilities system;
    @XmlElement(name="IO")
    protected IOCapabilities io;
    @XmlElement(name="Security")
    protected SecurityCapabilities security;
    @XmlElement(name="Extension")
    protected DeviceCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public NetworkCapabilities getNetwork() {
        return this.network;
    }

    public void setNetwork(NetworkCapabilities value) {
        this.network = value;
    }

    public SystemCapabilities getSystem() {
        return this.system;
    }

    public void setSystem(SystemCapabilities value) {
        this.system = value;
    }

    public IOCapabilities getIO() {
        return this.io;
    }

    public void setIO(IOCapabilities value) {
        this.io = value;
    }

    public SecurityCapabilities getSecurity() {
        return this.security;
    }

    public void setSecurity(SecurityCapabilities value) {
        this.security = value;
    }

    public DeviceCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(DeviceCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

