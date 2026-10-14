/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.SerialPortConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"serialPortConfiguration"})
@XmlRootElement(name="GetSerialPortConfigurationResponse")
public class GetSerialPortConfigurationResponse {
    @XmlElement(name="SerialPortConfiguration", required=true)
    protected SerialPortConfiguration serialPortConfiguration;

    public SerialPortConfiguration getSerialPortConfiguration() {
        return this.serialPortConfiguration;
    }

    public void setSerialPortConfiguration(SerialPortConfiguration value) {
        this.serialPortConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

