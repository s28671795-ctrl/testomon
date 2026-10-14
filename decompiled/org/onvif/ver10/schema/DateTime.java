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
import org.onvif.ver10.schema.Date;
import org.onvif.ver10.schema.Time;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DateTime", propOrder={"time", "date"})
public class DateTime {
    @XmlElement(name="Time", required=true)
    protected Time time;
    @XmlElement(name="Date", required=true)
    protected Date date;

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time value) {
        this.time = value;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date value) {
        this.date = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

