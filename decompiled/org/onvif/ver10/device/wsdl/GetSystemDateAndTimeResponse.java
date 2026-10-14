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
import org.onvif.ver10.schema.SystemDateTime;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"systemDateAndTime"})
@XmlRootElement(name="GetSystemDateAndTimeResponse")
public class GetSystemDateAndTimeResponse {
    @XmlElement(name="SystemDateAndTime", required=true)
    protected SystemDateTime systemDateAndTime;

    public SystemDateTime getSystemDateAndTime() {
        return this.systemDateAndTime;
    }

    public void setSystemDateAndTime(SystemDateTime value) {
        this.systemDateAndTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

