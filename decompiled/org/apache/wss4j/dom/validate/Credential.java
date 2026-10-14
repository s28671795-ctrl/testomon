/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.security.auth.Subject;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.message.token.UsernameToken;

public class Credential {
    private PublicKey publicKey;
    private X509Certificate[] certs;
    private Timestamp timestamp;
    private UsernameToken usernametoken;
    private BinarySecurity binarySecurityToken;
    private SamlAssertionWrapper samlAssertion;
    private SamlAssertionWrapper transformedToken;
    private SecurityContextToken securityContextToken;
    private Principal principal;
    private byte[] secretKey;
    private Subject subject;
    private Object delegationCredential;

    public void setSecurityContextToken(SecurityContextToken securityContextToken) {
        this.securityContextToken = securityContextToken;
    }

    public SecurityContextToken getSecurityContextToken() {
        return this.securityContextToken;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getSecretKey() {
        return this.secretKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setCertificates(X509Certificate[] certs) {
        this.certs = certs;
    }

    public X509Certificate[] getCertificates() {
        return this.certs;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setUsernametoken(UsernameToken usernametoken) {
        this.usernametoken = usernametoken;
    }

    public UsernameToken getUsernametoken() {
        return this.usernametoken;
    }

    public void setBinarySecurityToken(BinarySecurity binarySecurityToken) {
        this.binarySecurityToken = binarySecurityToken;
    }

    public BinarySecurity getBinarySecurityToken() {
        return this.binarySecurityToken;
    }

    public void setSamlAssertion(SamlAssertionWrapper samlAssertion) {
        this.samlAssertion = samlAssertion;
    }

    public SamlAssertionWrapper getSamlAssertion() {
        return this.samlAssertion;
    }

    public void setTransformedToken(SamlAssertionWrapper transformedToken) {
        this.transformedToken = transformedToken;
    }

    public SamlAssertionWrapper getTransformedToken() {
        return this.transformedToken;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Principal getPrincipal() {
        return this.principal;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public Object getDelegationCredential() {
        return this.delegationCredential;
    }

    public void setDelegationCredential(Object delegationCredential) {
        this.delegationCredential = delegationCredential;
    }
}

