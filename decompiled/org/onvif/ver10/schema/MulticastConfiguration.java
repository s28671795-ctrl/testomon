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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPAddress;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MulticastConfiguration", propOrder={"address", "port", "ttl", "autoStart", "any"})
public class MulticastConfiguration {
    @XmlElement(name="Address", required=true)
    protected IPAddress address;
    @XmlElement(name="Port")
    protected int port;
    @XmlElement(name="TTL")
    protected int ttl;
    @XmlElement(name="AutoStart")
    protected boolean autoStart;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IPAddress getAddress() {
        return this.address;
    }

    public void setAddress(IPAddress value) {
        this.address = value;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int value) {
        this.port = value;
    }

    public int getTTL() {
        return this.ttl;
    }

    public void setTTL(int value) {
        this.ttl = value;
    }

    public boolean getAutoStart() {
        return this.autoStart;
    }

    public void setAutoStart(boolean value) {
        this.autoStart = value;
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

