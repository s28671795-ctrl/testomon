/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"recordingConfiguration"})
@XmlRootElement(name="CreateRecording")
public class CreateRecording {
    @XmlElement(name="RecordingConfiguration", required=true)
    protected RecordingConfiguration recordingConfiguration;

    public RecordingConfiguration getRecordingConfiguration() {
        return this.recordingConfiguration;
    }

    public void setRecordingConfiguration(RecordingConfiguration value) {
        this.recordingConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

