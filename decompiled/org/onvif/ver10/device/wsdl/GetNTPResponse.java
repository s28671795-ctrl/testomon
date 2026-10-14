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
import org.onvif.ver10.schema.NTPInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ntpInformation"})
@XmlRootElement(name="GetNTPResponse")
public class GetNTPResponse {
    @XmlElement(name="NTPInformation", required=true)
    protected NTPInformation ntpInformation;

    public NTPInformation getNTPInformation() {
        return this.ntpInformation;
    }

    public void setNTPInformation(NTPInformation value) {
        this.ntpInformation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

