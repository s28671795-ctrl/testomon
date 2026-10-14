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
@XmlType(name="", propOrder={"jobToken", "jobConfiguration"})
@XmlRootElement(name="SetRecordingJobConfiguration")
public class SetRecordingJobConfiguration {
    @XmlElement(name="JobToken", required=true)
    protected String jobToken;
    @XmlElement(name="JobConfiguration", required=true)
    protected RecordingJobConfiguration jobConfiguration;

    public String getJobToken() {
        return this.jobToken;
    }

    public void setJobToken(String value) {
        this.jobToken = value;
    }

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

