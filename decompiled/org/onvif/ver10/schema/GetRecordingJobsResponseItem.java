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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingJobConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="GetRecordingJobsResponseItem", propOrder={"jobToken", "jobConfiguration", "any"})
public class GetRecordingJobsResponseItem {
    @XmlElement(name="JobToken", required=true)
    protected String jobToken;
    @XmlElement(name="JobConfiguration", required=true)
    protected RecordingJobConfiguration jobConfiguration;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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

