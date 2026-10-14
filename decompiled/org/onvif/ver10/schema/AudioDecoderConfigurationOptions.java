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
import org.onvif.ver10.schema.AACDecOptions;
import org.onvif.ver10.schema.AudioDecoderConfigurationOptionsExtension;
import org.onvif.ver10.schema.G711DecOptions;
import org.onvif.ver10.schema.G726DecOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioDecoderConfigurationOptions", propOrder={"aacDecOptions", "g711DecOptions", "g726DecOptions", "extension"})
public class AudioDecoderConfigurationOptions {
    @XmlElement(name="AACDecOptions")
    protected AACDecOptions aacDecOptions;
    @XmlElement(name="G711DecOptions")
    protected G711DecOptions g711DecOptions;
    @XmlElement(name="G726DecOptions")
    protected G726DecOptions g726DecOptions;
    @XmlElement(name="Extension")
    protected AudioDecoderConfigurationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AACDecOptions getAACDecOptions() {
        return this.aacDecOptions;
    }

    public void setAACDecOptions(AACDecOptions value) {
        this.aacDecOptions = value;
    }

    public G711DecOptions getG711DecOptions() {
        return this.g711DecOptions;
    }

    public void setG711DecOptions(G711DecOptions value) {
        this.g711DecOptions = value;
    }

    public G726DecOptions getG726DecOptions() {
        return this.g726DecOptions;
    }

    public void setG726DecOptions(G726DecOptions value) {
        this.g726DecOptions = value;
    }

    public AudioDecoderConfigurationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AudioDecoderConfigurationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

