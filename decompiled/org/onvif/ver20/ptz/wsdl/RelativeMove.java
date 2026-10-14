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
import org.onvif.ver10.schema.PTZVector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "translation", "speed"})
@XmlRootElement(name="RelativeMove")
public class RelativeMove {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="Translation", required=true)
    protected PTZVector translation;
    @XmlElement(name="Speed")
    protected PTZSpeed speed;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public PTZVector getTranslation() {
        return this.translation;
    }

    public void setTranslation(PTZVector value) {
        this.translation = value;
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

