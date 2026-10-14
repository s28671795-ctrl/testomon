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
import org.onvif.ver10.schema.GetRecordingsResponseItem;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"recordingItem"})
@XmlRootElement(name="GetRecordingsResponse")
public class GetRecordingsResponse {
    @XmlElement(name="RecordingItem")
    protected List<GetRecordingsResponseItem> recordingItem;

    public List<GetRecordingsResponseItem> getRecordingItem() {
        if (this.recordingItem == null) {
            this.recordingItem = new ArrayList<GetRecordingsResponseItem>();
        }
        return this.recordingItem;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

