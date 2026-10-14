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
import org.onvif.ver10.recording.wsdl.RecordingOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"options"})
@XmlRootElement(name="GetRecordingOptionsResponse")
public class GetRecordingOptionsResponse {
    @XmlElement(name="Options", required=true)
    protected RecordingOptions options;

    public RecordingOptions getOptions() {
        return this.options;
    }

    public void setOptions(RecordingOptions value) {
        this.options = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

