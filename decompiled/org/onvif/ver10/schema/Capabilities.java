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
import org.onvif.ver10.schema.AnalyticsCapabilities;
import org.onvif.ver10.schema.CapabilitiesExtension;
import org.onvif.ver10.schema.DeviceCapabilities;
import org.onvif.ver10.schema.EventCapabilities;
import org.onvif.ver10.schema.ImagingCapabilities;
import org.onvif.ver10.schema.MediaCapabilities;
import org.onvif.ver10.schema.PTZCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"analytics", "device", "events", "imaging", "media", "ptz", "extension"})
public class Capabilities {
    @XmlElement(name="Analytics")
    protected AnalyticsCapabilities analytics;
    @XmlElement(name="Device")
    protected DeviceCapabilities device;
    @XmlElement(name="Events")
    protected EventCapabilities events;
    @XmlElement(name="Imaging")
    protected ImagingCapabilities imaging;
    @XmlElement(name="Media")
    protected MediaCapabilities media;
    @XmlElement(name="PTZ")
    protected PTZCapabilities ptz;
    @XmlElement(name="Extension")
    protected CapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AnalyticsCapabilities getAnalytics() {
        return this.analytics;
    }

    public void setAnalytics(AnalyticsCapabilities value) {
        this.analytics = value;
    }

    public DeviceCapabilities getDevice() {
        return this.device;
    }

    public void setDevice(DeviceCapabilities value) {
        this.device = value;
    }

    public EventCapabilities getEvents() {
        return this.events;
    }

    public void setEvents(EventCapabilities value) {
        this.events = value;
    }

    public ImagingCapabilities getImaging() {
        return this.imaging;
    }

    public void setImaging(ImagingCapabilities value) {
        this.imaging = value;
    }

    public MediaCapabilities getMedia() {
        return this.media;
    }

    public void setMedia(MediaCapabilities value) {
        this.media = value;
    }

    public PTZCapabilities getPTZ() {
        return this.ptz;
    }

    public void setPTZ(PTZCapabilities value) {
        this.ptz = value;
    }

    public CapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(CapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

