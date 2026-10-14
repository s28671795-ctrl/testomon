/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.media.wsdl.VideoSourceMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoSourceModes"})
@XmlRootElement(name="GetVideoSourceModesResponse")
public class GetVideoSourceModesResponse {
    @XmlElement(name="VideoSourceModes", required=true)
    protected List<VideoSourceMode> videoSourceModes;

    public List<VideoSourceMode> getVideoSourceModes() {
        if (this.videoSourceModes == null) {
            this.videoSourceModes = new ArrayList<VideoSourceMode>();
        }
        return this.videoSourceModes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

