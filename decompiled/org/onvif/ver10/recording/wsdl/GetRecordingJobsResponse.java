/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.GetRecordingJobsResponseItem;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"jobItem"})
@XmlRootElement(name="GetRecordingJobsResponse")
public class GetRecordingJobsResponse {
    @XmlElement(name="JobItem")
    protected List<GetRecordingJobsResponseItem> jobItem;

    public List<GetRecordingJobsResponseItem> getJobItem() {
        if (this.jobItem == null) {
            this.jobItem = new ArrayList<GetRecordingJobsResponseItem>();
        }
        return this.jobItem;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

