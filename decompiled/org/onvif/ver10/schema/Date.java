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
@XmlType(name="Date", propOrder={"year", "month", "day"})
public class Date {
    @XmlElement(name="Year")
    protected int year;
    @XmlElement(name="Month")
    protected int month;
    @XmlElement(name="Day")
    protected int day;

    public int getYear() {
        return this.year;
    }

    public void setYear(int value) {
        this.year = value;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int value) {
        this.month = value;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int value) {
        this.day = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

