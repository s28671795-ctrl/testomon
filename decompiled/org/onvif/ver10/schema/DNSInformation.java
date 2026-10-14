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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DNSInformationExtension;
import org.onvif.ver10.schema.IPAddress;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DNSInformation", propOrder={"fromDHCP", "searchDomain", "dnsFromDHCP", "dnsManual", "extension"})
public class DNSInformation {
    @XmlElement(name="FromDHCP")
    protected boolean fromDHCP;
    @XmlElement(name="SearchDomain")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected List<String> searchDomain;
    @XmlElement(name="DNSFromDHCP")
    protected List<IPAddress> dnsFromDHCP;
    @XmlElement(name="DNSManual")
    protected List<IPAddress> dnsManual;
    @XmlElement(name="Extension")
    protected DNSInformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getFromDHCP() {
        return this.fromDHCP;
    }

    public void setFromDHCP(boolean value) {
        this.fromDHCP = value;
    }

    public List<String> getSearchDomain() {
        if (this.searchDomain == null) {
            this.searchDomain = new ArrayList<String>();
        }
        return this.searchDomain;
    }

    public List<IPAddress> getDNSFromDHCP() {
        if (this.dnsFromDHCP == null) {
            this.dnsFromDHCP = new ArrayList<IPAddress>();
        }
        return this.dnsFromDHCP;
    }

    public List<IPAddress> getDNSManual() {
        if (this.dnsManual == null) {
            this.dnsManual = new ArrayList<IPAddress>();
        }
        return this.dnsManual;
    }

    public DNSInformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(DNSInformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

