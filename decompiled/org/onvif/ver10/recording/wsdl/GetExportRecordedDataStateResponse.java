/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ArrayOfFileProgress;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"progress", "fileProgressStatus", "any"})
@XmlRootElement(name="GetExportRecordedDataStateResponse")
public class GetExportRecordedDataStateResponse {
    @XmlElement(name="Progress")
    protected float progress;
    @XmlElement(name="FileProgressStatus", required=true)
    protected ArrayOfFileProgress fileProgressStatus;
    @XmlAnyElement(lax=true)
    protected Object any;

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float value) {
        this.progress = value;
    }

    public ArrayOfFileProgress getFileProgressStatus() {
        return this.fileProgressStatus;
    }

    public void setFileProgressStatus(ArrayOfFileProgress value) {
        this.fileProgressStatus = value;
    }

    public Object getAny() {
        return this.any;
    }

    public void setAny(Object value) {
        this.any = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

