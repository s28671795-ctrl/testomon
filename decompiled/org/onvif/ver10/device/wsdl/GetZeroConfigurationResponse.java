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
import org.onvif.ver10.schema.NetworkZeroConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"zeroConfiguration"})
@XmlRootElement(name="GetZeroConfigurationResponse")
public class GetZeroConfigurationResponse {
    @XmlElement(name="ZeroConfiguration", required=true)
    protected NetworkZeroConfiguration zeroConfiguration;

    public NetworkZeroConfiguration getZeroConfiguration() {
        return this.zeroConfiguration;
    }

    public void setZeroConfiguration(NetworkZeroConfiguration value) {
        this.zeroConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

