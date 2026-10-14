/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.AccessDoorExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"token", "useExtendedTime", "accessTime", "openTooLongTime", "preAlarmTime", "extension"})
@XmlRootElement(name="AccessDoor")
public class AccessDoor {
    @XmlElement(name="Token", required=true)
    protected String token;
    @XmlElement(name="UseExtendedTime")
    protected Boolean useExtendedTime;
    @XmlElement(name="AccessTime")
    protected Duration accessTime;
    @XmlElement(name="OpenTooLongTime")
    protected Duration openTooLongTime;
    @XmlElement(name="PreAlarmTime")
    protected Duration preAlarmTime;
    @XmlElement(name="Extension")
    protected AccessDoorExtension extension;

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Boolean getUseExtendedTime() {
        return this.useExtendedTime;
    }

    public void setUseExtendedTime(Boolean value) {
        this.useExtendedTime = value;
    }

    public Duration getAccessTime() {
        return this.accessTime;
    }

    public void setAccessTime(Duration value) {
        this.accessTime = value;
    }

    public Duration getOpenTooLongTime() {
        return this.openTooLongTime;
    }

    public void setOpenTooLongTime(Duration value) {
        this.openTooLongTime = value;
    }

    public Duration getPreAlarmTime() {
        return this.preAlarmTime;
    }

    public void setPreAlarmTime(Duration value) {
        this.preAlarmTime = value;
    }

    public AccessDoorExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AccessDoorExtension value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

