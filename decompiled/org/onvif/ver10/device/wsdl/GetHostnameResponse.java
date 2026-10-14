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
import org.onvif.ver10.schema.HostnameInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"hostnameInformation"})
@XmlRootElement(name="GetHostnameResponse")
public class GetHostnameResponse {
    @XmlElement(name="HostnameInformation", required=true)
    protected HostnameInformation hostnameInformation;

    public HostnameInformation getHostnameInformation() {
        return this.hostnameInformation;
    }

    public void setHostnameInformation(HostnameInformation value) {
        this.hostnameInformation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

