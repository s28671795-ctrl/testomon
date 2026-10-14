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
import org.onvif.ver10.schema.RecordingSourceInformation;
import org.onvif.ver10.schema.RecordingStatus;
import org.onvif.ver10.schema.TrackInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingInformation", propOrder={"recordingToken", "source", "earliestRecording", "latestRecording", "content", "track", "recordingStatus", "any"})
public class RecordingInformation {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="Source", required=true)
    protected RecordingSourceInformation source;
    @XmlElement(name="EarliestRecording")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar earliestRecording;
    @XmlElement(name="LatestRecording")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar latestRecording;
    @XmlElement(name="Content", required=true)
    protected String content;
    @XmlElement(name="Track")
    protected List<TrackInformation> track;
    @XmlElement(name="RecordingStatus", required=true)
    @XmlSchemaType(name="string")
    protected RecordingStatus recordingStatus;
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

    public RecordingSourceInformation getSource() {
        return this.source;
    }

    public void setSource(RecordingSourceInformation value) {
        this.source = value;
    }

    public XMLGregorianCalendar getEarliestRecording() {
        return this.earliestRecording;
    }

    public void setEarliestRecording(XMLGregorianCalendar value) {
        this.earliestRecording = value;
    }

    public XMLGregorianCalendar getLatestRecording() {
        return this.latestRecording;
    }

    public void setLatestRecording(XMLGregorianCalendar value) {
        this.latestRecording = value;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public List<TrackInformation> getTrack() {
        if (this.track == null) {
            this.track = new ArrayList<TrackInformation>();
        }
        return this.track;
    }

    public RecordingStatus getRecordingStatus() {
        return this.recordingStatus;
    }

    public void setRecordingStatus(RecordingStatus value) {
        this.recordingStatus = value;
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

