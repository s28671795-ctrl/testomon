/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoSourceToken", "any"})
@XmlRootElement(name="GetVideoSourceConfigurationOptions")
public class GetVideoSourceConfigurationOptions {
    @XmlElement(name="VideoSourceToken", required=true)
    protected String videoSourceToken;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public String getVideoSourceToken() {
        return this.videoSourceToken;
    }

    public void setVideoSourceToken(String value) {
        this.videoSourceToken = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

