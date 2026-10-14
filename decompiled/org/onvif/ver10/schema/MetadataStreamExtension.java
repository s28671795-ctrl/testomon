/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioAnalyticsStream;
import org.onvif.ver10.schema.MetadataStreamExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataStreamExtension", propOrder={"any", "audioAnalyticsStream", "extension"})
public class MetadataStreamExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="AudioAnalyticsStream")
    protected AudioAnalyticsStream audioAnalyticsStream;
    @XmlElement(name="Extension")
    protected MetadataStreamExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public AudioAnalyticsStream getAudioAnalyticsStream() {
        return this.audioAnalyticsStream;
    }

    public void setAudioAnalyticsStream(AudioAnalyticsStream value) {
        this.audioAnalyticsStream = value;
    }

    public MetadataStreamExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(MetadataStreamExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

