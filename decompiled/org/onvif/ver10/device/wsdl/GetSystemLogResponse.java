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
import org.onvif.ver10.schema.SystemLog;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"systemLog"})
@XmlRootElement(name="GetSystemLogResponse")
public class GetSystemLogResponse {
    @XmlElement(name="SystemLog", required=true)
    protected SystemLog systemLog;

    public SystemLog getSystemLog() {
        return this.systemLog;
    }

    public void setSystemLog(SystemLog value) {
        this.systemLog = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

