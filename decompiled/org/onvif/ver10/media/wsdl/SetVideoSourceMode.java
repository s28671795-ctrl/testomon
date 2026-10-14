/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoSourceToken", "videoSourceModeToken"})
@XmlRootElement(name="SetVideoSourceMode")
public class SetVideoSourceMode {
    @XmlElement(name="VideoSourceToken", required=true)
    protected String videoSourceToken;
    @XmlElement(name="VideoSourceModeToken", required=true)
    protected String videoSourceModeToken;

    public String getVideoSourceToken() {
        return this.videoSourceToken;
    }

    public void setVideoSourceToken(String value) {
        this.videoSourceToken = value;
    }

    public String getVideoSourceModeToken() {
        return this.videoSourceModeToken;
    }

    public void setVideoSourceModeToken(String value) {
        this.videoSourceModeToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

