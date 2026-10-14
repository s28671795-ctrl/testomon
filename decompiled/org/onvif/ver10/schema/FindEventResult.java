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
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FindEventResult", propOrder={"recordingToken", "trackToken", "time", "event", "startStateEvent", "any"})
public class FindEventResult {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="TrackToken", required=true)
    protected String trackToken;
    @XmlElement(name="Time", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar time;
    @XmlElement(name="Event", required=true)
    protected NotificationMessageHolderType event;
    @XmlElement(name="StartStateEvent")
    protected boolean startStateEvent;
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

    public String getTrackToken() {
        return this.trackToken;
    }

    public void setTrackToken(String value) {
        this.trackToken = value;
    }

    public XMLGregorianCalendar getTime() {
        return this.time;
    }

    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    public NotificationMessageHolderType getEvent() {
        return this.event;
    }

    public void setEvent(NotificationMessageHolderType value) {
        this.event = value;
    }

    public boolean getStartStateEvent() {
        return this.startStateEvent;
    }

    public void setStartStateEvent(boolean value) {
        this.startStateEvent = value;
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

