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
import org.onvif.ver10.schema.RecordingJobStateTracks;
import org.onvif.ver10.schema.SourceReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingJobStateSource", propOrder={"sourceToken", "state", "tracks", "any"})
public class RecordingJobStateSource {
    @XmlElement(name="SourceToken", required=true)
    protected SourceReference sourceToken;
    @XmlElement(name="State", required=true)
    protected String state;
    @XmlElement(name="Tracks", required=true)
    protected RecordingJobStateTracks tracks;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public SourceReference getSourceToken() {
        return this.sourceToken;
    }

    public void setSourceToken(SourceReference value) {
        this.sourceToken = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public RecordingJobStateTracks getTracks() {
        return this.tracks;
    }

    public void setTracks(RecordingJobStateTracks value) {
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

