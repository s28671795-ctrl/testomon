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
import org.onvif.ver10.schema.PresetTour;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "presetTour"})
@XmlRootElement(name="ModifyPresetTour")
public class ModifyPresetTour {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="PresetTour", required=true)
    protected PresetTour presetTour;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public PresetTour getPresetTour() {
        return this.presetTour;
    }

    public void setPresetTour(PresetTour value) {
        this.presetTour = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

