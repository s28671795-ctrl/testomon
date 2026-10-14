/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.xmldsig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RSAKeyValueType", propOrder={"modulus", "exponent"})
public class RSAKeyValueType {
    @XmlElement(name="Modulus", required=true)
    protected byte[] modulus;
    @XmlElement(name="Exponent", required=true)
    protected byte[] exponent;

    public byte[] getModulus() {
        return this.modulus;
    }

    public void setModulus(byte[] value) {
        this.modulus = value;
    }

    public byte[] getExponent() {
        return this.exponent;
    }

    public void setExponent(byte[] value) {
        this.exponent = value;
    }
}

