/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkCapabilities")
public class NetworkCapabilities {
    @XmlAttribute(name="IPFilter")
    protected Boolean ipFilter;
    @XmlAttribute(name="ZeroConfiguration")
    protected Boolean zeroConfiguration;
    @XmlAttribute(name="IPVersion6")
    protected Boolean ipVersion6;
    @XmlAttribute(name="DynDNS")
    protected Boolean dynDNS;
    @XmlAttribute(name="Dot11Configuration")
    protected Boolean dot11Configuration;
    @XmlAttribute(name="Dot1XConfigurations")
    protected Integer dot1XConfigurations;
    @XmlAttribute(name="HostnameFromDHCP")
    protected Boolean hostnameFromDHCP;
    @XmlAttribute(name="NTP")
    protected Integer ntp;
    @XmlAttribute(name="DHCPv6")
    protected Boolean dhcPv6;
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

    public Boolean getDot11Configuration() {
        return this.dot11Configuration;
    }

    public void setDot11Configuration(Boolean value) {
        this.dot11Configuration = value;
    }

    public Integer getDot1XConfigurations() {
        return this.dot1XConfigurations;
    }

    public void setDot1XConfigurations(Integer value) {
        this.dot1XConfigurations = value;
    }

    public Boolean getHostnameFromDHCP() {
        return this.hostnameFromDHCP;
    }

    public void setHostnameFromDHCP(Boolean value) {
        this.hostnameFromDHCP = value;
    }

    public Integer getNTP() {
        return this.ntp;
    }

    public void setNTP(Integer value) {
        this.ntp = value;
    }

    public Boolean getDHCPv6() {
        return this.dhcPv6;
    }

    public void setDHCPv6(Boolean value) {
        this.dhcPv6 = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

