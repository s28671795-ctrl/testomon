/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioSource;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"audioSources"})
@XmlRootElement(name="GetAudioSourcesResponse")
public class GetAudioSourcesResponse {
    @XmlElement(name="AudioSources")
    protected List<AudioSource> audioSources;

    public List<AudioSource> getAudioSources() {
        if (this.audioSources == null) {
            this.audioSources = new ArrayList<AudioSource>();
        }
        return this.audioSources;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

