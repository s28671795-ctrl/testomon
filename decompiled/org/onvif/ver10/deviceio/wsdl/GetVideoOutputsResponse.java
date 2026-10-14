/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.VideoOutput;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoOutputs"})
@XmlRootElement(name="GetVideoOutputsResponse")
public class GetVideoOutputsResponse {
    @XmlElement(name="VideoOutputs")
    protected List<VideoOutput> videoOutputs;

    public List<VideoOutput> getVideoOutputs() {
        if (this.videoOutputs == null) {
            this.videoOutputs = new ArrayList<VideoOutput>();
        }
        return this.videoOutputs;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

