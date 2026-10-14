/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecurityCapabilities")
public class SecurityCapabilities {
    @XmlAttribute(name="TLS1.0")
    protected Boolean tls10;
    @XmlAttribute(name="TLS1.1")
    protected Boolean tls11;
    @XmlAttribute(name="TLS1.2")
    protected Boolean tls12;
    @XmlAttribute(name="OnboardKeyGeneration")
    protected Boolean onboardKeyGeneration;
    @XmlAttribute(name="AccessPolicyConfig")
    protected Boolean accessPolicyConfig;
    @XmlAttribute(name="DefaultAccessPolicy")
    protected Boolean defaultAccessPolicy;
    @XmlAttribute(name="Dot1X")
    protected Boolean dot1X;
    @XmlAttribute(name="RemoteUserHandling")
    protected Boolean remoteUserHandling;
    @XmlAttribute(name="X.509Token")
    protected Boolean x509Token;
    @XmlAttribute(name="SAMLToken")
    protected Boolean samlToken;
    @XmlAttribute(name="KerberosToken")
    protected Boolean kerberosToken;
    @XmlAttribute(name="UsernameToken")
    protected Boolean usernameToken;
    @XmlAttribute(name="HttpDigest")
    protected Boolean httpDigest;
    @XmlAttribute(name="RELToken")
    protected Boolean relToken;
    @XmlAttribute(name="SupportedEAPMethods")
    protected List<Integer> supportedEAPMethods;
    @XmlAttribute(name="MaxUsers")
    protected Integer maxUsers;
    @XmlAttribute(name="MaxUserNameLength")
    protected Integer maxUserNameLength;
    @XmlAttribute(name="MaxPasswordLength")
    protected Integer maxPasswordLength;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getTLS10() {
        return this.tls10;
    }

    public void setTLS10(Boolean value) {
        this.tls10 = value;
    }

    public Boolean getTLS11() {
        return this.tls11;
    }

    public void setTLS11(Boolean value) {
        this.tls11 = value;
    }

    public Boolean getTLS12() {
        return this.tls12;
    }

    public void setTLS12(Boolean value) {
        this.tls12 = value;
    }

    public Boolean getOnboardKeyGeneration() {
        return this.onboardKeyGeneration;
    }

    public void setOnboardKeyGeneration(Boolean value) {
        this.onboardKeyGeneration = value;
    }

    public Boolean getAccessPolicyConfig() {
        return this.accessPolicyConfig;
    }

    public void setAccessPolicyConfig(Boolean value) {
        this.accessPolicyConfig = value;
    }

    public Boolean getDefaultAccessPolicy() {
        return this.defaultAccessPolicy;
    }

    public void setDefaultAccessPolicy(Boolean value) {
        this.defaultAccessPolicy = value;
    }

    public Boolean getDot1X() {
        return this.dot1X;
    }

    public void setDot1X(Boolean value) {
        this.dot1X = value;
    }

    public Boolean getRemoteUserHandling() {
        return this.remoteUserHandling;
    }

    public void setRemoteUserHandling(Boolean value) {
        this.remoteUserHandling = value;
    }

    public Boolean getX509Token() {
        return this.x509Token;
    }

    public void setX509Token(Boolean value) {
        this.x509Token = value;
    }

    public Boolean getSAMLToken() {
        return this.samlToken;
    }

    public void setSAMLToken(Boolean value) {
        this.samlToken = value;
    }

    public Boolean getKerberosToken() {
        return this.kerberosToken;
    }

    public void setKerberosToken(Boolean value) {
        this.kerberosToken = value;
    }

    public Boolean getUsernameToken() {
        return this.usernameToken;
    }

    public void setUsernameToken(Boolean value) {
        this.usernameToken = value;
    }

    public Boolean getHttpDigest() {
        return this.httpDigest;
    }

    public void setHttpDigest(Boolean value) {
        this.httpDigest = value;
    }

    public Boolean getRELToken() {
        return this.relToken;
    }

    public void setRELToken(Boolean value) {
        this.relToken = value;
    }

    public List<Integer> getSupportedEAPMethods() {
        if (this.supportedEAPMethods == null) {
            this.supportedEAPMethods = new ArrayList<Integer>();
        }
        return this.supportedEAPMethods;
    }

    public Integer getMaxUsers() {
        return this.maxUsers;
    }

    public void setMaxUsers(Integer value) {
        this.maxUsers = value;
    }

    public Integer getMaxUserNameLength() {
        return this.maxUserNameLength;
    }

    public void setMaxUserNameLength(Integer value) {
        this.maxUserNameLength = value;
    }

    public Integer getMaxPasswordLength() {
        return this.maxPasswordLength;
    }

    public void setMaxPasswordLength(Integer value) {
        this.maxPasswordLength = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

