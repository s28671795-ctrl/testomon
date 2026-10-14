/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.SerialPort;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"serialPort"})
@XmlRootElement(name="GetSerialPortsResponse")
public class GetSerialPortsResponse {
    @XmlElement(name="SerialPort")
    protected List<SerialPort> serialPort;

    public List<SerialPort> getSerialPort() {
        if (this.serialPort == null) {
            this.serialPort = new ArrayList<SerialPort>();
        }
        return this.serialPort;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

