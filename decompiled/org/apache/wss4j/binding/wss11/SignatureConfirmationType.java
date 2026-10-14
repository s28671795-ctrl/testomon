/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SignatureConfirmationType")
public class SignatureConfirmationType {
    @XmlAttribute(name="Id", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    private String id;
    @XmlAttribute(name="Value", required=true)
    private byte[] value;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}

