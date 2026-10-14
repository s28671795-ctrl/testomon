/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ReceiverCapabilities", propOrder={"xAddr", "rtpMulticast", "rtptcp", "rtprtsptcp", "supportedReceivers", "maximumRTSPURILength", "any"})
public class ReceiverCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="RTP_Multicast")
    protected boolean rtpMulticast;
    @XmlElement(name="RTP_TCP")
    protected boolean rtptcp;
    @XmlElement(name="RTP_RTSP_TCP")
    protected boolean rtprtsptcp;
    @XmlElement(name="SupportedReceivers")
    protected int supportedReceivers;
    @XmlElement(name="MaximumRTSPURILength")
    protected int maximumRTSPURILength;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public boolean getRTPMulticast() {
        return this.rtpMulticast;
    }

    public void setRTPMulticast(boolean value) {
        this.rtpMulticast = value;
    }

    public boolean getRTPTCP() {
        return this.rtptcp;
    }

    public void setRTPTCP(boolean value) {
        this.rtptcp = value;
    }

    public boolean getRTPRTSPTCP() {
        return this.rtprtsptcp;
    }

    public void setRTPRTSPTCP(boolean value) {
        this.rtprtsptcp = value;
    }

    public int getSupportedReceivers() {
        return this.supportedReceivers;
    }

    public void setSupportedReceivers(int value) {
        this.supportedReceivers = value;
    }

    public int getMaximumRTSPURILength() {
        return this.maximumRTSPURILength;
    }

    public void setMaximumRTSPURILength(int value) {
        this.maximumRTSPURILength = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

