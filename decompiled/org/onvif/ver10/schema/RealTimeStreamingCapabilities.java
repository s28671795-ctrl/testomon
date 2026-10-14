/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RealTimeStreamingCapabilitiesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RealTimeStreamingCapabilities", propOrder={"rtpMulticast", "rtptcp", "rtprtsptcp", "extension"})
public class RealTimeStreamingCapabilities {
    @XmlElement(name="RTPMulticast")
    protected Boolean rtpMulticast;
    @XmlElement(name="RTP_TCP")
    protected Boolean rtptcp;
    @XmlElement(name="RTP_RTSP_TCP")
    protected Boolean rtprtsptcp;
    @XmlElement(name="Extension")
    protected RealTimeStreamingCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getRTPMulticast() {
        return this.rtpMulticast;
    }

    public void setRTPMulticast(Boolean value) {
        this.rtpMulticast = value;
    }

    public Boolean getRTPTCP() {
        return this.rtptcp;
    }

    public void setRTPTCP(Boolean value) {
        this.rtptcp = value;
    }

    public Boolean getRTPRTSPTCP() {
        return this.rtprtsptcp;
    }

    public void setRTPRTSPTCP(Boolean value) {
        this.rtprtsptcp = value;
    }

    public RealTimeStreamingCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RealTimeStreamingCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

