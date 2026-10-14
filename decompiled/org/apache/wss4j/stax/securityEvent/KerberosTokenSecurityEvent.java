/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.IssuedTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;

public class KerberosTokenSecurityEvent
extends IssuedTokenSecurityEvent<KerberosServiceSecurityToken> {
    private String issuerName;

    public KerberosTokenSecurityEvent() {
        super(WSSecurityEventConstants.KERBEROS_TOKEN);
    }

    @Override
    public String getIssuerName() {
        return this.issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public boolean isKerberosV5ApReqToken11() {
        String type = ((KerberosServiceSecurityToken)this.getSecurityToken()).getKerberosTokenValueType();
        return "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ1510".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ4120".equals(type);
    }

    public boolean isGssKerberosV5ApReqToken11() {
        String type = ((KerberosServiceSecurityToken)this.getSecurityToken()).getKerberosTokenValueType();
        return "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ4120".equals(type);
    }

    public String getKerberosTokenValueType() {
        return ((KerberosServiceSecurityToken)this.getSecurityToken()).getKerberosTokenValueType();
    }
}

