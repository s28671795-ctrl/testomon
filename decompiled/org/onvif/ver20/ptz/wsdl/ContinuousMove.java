/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZSpeed;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "velocity", "timeout"})
@XmlRootElement(name="ContinuousMove")
public class ContinuousMove {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="Velocity", required=true)
    protected PTZSpeed velocity;
    @XmlElement(name="Timeout")
    protected Duration timeout;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public PTZSpeed getVelocity() {
        return this.velocity;
    }

    public void setVelocity(PTZSpeed value) {
        this.velocity = value;
    }

    public Duration getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Duration value) {
        this.timeout = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

