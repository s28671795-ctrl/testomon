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
import org.onvif.ver10.schema.NetworkCapabilitiesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkCapabilities", propOrder={"ipFilter", "zeroConfiguration", "ipVersion6", "dynDNS", "extension"})
public class NetworkCapabilities {
    @XmlElement(name="IPFilter")
    protected Boolean ipFilter;
    @XmlElement(name="ZeroConfiguration")
    protected Boolean zeroConfiguration;
    @XmlElement(name="IPVersion6")
    protected Boolean ipVersion6;
    @XmlElement(name="DynDNS")
    protected Boolean dynDNS;
    @XmlElement(name="Extension")
    protected NetworkCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getIPFilter() {
        return this.ipFilter;
    }

    public void setIPFilter(Boolean value) {
        this.ipFilter = value;
    }

    public Boolean getZeroConfiguration() {
        return this.zeroConfiguration;
    }

    public void setZeroConfiguration(Boolean value) {
        this.zeroConfiguration = value;
    }

    public Boolean getIPVersion6() {
        return this.ipVersion6;
    }

    public void setIPVersion6(Boolean value) {
        this.ipVersion6 = value;
    }

    public Boolean getDynDNS() {
        return this.dynDNS;
    }

    public void setDynDNS(Boolean value) {
        this.dynDNS = value;
    }

    public NetworkCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

