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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.MulticastConfiguration;
import org.onvif.ver10.schema.VideoRateControl2;
import org.onvif.ver10.schema.VideoResolution2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoEncoder2Configuration", propOrder={"encoding", "resolution", "rateControl", "multicast", "quality", "any"})
public class VideoEncoder2Configuration
extends ConfigurationEntity {
    @XmlElement(name="Encoding", required=true)
    protected String encoding;
    @XmlElement(name="Resolution", required=true)
    protected VideoResolution2 resolution;
    @XmlElement(name="RateControl")
    protected VideoRateControl2 rateControl;
    @XmlElement(name="Multicast")
    protected MulticastConfiguration multicast;
    @XmlElement(name="Quality")
    protected float quality;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="GovLength")
    protected Integer govLength;
    @XmlAttribute(name="Profile")
    protected String profile;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String value) {
        this.encoding = value;
    }

    public VideoResolution2 getResolution() {
        return this.resolution;
    }

    public void setResolution(VideoResolution2 value) {
        this.resolution = value;
    }

    public VideoRateControl2 getRateControl() {
        return this.rateControl;
    }

    public void setRateControl(VideoRateControl2 value) {
        this.rateControl = value;
    }

    public MulticastConfiguration getMulticast() {
        return this.multicast;
    }

    public void setMulticast(MulticastConfiguration value) {
        this.multicast = value;
    }

    public float getQuality() {
        return this.quality;
    }

    public void setQuality(float value) {
        this.quality = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Integer getGovLength() {
        return this.govLength;
    }

    public void setGovLength(Integer value) {
        this.govLength = value;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String value) {
        this.profile = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

