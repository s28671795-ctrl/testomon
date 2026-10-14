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
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.H264Profile;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="H264DecOptions", propOrder={"resolutionsAvailable", "supportedH264Profiles", "supportedInputBitrate", "supportedFrameRate", "any"})
public class H264DecOptions {
    @XmlElement(name="ResolutionsAvailable", required=true)
    protected List<VideoResolution> resolutionsAvailable;
    @XmlElement(name="SupportedH264Profiles", required=true)
    @XmlSchemaType(name="string")
    protected List<H264Profile> supportedH264Profiles;
    @XmlElement(name="SupportedInputBitrate", required=true)
    protected IntRange supportedInputBitrate;
    @XmlElement(name="SupportedFrameRate", required=true)
    protected IntRange supportedFrameRate;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<VideoResolution> getResolutionsAvailable() {
        if (this.resolutionsAvailable == null) {
            this.resolutionsAvailable = new ArrayList<VideoResolution>();
        }
        return this.resolutionsAvailable;
    }

    public List<H264Profile> getSupportedH264Profiles() {
        if (this.supportedH264Profiles == null) {
            this.supportedH264Profiles = new ArrayList<H264Profile>();
        }
        return this.supportedH264Profiles;
    }

    public IntRange getSupportedInputBitrate() {
        return this.supportedInputBitrate;
    }

    public void setSupportedInputBitrate(IntRange value) {
        this.supportedInputBitrate = value;
    }

    public IntRange getSupportedFrameRate() {
        return this.supportedFrameRate;
    }

    public void setSupportedFrameRate(IntRange value) {
        this.supportedFrameRate = value;
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

