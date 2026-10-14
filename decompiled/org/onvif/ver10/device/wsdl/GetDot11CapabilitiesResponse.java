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
import org.onvif.ver10.schema.Dot11Capabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"capabilities"})
@XmlRootElement(name="GetDot11CapabilitiesResponse")
public class GetDot11CapabilitiesResponse {
    @XmlElement(name="Capabilities", required=true)
    protected Dot11Capabilities capabilities;

    public Dot11Capabilities getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(Dot11Capabilities value) {
        this.capabilities = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

