/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZSpeed;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "presetToken", "speed"})
@XmlRootElement(name="GotoPreset")
public class GotoPreset {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="PresetToken", required=true)
    protected String presetToken;
    @XmlElement(name="Speed")
    protected PTZSpeed speed;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public String getPresetToken() {
        return this.presetToken;
    }

    public void setPresetToken(String value) {
        this.presetToken = value;
    }

    public PTZSpeed getSpeed() {
        return this.speed;
    }

    public void setSpeed(PTZSpeed value) {
        this.speed = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

