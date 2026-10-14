/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.replay.wsdl;

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
    @XmlAttribute(name="ReversePlayback")
    protected Boolean reversePlayback;
    @XmlAttribute(name="SessionTimeoutRange")
    protected List<Float> sessionTimeoutRange;
    @XmlAttribute(name="RTP_RTSP_TCP")
    protected Boolean rtprtsptcp;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public boolean getReversePlayback() {
        if (this.reversePlayback == null) {
            return false;
        }
        return this.reversePlayback;
    }

    public void setReversePlayback(Boolean value) {
        this.reversePlayback = value;
    }

    public List<Float> getSessionTimeoutRange() {
        if (this.sessionTimeoutRange == null) {
            this.sessionTimeoutRange = new ArrayList<Float>();
        }
        return this.sessionTimeoutRange;
    }

    public Boolean getRTPRTSPTCP() {
        return this.rtprtsptcp;
    }

    public void setRTPRTSPTCP(Boolean value) {
        this.rtprtsptcp = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

