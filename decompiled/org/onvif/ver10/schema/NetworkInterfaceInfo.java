/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceInfo", propOrder={"name", "hwAddress", "mtu"})
public class NetworkInterfaceInfo {
    @XmlElement(name="Name")
    protected String name;
    @XmlElement(name="HwAddress", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String hwAddress;
    @XmlElement(name="MTU")
    protected Integer mtu;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getHwAddress() {
        return this.hwAddress;
    }

    public void setHwAddress(String value) {
        this.hwAddress = value;
    }

    public Integer getMTU() {
        return this.mtu;
    }

    public void setMTU(Integer value) {
        this.mtu = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

