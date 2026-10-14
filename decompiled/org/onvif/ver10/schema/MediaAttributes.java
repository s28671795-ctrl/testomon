/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.TrackAttributes;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MediaAttributes", propOrder={"recordingToken", "trackAttributes", "from", "until", "any"})
public class MediaAttributes {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="TrackAttributes")
    protected List<TrackAttributes> trackAttributes;
    @XmlElement(name="From", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar from;
    @XmlElement(name="Until", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar until;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getRecordingToken() {
        return this.recordingToken;
    }

    public void setRecordingToken(String value) {
        this.recordingToken = value;
    }

    public List<TrackAttributes> getTrackAttributes() {
        if (this.trackAttributes == null) {
            this.trackAttributes = new ArrayList<TrackAttributes>();
        }
        return this.trackAttributes;
    }

    public XMLGregorianCalendar getFrom() {
        return this.from;
    }

    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    public XMLGregorianCalendar getUntil() {
        return this.until;
    }

    public void setUntil(XMLGregorianCalendar value) {
        this.until = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

