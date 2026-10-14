/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioOutput;
import org.onvif.ver10.schema.AudioSource;
import org.onvif.ver10.schema.DigitalInput;
import org.onvif.ver10.schema.NetworkInterface;
import org.onvif.ver10.schema.OSDConfiguration;
import org.onvif.ver10.schema.PTZNode;
import org.onvif.ver10.schema.RelayOutput;
import org.onvif.ver10.schema.VideoOutput;
import org.onvif.ver10.schema.VideoSource;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DeviceEntity")
@XmlSeeAlso(value={VideoSource.class, AudioSource.class, VideoOutput.class, AudioOutput.class, NetworkInterface.class, RelayOutput.class, DigitalInput.class, PTZNode.class, OSDConfiguration.class})
public class DeviceEntity {
    @XmlAttribute(name="token", required=true)
    protected String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

