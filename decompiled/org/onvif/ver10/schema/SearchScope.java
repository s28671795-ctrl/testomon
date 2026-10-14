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
import org.onvif.ver10.schema.SearchScopeExtension;
import org.onvif.ver10.schema.SourceReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SearchScope", propOrder={"includedSources", "includedRecordings", "recordingInformationFilter", "extension"})
public class SearchScope {
    @XmlElement(name="IncludedSources")
    protected List<SourceReference> includedSources;
    @XmlElement(name="IncludedRecordings")
    protected List<String> includedRecordings;
    @XmlElement(name="RecordingInformationFilter")
    protected String recordingInformationFilter;
    @XmlElement(name="Extension")
    protected SearchScopeExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<SourceReference> getIncludedSources() {
        if (this.includedSources == null) {
            this.includedSources = new ArrayList<SourceReference>();
        }
        return this.includedSources;
    }

    public List<String> getIncludedRecordings() {
        if (this.includedRecordings == null) {
            this.includedRecordings = new ArrayList<String>();
        }
        return this.includedRecordings;
    }

    public String getRecordingInformationFilter() {
        return this.recordingInformationFilter;
    }

    public void setRecordingInformationFilter(String value) {
        this.recordingInformationFilter = value;
    }

    public SearchScopeExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SearchScopeExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

