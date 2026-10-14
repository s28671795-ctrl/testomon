/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.secext.SecurityTokenReferenceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RequestedReferenceType", propOrder={"securityTokenReference"})
public class RequestedReferenceType {
    @XmlElement(name="SecurityTokenReference", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", required=true)
    protected SecurityTokenReferenceType securityTokenReference = new SecurityTokenReferenceType();

    public SecurityTokenReferenceType getSecurityTokenReference() {
        return this.securityTokenReference;
    }

    public void setSecurityTokenReference(SecurityTokenReferenceType value) {
        this.securityTokenReference = value;
    }
}

