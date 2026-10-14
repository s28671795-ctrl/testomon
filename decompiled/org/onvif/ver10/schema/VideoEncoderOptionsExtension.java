/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.H264Options2;
import org.onvif.ver10.schema.JpegOptions2;
import org.onvif.ver10.schema.Mpeg4Options2;
import org.onvif.ver10.schema.VideoEncoderOptionsExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoEncoderOptionsExtension", propOrder={"any", "jpeg", "mpeg4", "h264", "extension"})
public class VideoEncoderOptionsExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="JPEG")
    protected JpegOptions2 jpeg;
    @XmlElement(name="MPEG4")
    protected Mpeg4Options2 mpeg4;
    @XmlElement(name="H264")
    protected H264Options2 h264;
    @XmlElement(name="Extension")
    protected VideoEncoderOptionsExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public JpegOptions2 getJPEG() {
        return this.jpeg;
    }

    public void setJPEG(JpegOptions2 value) {
        this.jpeg = value;
    }

    public Mpeg4Options2 getMPEG4() {
        return this.mpeg4;
    }

    public void setMPEG4(Mpeg4Options2 value) {
        this.mpeg4 = value;
    }

    public H264Options2 getH264() {
        return this.h264;
    }

    public void setH264(H264Options2 value) {
        this.h264 = value;
    }

    public VideoEncoderOptionsExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(VideoEncoderOptionsExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

