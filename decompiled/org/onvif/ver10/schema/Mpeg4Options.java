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
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.Mpeg4Options2;
import org.onvif.ver10.schema.Mpeg4Profile;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Mpeg4Options", propOrder={"resolutionsAvailable", "govLengthRange", "frameRateRange", "encodingIntervalRange", "mpeg4ProfilesSupported"})
@XmlSeeAlso(value={Mpeg4Options2.class})
public class Mpeg4Options {
    @XmlElement(name="ResolutionsAvailable", required=true)
    protected List<VideoResolution> resolutionsAvailable;
    @XmlElement(name="GovLengthRange", required=true)
    protected IntRange govLengthRange;
    @XmlElement(name="FrameRateRange", required=true)
    protected IntRange frameRateRange;
    @XmlElement(name="EncodingIntervalRange", required=true)
    protected IntRange encodingIntervalRange;
    @XmlElement(name="Mpeg4ProfilesSupported", required=true)
    @XmlSchemaType(name="string")
    protected List<Mpeg4Profile> mpeg4ProfilesSupported;

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

    public List<Mpeg4Profile> getMpeg4ProfilesSupported() {
        if (this.mpeg4ProfilesSupported == null) {
            this.mpeg4ProfilesSupported = new ArrayList<Mpeg4Profile>();
        }
        return this.mpeg4ProfilesSupported;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

