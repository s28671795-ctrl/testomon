/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.replay.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.StreamSetup;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"streamSetup", "recordingToken"})
@XmlRootElement(name="GetReplayUri")
public class GetReplayUri {
    @XmlElement(name="StreamSetup", required=true)
    protected StreamSetup streamSetup;
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;

    public StreamSetup getStreamSetup() {
        return this.streamSetup;
    }

    public void setStreamSetup(StreamSetup value) {
        this.streamSetup = value;
    }

    public String getRecordingToken() {
        return this.recordingToken;
    }

    public void setRecordingToken(String value) {
        this.recordingToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

