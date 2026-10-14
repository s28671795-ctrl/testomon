/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pkcs10CSR"})
@XmlRootElement(name="CreatePKCS10CSRResponse")
public class CreatePKCS10CSRResponse {
    @XmlElement(name="PKCS10CSR", required=true)
    protected byte[] pkcs10CSR;

    public byte[] getPKCS10CSR() {
        return this.pkcs10CSR;
    }

    public void setPKCS10CSR(byte[] value) {
        this.pkcs10CSR = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

