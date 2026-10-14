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
import org.onvif.ver10.schema.AudioDecoderConfigurationOptions;
import org.onvif.ver10.schema.AudioEncoderConfigurationOptions;
import org.onvif.ver10.schema.VideoDecoderConfigurationOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CodingCapabilities", propOrder={"audioEncodingCapabilities", "audioDecodingCapabilities", "videoDecodingCapabilities", "any"})
public class CodingCapabilities {
    @XmlElement(name="AudioEncodingCapabilities")
    protected AudioEncoderConfigurationOptions audioEncodingCapabilities;
    @XmlElement(name="AudioDecodingCapabilities")
    protected AudioDecoderConfigurationOptions audioDecodingCapabilities;
    @XmlElement(name="VideoDecodingCapabilities", required=true)
    protected VideoDecoderConfigurationOptions videoDecodingCapabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AudioEncoderConfigurationOptions getAudioEncodingCapabilities() {
        return this.audioEncodingCapabilities;
    }

    public void setAudioEncodingCapabilities(AudioEncoderConfigurationOptions value) {
        this.audioEncodingCapabilities = value;
    }

    public AudioDecoderConfigurationOptions getAudioDecodingCapabilities() {
        return this.audioDecodingCapabilities;
    }

    public void setAudioDecodingCapabilities(AudioDecoderConfigurationOptions value) {
        this.audioDecodingCapabilities = value;
    }

    public VideoDecoderConfigurationOptions getVideoDecodingCapabilities() {
        return this.videoDecodingCapabilities;
    }

    public void setVideoDecodingCapabilities(VideoDecoderConfigurationOptions value) {
        this.videoDecodingCapabilities = value;
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

