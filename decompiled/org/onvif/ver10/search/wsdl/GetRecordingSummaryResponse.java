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
import org.onvif.ver10.schema.RecordingSummary;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"summary"})
@XmlRootElement(name="GetRecordingSummaryResponse")
public class GetRecordingSummaryResponse {
    @XmlElement(name="Summary", required=true)
    protected RecordingSummary summary;

    public RecordingSummary getSummary() {
        return this.summary;
    }

    public void setSummary(RecordingSummary value) {
        this.summary = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

