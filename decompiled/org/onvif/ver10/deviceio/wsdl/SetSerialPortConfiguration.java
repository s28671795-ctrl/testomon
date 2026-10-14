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
@XmlType(name="", propOrder={"serialPortConfiguration", "forcePersistance"})
@XmlRootElement(name="SetSerialPortConfiguration")
public class SetSerialPortConfiguration {
    @XmlElement(name="SerialPortConfiguration", required=true)
    protected SerialPortConfiguration serialPortConfiguration;
    @XmlElement(name="ForcePersistance")
    protected boolean forcePersistance;

    public SerialPortConfiguration getSerialPortConfiguration() {
        return this.serialPortConfiguration;
    }

    public void setSerialPortConfiguration(SerialPortConfiguration value) {
        this.serialPortConfiguration = value;
    }

    public boolean getForcePersistance() {
        return this.forcePersistance;
    }

    public void setForcePersistance(boolean value) {
        this.forcePersistance = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

