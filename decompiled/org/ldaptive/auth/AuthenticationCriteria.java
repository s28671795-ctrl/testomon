/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.Credential;
import org.ldaptive.auth.AuthenticationRequest;

public class AuthenticationCriteria {
    private String authenticationDn;
    private AuthenticationRequest authenticationRequest;

    public AuthenticationCriteria() {
    }

    public AuthenticationCriteria(String dn) {
        this.authenticationDn = dn;
    }

    public AuthenticationCriteria(String dn, AuthenticationRequest request) {
        this.authenticationDn = dn;
        this.authenticationRequest = request;
    }

    public String getDn() {
        return this.authenticationDn;
    }

    public void setDn(String dn) {
        this.authenticationDn = dn;
    }

    public Credential getCredential() {
        return this.authenticationRequest.getCredential();
    }

    public AuthenticationRequest getAuthenticationRequest() {
        return this.authenticationRequest;
    }

    public void setAuthenticationRequest(AuthenticationRequest request) {
        this.authenticationRequest = request;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "dn=" + this.authenticationDn + ", " + "authenticationRequest=" + this.authenticationRequest + "]";
    }
}

