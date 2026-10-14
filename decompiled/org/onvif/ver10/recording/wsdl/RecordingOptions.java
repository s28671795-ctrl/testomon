/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.recording.wsdl.JobOptions;
import org.onvif.ver10.recording.wsdl.TrackOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingOptions", propOrder={"job", "track", "any"})
public class RecordingOptions {
    @XmlElement(name="Job", required=true)
    protected JobOptions job;
    @XmlElement(name="Track", required=true)
    protected TrackOptions track;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public JobOptions getJob() {
        return this.job;
    }

    public void setJob(JobOptions value) {
        this.job = value;
    }

    public TrackOptions getTrack() {
        return this.track;
    }

    public void setTrack(TrackOptions value) {
        this.track = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

