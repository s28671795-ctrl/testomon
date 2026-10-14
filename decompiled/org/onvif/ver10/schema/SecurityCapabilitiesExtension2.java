/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecurityCapabilitiesExtension2", propOrder={"dot1X", "supportedEAPMethod", "remoteUserHandling", "any"})
public class SecurityCapabilitiesExtension2 {
    @XmlElement(name="Dot1X")
    protected boolean dot1X;
    @XmlElement(name="SupportedEAPMethod", type=Integer.class)
    protected List<Integer> supportedEAPMethod;
    @XmlElement(name="RemoteUserHandling")
    protected boolean remoteUserHandling;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public boolean getDot1X() {
        return this.dot1X;
    }

    public void setDot1X(boolean value) {
        this.dot1X = value;
    }

    public List<Integer> getSupportedEAPMethod() {
        if (this.supportedEAPMethod == null) {
            this.supportedEAPMethod = new ArrayList<Integer>();
        }
        return this.supportedEAPMethod;
    }

    public boolean getRemoteUserHandling() {
        return this.remoteUserHandling;
    }

    public void setRemoteUserHandling(boolean value) {
        this.remoteUserHandling = value;
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

