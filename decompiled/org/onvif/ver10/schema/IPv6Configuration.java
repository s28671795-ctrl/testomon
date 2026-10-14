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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPv6ConfigurationExtension;
import org.onvif.ver10.schema.IPv6DHCPConfiguration;
import org.onvif.ver10.schema.PrefixedIPv6Address;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPv6Configuration", propOrder={"acceptRouterAdvert", "dhcp", "manual", "linkLocal", "fromDHCP", "fromRA", "extension"})
public class IPv6Configuration {
    @XmlElement(name="AcceptRouterAdvert")
    protected Boolean acceptRouterAdvert;
    @XmlElement(name="DHCP", required=true)
    @XmlSchemaType(name="string")
    protected IPv6DHCPConfiguration dhcp;
    @XmlElement(name="Manual")
    protected List<PrefixedIPv6Address> manual;
    @XmlElement(name="LinkLocal")
    protected List<PrefixedIPv6Address> linkLocal;
    @XmlElement(name="FromDHCP")
    protected List<PrefixedIPv6Address> fromDHCP;
    @XmlElement(name="FromRA")
    protected List<PrefixedIPv6Address> fromRA;
    @XmlElement(name="Extension")
    protected IPv6ConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getAcceptRouterAdvert() {
        return this.acceptRouterAdvert;
    }

    public void setAcceptRouterAdvert(Boolean value) {
        this.acceptRouterAdvert = value;
    }

    public IPv6DHCPConfiguration getDHCP() {
        return this.dhcp;
    }

    public void setDHCP(IPv6DHCPConfiguration value) {
        this.dhcp = value;
    }

    public List<PrefixedIPv6Address> getManual() {
        if (this.manual == null) {
            this.manual = new ArrayList<PrefixedIPv6Address>();
        }
        return this.manual;
    }

    public List<PrefixedIPv6Address> getLinkLocal() {
        if (this.linkLocal == null) {
            this.linkLocal = new ArrayList<PrefixedIPv6Address>();
        }
        return this.linkLocal;
    }

    public List<PrefixedIPv6Address> getFromDHCP() {
        if (this.fromDHCP == null) {
            this.fromDHCP = new ArrayList<PrefixedIPv6Address>();
        }
        return this.fromDHCP;
    }

    public List<PrefixedIPv6Address> getFromRA() {
        if (this.fromRA == null) {
            this.fromRA = new ArrayList<PrefixedIPv6Address>();
        }
        return this.fromRA;
    }

    public IPv6ConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(IPv6ConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

