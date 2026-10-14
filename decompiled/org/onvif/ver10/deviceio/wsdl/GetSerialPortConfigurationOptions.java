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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"serialPortToken"})
@XmlRootElement(name="GetSerialPortConfigurationOptions")
public class GetSerialPortConfigurationOptions {
    @XmlElement(name="SerialPortToken", required=true)
    protected String serialPortToken;

    public String getSerialPortToken() {
        return this.serialPortToken;
    }

    public void setSerialPortToken(String value) {
        this.serialPortToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

