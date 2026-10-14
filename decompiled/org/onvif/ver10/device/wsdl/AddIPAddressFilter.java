/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPAddressFilter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ipAddressFilter"})
@XmlRootElement(name="AddIPAddressFilter")
public class AddIPAddressFilter {
    @XmlElement(name="IPAddressFilter", required=true)
    protected IPAddressFilter ipAddressFilter;

    public IPAddressFilter getIPAddressFilter() {
        return this.ipAddressFilter;
    }

    public void setIPAddressFilter(IPAddressFilter value) {
        this.ipAddressFilter = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

