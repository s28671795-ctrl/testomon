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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingJobStateInformationExtension;
import org.onvif.ver10.schema.RecordingJobStateSource;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingJobStateInformation", propOrder={"recordingToken", "state", "sources", "extension"})
public class RecordingJobStateInformation {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="State", required=true)
    protected String state;
    @XmlElement(name="Sources")
    protected List<RecordingJobStateSource> sources;
    @XmlElement(name="Extension")
    protected RecordingJobStateInformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getRecordingToken() {
        return this.recordingToken;
    }

    public void setRecordingToken(String value) {
        this.recordingToken = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public List<RecordingJobStateSource> getSources() {
        if (this.sources == null) {
            this.sources = new ArrayList<RecordingJobStateSource>();
        }
        return this.sources;
    }

    public RecordingJobStateInformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RecordingJobStateInformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

