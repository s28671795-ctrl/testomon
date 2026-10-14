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
@XmlType(name="PrefixedIPv4Address", propOrder={"address", "prefixLength"})
public class PrefixedIPv4Address {
    @XmlElement(name="Address", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String address;
    @XmlElement(name="PrefixLength")
    protected int prefixLength;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public int getPrefixLength() {
        return this.prefixLength;
    }

    public void setPrefixLength(int value) {
        this.prefixLength = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

