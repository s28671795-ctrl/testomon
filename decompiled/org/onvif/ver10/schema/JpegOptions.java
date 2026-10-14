/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.JpegOptions2;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="JpegOptions", propOrder={"resolutionsAvailable", "frameRateRange", "encodingIntervalRange"})
@XmlSeeAlso(value={JpegOptions2.class})
public class JpegOptions {
    @XmlElement(name="ResolutionsAvailable", required=true)
    protected List<VideoResolution> resolutionsAvailable;
    @XmlElement(name="FrameRateRange", required=true)
    protected IntRange frameRateRange;
    @XmlElement(name="EncodingIntervalRange", required=true)
    protected IntRange encodingIntervalRange;

    public List<VideoResolution> getResolutionsAvailable() {
        if (this.resolutionsAvailable == null) {
            this.resolutionsAvailable = new ArrayList<VideoResolution>();
        }
        return this.resolutionsAvailable;
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

