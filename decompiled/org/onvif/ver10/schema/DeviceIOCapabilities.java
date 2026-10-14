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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DeviceIOCapabilities", propOrder={"xAddr", "videoSources", "videoOutputs", "audioSources", "audioOutputs", "relayOutputs", "any"})
public class DeviceIOCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="VideoSources")
    protected int videoSources;
    @XmlElement(name="VideoOutputs")
    protected int videoOutputs;
    @XmlElement(name="AudioSources")
    protected int audioSources;
    @XmlElement(name="AudioOutputs")
    protected int audioOutputs;
    @XmlElement(name="RelayOutputs")
    protected int relayOutputs;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public int getVideoSources() {
        return this.videoSources;
    }

    public void setVideoSources(int value) {
        this.videoSources = value;
    }

    public int getVideoOutputs() {
        return this.videoOutputs;
    }

    public void setVideoOutputs(int value) {
        this.videoOutputs = value;
    }

    public int getAudioSources() {
        return this.audioSources;
    }

    public void setAudioSources(int value) {
        this.audioSources = value;
    }

    public int getAudioOutputs() {
        return this.audioOutputs;
    }

    public void setAudioOutputs(int value) {
        this.audioOutputs = value;
    }

    public int getRelayOutputs() {
        return this.relayOutputs;
    }

    public void setRelayOutputs(int value) {
        this.relayOutputs = value;
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

