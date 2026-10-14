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
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.VideoResolution2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoEncoder2ConfigurationOptions", propOrder={"encoding", "qualityRange", "resolutionsAvailable", "bitrateRange", "any"})
public class VideoEncoder2ConfigurationOptions {
    @XmlElement(name="Encoding", required=true)
    protected String encoding;
    @XmlElement(name="QualityRange", required=true)
    protected IntRange qualityRange;
    @XmlElement(name="ResolutionsAvailable", required=true)
    protected List<VideoResolution2> resolutionsAvailable;
    @XmlElement(name="BitrateRange", required=true)
    protected IntRange bitrateRange;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="GovLengthRange")
    protected List<Integer> govLengthRange;
    @XmlAttribute(name="FrameRatesSupported")
    protected List<Float> frameRatesSupported;
    @XmlAttribute(name="ProfilesSupported")
    protected List<String> profilesSupported;
    @XmlAttribute(name="ConstantBitRateSupported")
    protected Boolean constantBitRateSupported;
    @XmlAttribute(name="GuaranteedInstances")
    protected Integer guaranteedInstances;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String value) {
        this.encoding = value;
    }

    public IntRange getQualityRange() {
        return this.qualityRange;
    }

    public void setQualityRange(IntRange value) {
        this.qualityRange = value;
    }

    public List<VideoResolution2> getResolutionsAvailable() {
        if (this.resolutionsAvailable == null) {
            this.resolutionsAvailable = new ArrayList<VideoResolution2>();
        }
        return this.resolutionsAvailable;
    }

    public IntRange getBitrateRange() {
        return this.bitrateRange;
    }

    public void setBitrateRange(IntRange value) {
        this.bitrateRange = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<Integer> getGovLengthRange() {
        if (this.govLengthRange == null) {
            this.govLengthRange = new ArrayList<Integer>();
        }
        return this.govLengthRange;
    }

    public List<Float> getFrameRatesSupported() {
        if (this.frameRatesSupported == null) {
            this.frameRatesSupported = new ArrayList<Float>();
        }
        return this.frameRatesSupported;
    }

    public List<String> getProfilesSupported() {
        if (this.profilesSupported == null) {
            this.profilesSupported = new ArrayList<String>();
        }
        return this.profilesSupported;
    }

    public Boolean getConstantBitRateSupported() {
        return this.constantBitRateSupported;
    }

    public void setConstantBitRateSupported(Boolean value) {
        this.constantBitRateSupported = value;
    }

    public Integer getGuaranteedInstances() {
        return this.guaranteedInstances;
    }

    public void setGuaranteedInstances(Integer value) {
        this.guaranteedInstances = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

