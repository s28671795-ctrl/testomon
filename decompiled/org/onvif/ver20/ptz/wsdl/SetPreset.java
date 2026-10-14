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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "presetName", "presetToken"})
@XmlRootElement(name="SetPreset")
public class SetPreset {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="PresetName")
    protected String presetName;
    @XmlElement(name="PresetToken")
    protected String presetToken;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public String getPresetName() {
        return this.presetName;
    }

    public void setPresetName(String value) {
        this.presetName = value;
    }

    public String getPresetToken() {
        return this.presetToken;
    }

    public void setPresetToken(String value) {
        this.presetToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

