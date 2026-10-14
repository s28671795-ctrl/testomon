/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"any"})
public class Capabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="DynamicRecordings")
    protected Boolean dynamicRecordings;
    @XmlAttribute(name="DynamicTracks")
    protected Boolean dynamicTracks;
    @XmlAttribute(name="Encoding")
    protected List<String> encoding;
    @XmlAttribute(name="MaxRate")
    protected Float maxRate;
    @XmlAttribute(name="MaxTotalRate")
    protected Float maxTotalRate;
    @XmlAttribute(name="MaxRecordings")
    protected Float maxRecordings;
    @XmlAttribute(name="MaxRecordingJobs")
    protected Integer maxRecordingJobs;
    @XmlAttribute(name="Options")
    protected Boolean options;
    @XmlAttribute(name="MetadataRecording")
    protected Boolean metadataRecording;
    @XmlAttribute(name="SupportedExportFileFormats")
    protected List<String> supportedExportFileFormats;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getDynamicRecordings() {
        return this.dynamicRecordings;
    }

    public void setDynamicRecordings(Boolean value) {
        this.dynamicRecordings = value;
    }

    public Boolean getDynamicTracks() {
        return this.dynamicTracks;
    }

    public void setDynamicTracks(Boolean value) {
        this.dynamicTracks = value;
    }

    public List<String> getEncoding() {
        if (this.encoding == null) {
            this.encoding = new ArrayList<String>();
        }
        return this.encoding;
    }

    public Float getMaxRate() {
        return this.maxRate;
    }

    public void setMaxRate(Float value) {
        this.maxRate = value;
    }

    public Float getMaxTotalRate() {
        return this.maxTotalRate;
    }

    public void setMaxTotalRate(Float value) {
        this.maxTotalRate = value;
    }

    public Float getMaxRecordings() {
        return this.maxRecordings;
    }

    public void setMaxRecordings(Float value) {
        this.maxRecordings = value;
    }

    public Integer getMaxRecordingJobs() {
        return this.maxRecordingJobs;
    }

    public void setMaxRecordingJobs(Integer value) {
        this.maxRecordingJobs = value;
    }

    public Boolean getOptions() {
        return this.options;
    }

    public void setOptions(Boolean value) {
        this.options = value;
    }

    public Boolean getMetadataRecording() {
        return this.metadataRecording;
    }

    public void setMetadataRecording(Boolean value) {
        this.metadataRecording = value;
    }

    public List<String> getSupportedExportFileFormats() {
        if (this.supportedExportFileFormats == null) {
            this.supportedExportFileFormats = new ArrayList<String>();
        }
        return this.supportedExportFileFormats;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

