/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioAttributes;
import org.onvif.ver10.schema.MetadataAttributes;
import org.onvif.ver10.schema.TrackAttributesExtension;
import org.onvif.ver10.schema.TrackInformation;
import org.onvif.ver10.schema.VideoAttributes;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrackAttributes", propOrder={"trackInformation", "videoAttributes", "audioAttributes", "metadataAttributes", "extension"})
public class TrackAttributes {
    @XmlElement(name="TrackInformation", required=true)
    protected TrackInformation trackInformation;
    @XmlElement(name="VideoAttributes")
    protected VideoAttributes videoAttributes;
    @XmlElement(name="AudioAttributes")
    protected AudioAttributes audioAttributes;
    @XmlElement(name="MetadataAttributes")
    protected MetadataAttributes metadataAttributes;
    @XmlElement(name="Extension")
    protected TrackAttributesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public TrackInformation getTrackInformation() {
        return this.trackInformation;
    }

    public void setTrackInformation(TrackInformation value) {
        this.trackInformation = value;
    }

    public VideoAttributes getVideoAttributes() {
        return this.videoAttributes;
    }

    public void setVideoAttributes(VideoAttributes value) {
        this.videoAttributes = value;
    }

    public AudioAttributes getAudioAttributes() {
        return this.audioAttributes;
    }

    public void setAudioAttributes(AudioAttributes value) {
        this.audioAttributes = value;
    }

    public MetadataAttributes getMetadataAttributes() {
        return this.metadataAttributes;
    }

    public void setMetadataAttributes(MetadataAttributes value) {
        this.metadataAttributes = value;
    }

    public TrackAttributesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(TrackAttributesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

