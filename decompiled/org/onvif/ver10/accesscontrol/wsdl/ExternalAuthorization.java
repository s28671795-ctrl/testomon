/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accesscontrol.wsdl.Decision;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"accessPointToken", "credentialToken", "reason", "decision"})
@XmlRootElement(name="ExternalAuthorization")
public class ExternalAuthorization {
    @XmlElement(name="AccessPointToken", required=true)
    protected String accessPointToken;
    @XmlElement(name="CredentialToken")
    protected String credentialToken;
    @XmlElement(name="Reason")
    protected String reason;
    @XmlElement(name="Decision", required=true)
    @XmlSchemaType(name="string")
    protected Decision decision;

    public String getAccessPointToken() {
        return this.accessPointToken;
    }

    public void setAccessPointToken(String value) {
        this.accessPointToken = value;
    }

    public String getCredentialToken() {
        return this.credentialToken;
    }

    public void setCredentialToken(String value) {
        this.credentialToken = value;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }

    public Decision getDecision() {
        return this.decision;
    }

    public void setDecision(Decision value) {
        this.decision = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

