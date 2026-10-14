/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AuthorizationPolicy", namespace="http://cxf.apache.org/configuration/security", propOrder={"userName", "password", "authorizationType", "authorization"})
@XmlSeeAlso(value={ProxyAuthorizationPolicy.class})
public class AuthorizationPolicy {
    @XmlElement(name="UserName", namespace="http://cxf.apache.org/configuration/security")
    protected String userName;
    @XmlElement(name="Password", namespace="http://cxf.apache.org/configuration/security")
    protected String password;
    @XmlElement(name="AuthorizationType", namespace="http://cxf.apache.org/configuration/security")
    protected String authorizationType;
    @XmlElement(name="Authorization", namespace="http://cxf.apache.org/configuration/security")
    protected String authorization;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    public boolean isSetUserName() {
        return this.userName != null;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public boolean isSetPassword() {
        return this.password != null;
    }

    public String getAuthorizationType() {
        return this.authorizationType;
    }

    public void setAuthorizationType(String value) {
        this.authorizationType = value;
    }

    public boolean isSetAuthorizationType() {
        return this.authorizationType != null;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String value) {
        this.authorization = value;
    }

    public boolean isSetAuthorization() {
        return this.authorization != null;
    }
}

