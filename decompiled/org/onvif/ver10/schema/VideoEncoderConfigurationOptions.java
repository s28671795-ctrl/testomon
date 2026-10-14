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
import org.onvif.ver10.schema.H264Options;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.JpegOptions;
import org.onvif.ver10.schema.Mpeg4Options;
import org.onvif.ver10.schema.VideoEncoderOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoEncoderConfigurationOptions", propOrder={"qualityRange", "jpeg", "mpeg4", "h264", "extension"})
public class VideoEncoderConfigurationOptions {
    @XmlElement(name="QualityRange", required=true)
    protected IntRange qualityRange;
    @XmlElement(name="JPEG")
    protected JpegOptions jpeg;
    @XmlElement(name="MPEG4")
    protected Mpeg4Options mpeg4;
    @XmlElement(name="H264")
    protected H264Options h264;
    @XmlElement(name="Extension")
    protected VideoEncoderOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IntRange getQualityRange() {
        return this.qualityRange;
    }

    public void setQualityRange(IntRange value) {
        this.qualityRange = value;
    }

    public JpegOptions getJPEG() {
        return this.jpeg;
    }

    public void setJPEG(JpegOptions value) {
        this.jpeg = value;
    }

    public Mpeg4Options getMPEG4() {
        return this.mpeg4;
    }

    public void setMPEG4(Mpeg4Options value) {
        this.mpeg4 = value;
    }

    public H264Options getH264() {
        return this.h264;
    }

    public void setH264(H264Options value) {
        this.h264 = value;
    }

    public VideoEncoderOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoEncoderOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

