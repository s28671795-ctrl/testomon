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
import org.onvif.ver10.schema.VideoSourceConfigurationOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoSourceConfigurationOptions", "any"})
@XmlRootElement(name="GetVideoSourceConfigurationOptionsResponse")
public class GetVideoSourceConfigurationOptionsResponse {
    @XmlElement(name="VideoSourceConfigurationOptions", required=true)
    protected VideoSourceConfigurationOptions videoSourceConfigurationOptions;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public VideoSourceConfigurationOptions getVideoSourceConfigurationOptions() {
        return this.videoSourceConfigurationOptions;
    }

    public void setVideoSourceConfigurationOptions(VideoSourceConfigurationOptions value) {
        this.videoSourceConfigurationOptions = value;
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

