/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPAddress;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"fromDHCP", "searchDomain", "dnsManual"})
@XmlRootElement(name="SetDNS")
public class SetDNS {
    @XmlElement(name="FromDHCP")
    protected boolean fromDHCP;
    @XmlElement(name="SearchDomain")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected List<String> searchDomain;
    @XmlElement(name="DNSManual")
    protected List<IPAddress> dnsManual;

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

    public List<IPAddress> getDNSManual() {
        if (this.dnsManual == null) {
            this.dnsManual = new ArrayList<IPAddress>();
        }
        return this.dnsManual;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

