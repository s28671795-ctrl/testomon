/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.ServiceCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"capabilities"})
@XmlRootElement(name="GetServiceCapabilitiesResponse")
public class GetServiceCapabilitiesResponse {
    @XmlElement(name="Capabilities", required=true)
    protected ServiceCapabilities capabilities;

    public ServiceCapabilities getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(ServiceCapabilities value) {
        this.capabilities = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

