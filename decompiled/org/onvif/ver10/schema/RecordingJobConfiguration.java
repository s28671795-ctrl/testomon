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
import org.onvif.ver10.schema.RecordingJobConfigurationExtension;
import org.onvif.ver10.schema.RecordingJobSource;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingJobConfiguration", propOrder={"recordingToken", "mode", "priority", "source", "extension"})
public class RecordingJobConfiguration {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="Mode", required=true)
    protected String mode;
    @XmlElement(name="Priority")
    protected int priority;
    @XmlElement(name="Source")
    protected List<RecordingJobSource> source;
    @XmlElement(name="Extension")
    protected RecordingJobConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getRecordingToken() {
        return this.recordingToken;
    }

    public void setRecordingToken(String value) {
        this.recordingToken = value;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String value) {
        this.mode = value;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int value) {
        this.priority = value;
    }

    public List<RecordingJobSource> getSource() {
        if (this.source == null) {
            this.source = new ArrayList<RecordingJobSource>();
        }
        return this.source;
    }

    public RecordingJobConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RecordingJobConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

