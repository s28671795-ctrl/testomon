/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"any"})
public class Capabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="RTP_Multicast")
    protected Boolean rtpMulticast;
    @XmlAttribute(name="RTP_TCP")
    protected Boolean rtptcp;
    @XmlAttribute(name="RTP_RTSP_TCP")
    protected Boolean rtprtsptcp;
    @XmlAttribute(name="SupportedReceivers", required=true)
    protected int supportedReceivers;
    @XmlAttribute(name="MaximumRTSPURILength")
    protected Integer maximumRTSPURILength;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

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

    public int getSupportedReceivers() {
        return this.supportedReceivers;
    }

    public void setSupportedReceivers(int value) {
        this.supportedReceivers = value;
    }

    public Integer getMaximumRTSPURILength() {
        return this.maximumRTSPURILength;
    }

    public void setMaximumRTSPURILength(Integer value) {
        this.maximumRTSPURILength = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

