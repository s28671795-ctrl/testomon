/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.H264DecOptions;
import org.onvif.ver10.schema.JpegDecOptions;
import org.onvif.ver10.schema.Mpeg4DecOptions;
import org.onvif.ver10.schema.VideoDecoderConfigurationOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoDecoderConfigurationOptions", propOrder={"jpegDecOptions", "h264DecOptions", "mpeg4DecOptions", "extension"})
public class VideoDecoderConfigurationOptions {
    @XmlElement(name="JpegDecOptions")
    protected JpegDecOptions jpegDecOptions;
    @XmlElement(name="H264DecOptions")
    protected H264DecOptions h264DecOptions;
    @XmlElement(name="Mpeg4DecOptions")
    protected Mpeg4DecOptions mpeg4DecOptions;
    @XmlElement(name="Extension")
    protected VideoDecoderConfigurationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public JpegDecOptions getJpegDecOptions() {
        return this.jpegDecOptions;
    }

    public void setJpegDecOptions(JpegDecOptions value) {
        this.jpegDecOptions = value;
    }

    public H264DecOptions getH264DecOptions() {
        return this.h264DecOptions;
    }

    public void setH264DecOptions(H264DecOptions value) {
        this.h264DecOptions = value;
    }

    public Mpeg4DecOptions getMpeg4DecOptions() {
        return this.mpeg4DecOptions;
    }

    public void setMpeg4DecOptions(Mpeg4DecOptions value) {
        this.mpeg4DecOptions = value;
    }

    public VideoDecoderConfigurationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoDecoderConfigurationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

