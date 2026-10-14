/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoRateControl", propOrder={"frameRateLimit", "encodingInterval", "bitrateLimit"})
public class VideoRateControl {
    @XmlElement(name="FrameRateLimit")
    protected int frameRateLimit;
    @XmlElement(name="EncodingInterval")
    protected int encodingInterval;
    @XmlElement(name="BitrateLimit")
    protected int bitrateLimit;

    public int getFrameRateLimit() {
        return this.frameRateLimit;
    }

    public void setFrameRateLimit(int value) {
        this.frameRateLimit = value;
    }

    public int getEncodingInterval() {
        return this.encodingInterval;
    }

    public void setEncodingInterval(int value) {
        this.encodingInterval = value;
    }

    public int getBitrateLimit() {
        return this.bitrateLimit;
    }

    public void setBitrateLimit(int value) {
        this.bitrateLimit = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

