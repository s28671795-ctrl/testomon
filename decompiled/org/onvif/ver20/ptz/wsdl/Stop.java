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
@XmlType(name="", propOrder={"profileToken", "panTilt", "zoom"})
@XmlRootElement(name="Stop")
public class Stop {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="PanTilt")
    protected Boolean panTilt;
    @XmlElement(name="Zoom")
    protected Boolean zoom;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public Boolean getPanTilt() {
        return this.panTilt;
    }

    public void setPanTilt(Boolean value) {
        this.panTilt = value;
    }

    public Boolean getZoom() {
        return this.zoom;
    }

    public void setZoom(Boolean value) {
        this.zoom = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

