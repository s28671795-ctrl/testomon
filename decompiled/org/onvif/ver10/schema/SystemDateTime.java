/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DateTime;
import org.onvif.ver10.schema.SetDateTimeType;
import org.onvif.ver10.schema.SystemDateTimeExtension;
import org.onvif.ver10.schema.TimeZone;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemDateTime", propOrder={"dateTimeType", "daylightSavings", "timeZone", "utcDateTime", "localDateTime", "extension"})
public class SystemDateTime {
    @XmlElement(name="DateTimeType", required=true)
    @XmlSchemaType(name="string")
    protected SetDateTimeType dateTimeType;
    @XmlElement(name="DaylightSavings")
    protected boolean daylightSavings;
    @XmlElement(name="TimeZone")
    protected TimeZone timeZone;
    @XmlElement(name="UTCDateTime")
    protected DateTime utcDateTime;
    @XmlElement(name="LocalDateTime")
    protected DateTime localDateTime;
    @XmlElement(name="Extension")
    protected SystemDateTimeExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public SetDateTimeType getDateTimeType() {
        return this.dateTimeType;
    }

    public void setDateTimeType(SetDateTimeType value) {
        this.dateTimeType = value;
    }

    public boolean getDaylightSavings() {
        return this.daylightSavings;
    }

    public void setDaylightSavings(boolean value) {
        this.daylightSavings = value;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone value) {
        this.timeZone = value;
    }

    public DateTime getUTCDateTime() {
        return this.utcDateTime;
    }

    public void setUTCDateTime(DateTime value) {
        this.utcDateTime = value;
    }

    public DateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public void setLocalDateTime(DateTime value) {
        this.localDateTime = value;
    }

    public SystemDateTimeExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SystemDateTimeExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

