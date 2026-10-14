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
import org.onvif.ver10.schema.PrefixedIPv4Address;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPv4Configuration", propOrder={"manual", "linkLocal", "fromDHCP", "dhcp", "any"})
public class IPv4Configuration {
    @XmlElement(name="Manual")
    protected List<PrefixedIPv4Address> manual;
    @XmlElement(name="LinkLocal")
    protected PrefixedIPv4Address linkLocal;
    @XmlElement(name="FromDHCP")
    protected PrefixedIPv4Address fromDHCP;
    @XmlElement(name="DHCP")
    protected boolean dhcp;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<PrefixedIPv4Address> getManual() {
        if (this.manual == null) {
            this.manual = new ArrayList<PrefixedIPv4Address>();
        }
        return this.manual;
    }

    public PrefixedIPv4Address getLinkLocal() {
        return this.linkLocal;
    }

    public void setLinkLocal(PrefixedIPv4Address value) {
        this.linkLocal = value;
    }

    public PrefixedIPv4Address getFromDHCP() {
        return this.fromDHCP;
    }

    public void setFromDHCP(PrefixedIPv4Address value) {
        this.fromDHCP = value;
    }

    public boolean getDHCP() {
        return this.dhcp;
    }

    public void setDHCP(boolean value) {
        this.dhcp = value;
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

