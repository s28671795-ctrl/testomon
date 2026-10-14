/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.xmldsig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SignatureValueType", propOrder={"value"})
public class SignatureValueType {
    @XmlValue
    protected byte[] value;

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}

