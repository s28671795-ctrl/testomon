/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.H264Options2;
import org.onvif.ver10.schema.H264Profile;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="H264Options", propOrder={"resolutionsAvailable", "govLengthRange", "frameRateRange", "encodingIntervalRange", "h264ProfilesSupported"})
@XmlSeeAlso(value={H264Options2.class})
public class H264Options {
    @XmlElement(name="ResolutionsAvailable", required=true)
    protected List<VideoResolution> resolutionsAvailable;
    @XmlElement(name="GovLengthRange", required=true)
    protected IntRange govLengthRange;
    @XmlElement(name="FrameRateRange", required=true)
    protected IntRange frameRateRange;
    @XmlElement(name="EncodingIntervalRange", required=true)
    protected IntRange encodingIntervalRange;
    @XmlElement(name="H264ProfilesSupported", required=true)
    @XmlSchemaType(name="string")
    protected List<H264Profile> h264ProfilesSupported;

    public List<VideoResolution> getResolutionsAvailable() {
        if (this.resolutionsAvailable == null) {
            this.resolutionsAvailable = new ArrayList<VideoResolution>();
        }
        return this.resolutionsAvailable;
    }

    public IntRange getGovLengthRange() {
        return this.govLengthRange;
    }

    public void setGovLengthRange(IntRange value) {
        this.govLengthRange = value;
    }

    public IntRange getFrameRateRange() {
        return this.frameRateRange;
    }

    public void setFrameRateRange(IntRange value) {
        this.frameRateRange = value;
    }

    public IntRange getEncodingIntervalRange() {
        return this.encodingIntervalRange;
    }

    public void setEncodingIntervalRange(IntRange value) {
        this.encodingIntervalRange = value;
    }

    public List<H264Profile> getH264ProfilesSupported() {
        if (this.h264ProfilesSupported == null) {
            this.h264ProfilesSupported = new ArrayList<H264Profile>();
        }
        return this.h264ProfilesSupported;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

