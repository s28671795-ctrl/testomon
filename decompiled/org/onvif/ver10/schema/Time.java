/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Time", propOrder={"hour", "minute", "second"})
public class Time {
    @XmlElement(name="Hour")
    protected int hour;
    @XmlElement(name="Minute")
    protected int minute;
    @XmlElement(name="Second")
    protected int second;

    public int getHour() {
        return this.hour;
    }

    public void setHour(int value) {
        this.hour = value;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int value) {
        this.minute = value;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int value) {
        this.second = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

