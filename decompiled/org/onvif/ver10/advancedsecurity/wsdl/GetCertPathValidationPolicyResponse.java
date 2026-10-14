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
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationPolicy;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certPathValidationPolicy"})
@XmlRootElement(name="GetCertPathValidationPolicyResponse")
public class GetCertPathValidationPolicyResponse {
    @XmlElement(name="CertPathValidationPolicy", required=true)
    protected CertPathValidationPolicy certPathValidationPolicy;

    public CertPathValidationPolicy getCertPathValidationPolicy() {
        return this.certPathValidationPolicy;
    }

    public void setCertPathValidationPolicy(CertPathValidationPolicy value) {
        this.certPathValidationPolicy = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

