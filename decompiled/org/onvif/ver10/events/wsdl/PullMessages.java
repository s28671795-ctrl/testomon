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
@XmlType(name="", propOrder={"timeout", "messageLimit", "any"})
@XmlRootElement(name="PullMessages")
public class PullMessages {
    @XmlElement(name="Timeout", required=true)
    protected Duration timeout;
    @XmlElement(name="MessageLimit")
    protected int messageLimit;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public Duration getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Duration value) {
        this.timeout = value;
    }

    public int getMessageLimit() {
        return this.messageLimit;
    }

    public void setMessageLimit(int value) {
        this.messageLimit = value;
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

