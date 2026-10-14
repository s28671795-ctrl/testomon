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
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioEncoding;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.MulticastConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioEncoderConfiguration", propOrder={"encoding", "bitrate", "sampleRate", "multicast", "sessionTimeout", "any"})
public class AudioEncoderConfiguration
extends ConfigurationEntity {
    @XmlElement(name="Encoding", required=true)
    @XmlSchemaType(name="string")
    protected AudioEncoding encoding;
    @XmlElement(name="Bitrate")
    protected int bitrate;
    @XmlElement(name="SampleRate")
    protected int sampleRate;
    @XmlElement(name="Multicast", required=true)
    protected MulticastConfiguration multicast;
    @XmlElement(name="SessionTimeout", required=true)
    protected Duration sessionTimeout;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AudioEncoding getEncoding() {
        return this.encoding;
    }

    public void setEncoding(AudioEncoding value) {
        this.encoding = value;
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(int value) {
        this.bitrate = value;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int value) {
        this.sampleRate = value;
    }

    public MulticastConfiguration getMulticast() {
        return this.multicast;
    }

    public void setMulticast(MulticastConfiguration value) {
        this.multicast = value;
    }

    public Duration getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Duration value) {
        this.sessionTimeout = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

