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
import org.onvif.ver10.schema.RecordingJobStateInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"state"})
@XmlRootElement(name="GetRecordingJobStateResponse")
public class GetRecordingJobStateResponse {
    @XmlElement(name="State", required=true)
    protected RecordingJobStateInformation state;

    public RecordingJobStateInformation getState() {
        return this.state;
    }

    public void setState(RecordingJobStateInformation value) {
        this.state = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

