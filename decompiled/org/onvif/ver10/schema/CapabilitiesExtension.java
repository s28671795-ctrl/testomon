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
import org.onvif.ver10.schema.AnalyticsDeviceCapabilities;
import org.onvif.ver10.schema.CapabilitiesExtension2;
import org.onvif.ver10.schema.DeviceIOCapabilities;
import org.onvif.ver10.schema.DisplayCapabilities;
import org.onvif.ver10.schema.ReceiverCapabilities;
import org.onvif.ver10.schema.RecordingCapabilities;
import org.onvif.ver10.schema.ReplayCapabilities;
import org.onvif.ver10.schema.SearchCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CapabilitiesExtension", propOrder={"any", "deviceIO", "display", "recording", "search", "replay", "receiver", "analyticsDevice", "extensions"})
public class CapabilitiesExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="DeviceIO")
    protected DeviceIOCapabilities deviceIO;
    @XmlElement(name="Display")
    protected DisplayCapabilities display;
    @XmlElement(name="Recording")
    protected RecordingCapabilities recording;
    @XmlElement(name="Search")
    protected SearchCapabilities search;
    @XmlElement(name="Replay")
    protected ReplayCapabilities replay;
    @XmlElement(name="Receiver")
    protected ReceiverCapabilities receiver;
    @XmlElement(name="AnalyticsDevice")
    protected AnalyticsDeviceCapabilities analyticsDevice;
    @XmlElement(name="Extensions")
    protected CapabilitiesExtension2 extensions;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public DeviceIOCapabilities getDeviceIO() {
        return this.deviceIO;
    }

    public void setDeviceIO(DeviceIOCapabilities value) {
        this.deviceIO = value;
    }

    public DisplayCapabilities getDisplay() {
        return this.display;
    }

    public void setDisplay(DisplayCapabilities value) {
        this.display = value;
    }

    public RecordingCapabilities getRecording() {
        return this.recording;
    }

    public void setRecording(RecordingCapabilities value) {
        this.recording = value;
    }

    public SearchCapabilities getSearch() {
        return this.search;
    }

    public void setSearch(SearchCapabilities value) {
        this.search = value;
    }

    public ReplayCapabilities getReplay() {
        return this.replay;
    }

    public void setReplay(ReplayCapabilities value) {
        this.replay = value;
    }

    public ReceiverCapabilities getReceiver() {
        return this.receiver;
    }

    public void setReceiver(ReceiverCapabilities value) {
        this.receiver = value;
    }

    public AnalyticsDeviceCapabilities getAnalyticsDevice() {
        return this.analyticsDevice;
    }

    public void setAnalyticsDevice(AnalyticsDeviceCapabilities value) {
        this.analyticsDevice = value;
    }

    public CapabilitiesExtension2 getExtensions() {
        return this.extensions;
    }

    public void setExtensions(CapabilitiesExtension2 value) {
        this.extensions = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

