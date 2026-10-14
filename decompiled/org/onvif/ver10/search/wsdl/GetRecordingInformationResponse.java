/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"recordingInformation"})
@XmlRootElement(name="GetRecordingInformationResponse")
public class GetRecordingInformationResponse {
    @XmlElement(name="RecordingInformation", required=true)
    protected RecordingInformation recordingInformation;

    public RecordingInformation getRecordingInformation() {
        return this.recordingInformation;
    }

    public void setRecordingInformation(RecordingInformation value) {
        this.recordingInformation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

