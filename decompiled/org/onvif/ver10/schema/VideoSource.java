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
import org.onvif.ver10.schema.DeviceEntity;
import org.onvif.ver10.schema.ImagingSettings;
import org.onvif.ver10.schema.VideoResolution;
import org.onvif.ver10.schema.VideoSourceExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSource", propOrder={"framerate", "resolution", "imaging", "extension"})
public class VideoSource
extends DeviceEntity {
    @XmlElement(name="Framerate")
    protected float framerate;
    @XmlElement(name="Resolution", required=true)
    protected VideoResolution resolution;
    @XmlElement(name="Imaging")
    protected ImagingSettings imaging;
    @XmlElement(name="Extension")
    protected VideoSourceExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public float getFramerate() {
        return this.framerate;
    }

    public void setFramerate(float value) {
        this.framerate = value;
    }

    public VideoResolution getResolution() {
        return this.resolution;
    }

    public void setResolution(VideoResolution value) {
        this.resolution = value;
    }

    public ImagingSettings getImaging() {
        return this.imaging;
    }

    public void setImaging(ImagingSettings value) {
        this.imaging = value;
    }

    public VideoSourceExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

