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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.GetTracksResponseList;
import org.onvif.ver10.schema.RecordingConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="GetRecordingsResponseItem", propOrder={"recordingToken", "configuration", "tracks", "any"})
public class GetRecordingsResponseItem {
    @XmlElement(name="RecordingToken", required=true)
    protected String recordingToken;
    @XmlElement(name="Configuration", required=true)
    protected RecordingConfiguration configuration;
    @XmlElement(name="Tracks", required=true)
    protected GetTracksResponseList tracks;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getRecordingToken() {
        return this.recordingToken;
    }

    public void setRecordingToken(String value) {
        this.recordingToken = value;
    }

    public RecordingConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(RecordingConfiguration value) {
        this.configuration = value;
    }

    public GetTracksResponseList getTracks() {
        return this.tracks;
    }

    public void setTracks(GetTracksResponseList value) {
        this.tracks = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

