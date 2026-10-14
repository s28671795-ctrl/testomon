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
import org.onvif.ver10.schema.TrackType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrackInformation", propOrder={"trackToken", "trackType", "description", "dataFrom", "dataTo", "any"})
public class TrackInformation {
    @XmlElement(name="TrackToken", required=true)
    protected String trackToken;
    @XmlElement(name="TrackType", required=true)
    @XmlSchemaType(name="string")
    protected TrackType trackType;
    @XmlElement(name="Description", required=true)
    protected String description;
    @XmlElement(name="DataFrom", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar dataFrom;
    @XmlElement(name="DataTo", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar dataTo;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getTrackToken() {
        return this.trackToken;
    }

    public void setTrackToken(String value) {
        this.trackToken = value;
    }

    public TrackType getTrackType() {
        return this.trackType;
    }

    public void setTrackType(TrackType value) {
        this.trackType = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public XMLGregorianCalendar getDataFrom() {
        return this.dataFrom;
    }

    public void setDataFrom(XMLGregorianCalendar value) {
        this.dataFrom = value;
    }

    public XMLGregorianCalendar getDataTo() {
        return this.dataTo;
    }

    public void setDataTo(XMLGregorianCalendar value) {
        this.dataTo = value;
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

