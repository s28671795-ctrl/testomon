/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"any"})
public class Capabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="VideoSources")
    protected Integer videoSources;
    @XmlAttribute(name="VideoOutputs")
    protected Integer videoOutputs;
    @XmlAttribute(name="AudioSources")
    protected Integer audioSources;
    @XmlAttribute(name="AudioOutputs")
    protected Integer audioOutputs;
    @XmlAttribute(name="RelayOutputs")
    protected Integer relayOutputs;
    @XmlAttribute(name="SerialPorts")
    protected Integer serialPorts;
    @XmlAttribute(name="DigitalInputs")
    protected Integer digitalInputs;
    @XmlAttribute(name="DigitalInputOptions")
    protected Boolean digitalInputOptions;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public int getVideoSources() {
        if (this.videoSources == null) {
            return 0;
        }
        return this.videoSources;
    }

    public void setVideoSources(Integer value) {
        this.videoSources = value;
    }

    public int getVideoOutputs() {
        if (this.videoOutputs == null) {
            return 0;
        }
        return this.videoOutputs;
    }

    public void setVideoOutputs(Integer value) {
        this.videoOutputs = value;
    }

    public int getAudioSources() {
        if (this.audioSources == null) {
            return 0;
        }
        return this.audioSources;
    }

    public void setAudioSources(Integer value) {
        this.audioSources = value;
    }

    public int getAudioOutputs() {
        if (this.audioOutputs == null) {
            return 0;
        }
        return this.audioOutputs;
    }

    public void setAudioOutputs(Integer value) {
        this.audioOutputs = value;
    }

    public int getRelayOutputs() {
        if (this.relayOutputs == null) {
            return 0;
        }
        return this.relayOutputs;
    }

    public void setRelayOutputs(Integer value) {
        this.relayOutputs = value;
    }

    public int getSerialPorts() {
        if (this.serialPorts == null) {
            return 0;
        }
        return this.serialPorts;
    }

    public void setSerialPorts(Integer value) {
        this.serialPorts = value;
    }

    public int getDigitalInputs() {
        if (this.digitalInputs == null) {
            return 0;
        }
        return this.digitalInputs;
    }

    public void setDigitalInputs(Integer value) {
        this.digitalInputs = value;
    }

    public boolean getDigitalInputOptions() {
        if (this.digitalInputOptions == null) {
            return false;
        }
        return this.digitalInputOptions;
    }

    public void setDigitalInputOptions(Boolean value) {
        this.digitalInputOptions = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

