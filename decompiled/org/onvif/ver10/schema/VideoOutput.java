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
import org.onvif.ver10.schema.Layout;
import org.onvif.ver10.schema.VideoOutputExtension;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoOutput", propOrder={"layout", "resolution", "refreshRate", "aspectRatio", "extension"})
public class VideoOutput
extends DeviceEntity {
    @XmlElement(name="Layout", required=true)
    protected Layout layout;
    @XmlElement(name="Resolution")
    protected VideoResolution resolution;
    @XmlElement(name="RefreshRate")
    protected Float refreshRate;
    @XmlElement(name="AspectRatio")
    protected Float aspectRatio;
    @XmlElement(name="Extension")
    protected VideoOutputExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Layout getLayout() {
        return this.layout;
    }

    public void setLayout(Layout value) {
        this.layout = value;
    }

    public VideoResolution getResolution() {
        return this.resolution;
    }

    public void setResolution(VideoResolution value) {
        this.resolution = value;
    }

    public Float getRefreshRate() {
        return this.refreshRate;
    }

    public void setRefreshRate(Float value) {
        this.refreshRate = value;
    }

    public Float getAspectRatio() {
        return this.aspectRatio;
    }

    public void setAspectRatio(Float value) {
        this.aspectRatio = value;
    }

    public VideoOutputExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoOutputExtension value) {
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

