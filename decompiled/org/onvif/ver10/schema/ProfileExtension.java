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
import org.onvif.ver10.schema.AudioDecoderConfiguration;
import org.onvif.ver10.schema.AudioOutputConfiguration;
import org.onvif.ver10.schema.ProfileExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProfileExtension", propOrder={"any", "audioOutputConfiguration", "audioDecoderConfiguration", "extension"})
public class ProfileExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="AudioOutputConfiguration")
    protected AudioOutputConfiguration audioOutputConfiguration;
    @XmlElement(name="AudioDecoderConfiguration")
    protected AudioDecoderConfiguration audioDecoderConfiguration;
    @XmlElement(name="Extension")
    protected ProfileExtension2 extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public AudioOutputConfiguration getAudioOutputConfiguration() {
        return this.audioOutputConfiguration;
    }

    public void setAudioOutputConfiguration(AudioOutputConfiguration value) {
        this.audioOutputConfiguration = value;
    }

    public AudioDecoderConfiguration getAudioDecoderConfiguration() {
        return this.audioDecoderConfiguration;
    }

    public void setAudioDecoderConfiguration(AudioDecoderConfiguration value) {
        this.audioDecoderConfiguration = value;
    }

    public ProfileExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(ProfileExtension2 value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

