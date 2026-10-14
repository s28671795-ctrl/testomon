/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.List;
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
@XmlRootElement(name="GetAllCertPathValidationPoliciesResponse")
public class GetAllCertPathValidationPoliciesResponse {
    @XmlElement(name="CertPathValidationPolicy")
    protected List<CertPathValidationPolicy> certPathValidationPolicy;

    public List<CertPathValidationPolicy> getCertPathValidationPolicy() {
        if (this.certPathValidationPolicy == null) {
            this.certPathValidationPolicy = new ArrayList<CertPathValidationPolicy>();
        }
        return this.certPathValidationPolicy;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

