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
import org.onvif.ver10.schema.RecordingJobConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"jobConfiguration"})
@XmlRootElement(name="SetRecordingJobConfigurationResponse")
public class SetRecordingJobConfigurationResponse {
    @XmlElement(name="JobConfiguration", required=true)
    protected RecordingJobConfiguration jobConfiguration;

    public RecordingJobConfiguration getJobConfiguration() {
        return this.jobConfiguration;
    }

    public void setJobConfiguration(RecordingJobConfiguration value) {
        this.jobConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

