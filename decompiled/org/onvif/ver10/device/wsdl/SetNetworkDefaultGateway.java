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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"iPv4Address", "iPv6Address"})
@XmlRootElement(name="SetNetworkDefaultGateway")
public class SetNetworkDefaultGateway {
    @XmlElement(name="IPv4Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected List<String> iPv4Address;
    @XmlElement(name="IPv6Address")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected List<String> iPv6Address;

    public List<String> getIPv4Address() {
        if (this.iPv4Address == null) {
            this.iPv4Address = new ArrayList<String>();
        }
        return this.iPv4Address;
    }

    public List<String> getIPv6Address() {
        if (this.iPv6Address == null) {
            this.iPv6Address = new ArrayList<String>();
        }
        return this.iPv6Address;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

