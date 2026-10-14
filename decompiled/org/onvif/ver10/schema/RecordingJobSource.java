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
import org.onvif.ver10.schema.RecordingJobSourceExtension;
import org.onvif.ver10.schema.RecordingJobTrack;
import org.onvif.ver10.schema.SourceReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingJobSource", propOrder={"sourceToken", "autoCreateReceiver", "tracks", "extension"})
public class RecordingJobSource {
    @XmlElement(name="SourceToken")
    protected SourceReference sourceToken;
    @XmlElement(name="AutoCreateReceiver")
    protected Boolean autoCreateReceiver;
    @XmlElement(name="Tracks")
    protected List<RecordingJobTrack> tracks;
    @XmlElement(name="Extension")
    protected RecordingJobSourceExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public SourceReference getSourceToken() {
        return this.sourceToken;
    }

    public void setSourceToken(SourceReference value) {
        this.sourceToken = value;
    }

    public Boolean getAutoCreateReceiver() {
        return this.autoCreateReceiver;
    }

    public void setAutoCreateReceiver(Boolean value) {
        this.autoCreateReceiver = value;
    }

    public List<RecordingJobTrack> getTracks() {
        if (this.tracks == null) {
            this.tracks = new ArrayList<RecordingJobTrack>();
        }
        return this.tracks;
    }

    public RecordingJobSourceExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RecordingJobSourceExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

