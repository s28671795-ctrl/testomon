/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import java.security.Key;
import java.security.Principal;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;

public class KerberosServiceContext {
    private Principal principal;
    private Key sessionKey;
    private GSSCredential delegationCredential;
    private GSSContext gssContext;
    private byte[] kerberosToken;

    public Principal getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Key getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(Key sessionKey) {
        this.sessionKey = sessionKey;
    }

    public GSSCredential getDelegationCredential() {
        return this.delegationCredential;
    }

    public void setDelegationCredential(GSSCredential delegationCredential) {
        this.delegationCredential = delegationCredential;
    }

    public GSSContext getGssContext() {
        return this.gssContext;
    }

    public void setGssContext(GSSContext gssContext) {
        this.gssContext = gssContext;
    }

    public byte[] getKerberosToken() {
        return this.kerberosToken;
    }

    public void setKerberosToken(byte[] kerberosToken) {
        this.kerberosToken = kerberosToken;
    }
}

