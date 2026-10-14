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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.H264Configuration;
import org.onvif.ver10.schema.Mpeg4Configuration;
import org.onvif.ver10.schema.MulticastConfiguration;
import org.onvif.ver10.schema.VideoEncoding;
import org.onvif.ver10.schema.VideoRateControl;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoEncoderConfiguration", propOrder={"encoding", "resolution", "quality", "rateControl", "mpeg4", "h264", "multicast", "sessionTimeout", "any"})
public class VideoEncoderConfiguration
extends ConfigurationEntity {
    @XmlElement(name="Encoding", required=true)
    @XmlSchemaType(name="string")
    protected VideoEncoding encoding;
    @XmlElement(name="Resolution", required=true)
    protected VideoResolution resolution;
    @XmlElement(name="Quality")
    protected float quality;
    @XmlElement(name="RateControl")
    protected VideoRateControl rateControl;
    @XmlElement(name="MPEG4")
    protected Mpeg4Configuration mpeg4;
    @XmlElement(name="H264")
    protected H264Configuration h264;
    @XmlElement(name="Multicast", required=true)
    protected MulticastConfiguration multicast;
    @XmlElement(name="SessionTimeout", required=true)
    protected Duration sessionTimeout;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public VideoEncoding getEncoding() {
        return this.encoding;
    }

    public void setEncoding(VideoEncoding value) {
        this.encoding = value;
    }

    public VideoResolution getResolution() {
        return this.resolution;
    }

    public void setResolution(VideoResolution value) {
        this.resolution = value;
    }

    public float getQuality() {
        return this.quality;
    }

    public void setQuality(float value) {
        this.quality = value;
    }

    public VideoRateControl getRateControl() {
        return this.rateControl;
    }

    public void setRateControl(VideoRateControl value) {
        this.rateControl = value;
    }

    public Mpeg4Configuration getMPEG4() {
        return this.mpeg4;
    }

    public void setMPEG4(Mpeg4Configuration value) {
        this.mpeg4 = value;
    }

    public H264Configuration getH264() {
        return this.h264;
    }

    public void setH264(H264Configuration value) {
        this.h264 = value;
    }

    public MulticastConfiguration getMulticast() {
        return this.multicast;
    }

    public void setMulticast(MulticastConfiguration value) {
        this.multicast = value;
    }

    public Duration getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Duration value) {
        this.sessionTimeout = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

