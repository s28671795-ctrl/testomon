/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioEncoderConfiguration;
import org.onvif.ver10.schema.AudioSourceConfiguration;
import org.onvif.ver10.schema.MetadataConfiguration;
import org.onvif.ver10.schema.PTZConfiguration;
import org.onvif.ver10.schema.ProfileExtension;
import org.onvif.ver10.schema.VideoAnalyticsConfiguration;
import org.onvif.ver10.schema.VideoEncoderConfiguration;
import org.onvif.ver10.schema.VideoSourceConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Profile", propOrder={"name", "videoSourceConfiguration", "audioSourceConfiguration", "videoEncoderConfiguration", "audioEncoderConfiguration", "videoAnalyticsConfiguration", "ptzConfiguration", "metadataConfiguration", "extension"})
public class Profile {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="VideoSourceConfiguration")
    protected VideoSourceConfiguration videoSourceConfiguration;
    @XmlElement(name="AudioSourceConfiguration")
    protected AudioSourceConfiguration audioSourceConfiguration;
    @XmlElement(name="VideoEncoderConfiguration")
    protected VideoEncoderConfiguration videoEncoderConfiguration;
    @XmlElement(name="AudioEncoderConfiguration")
    protected AudioEncoderConfiguration audioEncoderConfiguration;
    @XmlElement(name="VideoAnalyticsConfiguration")
    protected VideoAnalyticsConfiguration videoAnalyticsConfiguration;
    @XmlElement(name="PTZConfiguration")
    protected PTZConfiguration ptzConfiguration;
    @XmlElement(name="MetadataConfiguration")
    protected MetadataConfiguration metadataConfiguration;
    @XmlElement(name="Extension")
    protected ProfileExtension extension;
    @XmlAttribute(name="token", required=true)
    protected String token;
    @XmlAttribute(name="fixed")
    protected Boolean fixed;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public VideoSourceConfiguration getVideoSourceConfiguration() {
        return this.videoSourceConfiguration;
    }

    public void setVideoSourceConfiguration(VideoSourceConfiguration value) {
        this.videoSourceConfiguration = value;
    }

    public AudioSourceConfiguration getAudioSourceConfiguration() {
        return this.audioSourceConfiguration;
    }

    public void setAudioSourceConfiguration(AudioSourceConfiguration value) {
        this.audioSourceConfiguration = value;
    }

    public VideoEncoderConfiguration getVideoEncoderConfiguration() {
        return this.videoEncoderConfiguration;
    }

    public void setVideoEncoderConfiguration(VideoEncoderConfiguration value) {
        this.videoEncoderConfiguration = value;
    }

    public AudioEncoderConfiguration getAudioEncoderConfiguration() {
        return this.audioEncoderConfiguration;
    }

    public void setAudioEncoderConfiguration(AudioEncoderConfiguration value) {
        this.audioEncoderConfiguration = value;
    }

    public VideoAnalyticsConfiguration getVideoAnalyticsConfiguration() {
        return this.videoAnalyticsConfiguration;
    }

    public void setVideoAnalyticsConfiguration(VideoAnalyticsConfiguration value) {
        this.videoAnalyticsConfiguration = value;
    }

    public PTZConfiguration getPTZConfiguration() {
        return this.ptzConfiguration;
    }

    public void setPTZConfiguration(PTZConfiguration value) {
        this.ptzConfiguration = value;
    }

    public MetadataConfiguration getMetadataConfiguration() {
        return this.metadataConfiguration;
    }

    public void setMetadataConfiguration(MetadataConfiguration value) {
        this.metadataConfiguration = value;
    }

    public ProfileExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ProfileExtension value) {
        this.extension = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Boolean getFixed() {
        return this.fixed;
    }

    public void setFixed(Boolean value) {
        this.fixed = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

