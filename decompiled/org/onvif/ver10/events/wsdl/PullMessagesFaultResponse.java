/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"maxTimeout", "maxMessageLimit", "any"})
@XmlRootElement(name="PullMessagesFaultResponse")
public class PullMessagesFaultResponse {
    @XmlElement(name="MaxTimeout", required=true)
    protected Duration maxTimeout;
    @XmlElement(name="MaxMessageLimit")
    protected int maxMessageLimit;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public Duration getMaxTimeout() {
        return this.maxTimeout;
    }

    public void setMaxTimeout(Duration value) {
        this.maxTimeout = value;
    }

    public int getMaxMessageLimit() {
        return this.maxMessageLimit;
    }

    public void setMaxMessageLimit(int value) {
        this.maxMessageLimit = value;
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

