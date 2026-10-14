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
import org.onvif.ver10.schema.AttachmentData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"firmware"})
@XmlRootElement(name="UpgradeSystemFirmware")
public class UpgradeSystemFirmware {
    @XmlElement(name="Firmware", required=true)
    protected AttachmentData firmware;

    public AttachmentData getFirmware() {
        return this.firmware;
    }

    public void setFirmware(AttachmentData value) {
        this.firmware = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

