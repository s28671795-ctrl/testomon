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
import org.onvif.ver10.advancedsecurity.wsdl.X509Certificate;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificate"})
@XmlRootElement(name="GetCertificateResponse")
public class GetCertificateResponse {
    @XmlElement(name="Certificate", required=true)
    protected X509Certificate certificate;

    public X509Certificate getCertificate() {
        return this.certificate;
    }

    public void setCertificate(X509Certificate value) {
        this.certificate = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

