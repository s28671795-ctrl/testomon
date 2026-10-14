/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioEncoderConfigurationOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"options"})
@XmlRootElement(name="GetAudioEncoderConfigurationOptionsResponse")
public class GetAudioEncoderConfigurationOptionsResponse {
    @XmlElement(name="Options", required=true)
    protected AudioEncoderConfigurationOptions options;

    public AudioEncoderConfigurationOptions getOptions() {
        return this.options;
    }

    public void setOptions(AudioEncoderConfigurationOptions value) {
        this.options = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

