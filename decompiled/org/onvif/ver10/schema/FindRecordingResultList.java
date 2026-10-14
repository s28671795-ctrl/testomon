/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingInformation;
import org.onvif.ver10.schema.SearchState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FindRecordingResultList", propOrder={"searchState", "recordingInformation"})
public class FindRecordingResultList {
    @XmlElement(name="SearchState", required=true)
    @XmlSchemaType(name="string")
    protected SearchState searchState;
    @XmlElement(name="RecordingInformation")
    protected List<RecordingInformation> recordingInformation;

    public SearchState getSearchState() {
        return this.searchState;
    }

    public void setSearchState(SearchState value) {
        this.searchState = value;
    }

    public List<RecordingInformation> getRecordingInformation() {
        if (this.recordingInformation == null) {
            this.recordingInformation = new ArrayList<RecordingInformation>();
        }
        return this.recordingInformation;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

