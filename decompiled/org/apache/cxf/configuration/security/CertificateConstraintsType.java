/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.DNConstraintsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CertificateConstraintsType", namespace="http://cxf.apache.org/configuration/security", propOrder={"subjectDNConstraints", "issuerDNConstraints"})
public class CertificateConstraintsType {
    @XmlElement(name="SubjectDNConstraints", namespace="http://cxf.apache.org/configuration/security")
    protected DNConstraintsType subjectDNConstraints;
    @XmlElement(name="IssuerDNConstraints", namespace="http://cxf.apache.org/configuration/security")
    protected DNConstraintsType issuerDNConstraints;

    public DNConstraintsType getSubjectDNConstraints() {
        return this.subjectDNConstraints;
    }

    public void setSubjectDNConstraints(DNConstraintsType value) {
        this.subjectDNConstraints = value;
    }

    public boolean isSetSubjectDNConstraints() {
        return this.subjectDNConstraints != null;
    }

    public DNConstraintsType getIssuerDNConstraints() {
        return this.issuerDNConstraints;
    }

    public void setIssuerDNConstraints(DNConstraintsType value) {
        this.issuerDNConstraints = value;
    }

    public boolean isSetIssuerDNConstraints() {
        return this.issuerDNConstraints != null;
    }
}

