/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipal;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.securityEvent.IssuedTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;

public class SamlTokenSecurityEvent
extends IssuedTokenSecurityEvent<SamlSecurityToken> {
    public SamlTokenSecurityEvent() {
        super(WSSecurityEventConstants.SAML_TOKEN);
    }

    @Override
    public String getIssuerName() throws WSSecurityException {
        return this.getSamlAssertionWrapper().getIssuerString();
    }

    public SamlAssertionWrapper getSamlAssertionWrapper() throws WSSecurityException {
        return ((SAMLTokenPrincipal)((SamlSecurityToken)this.getSecurityToken()).getPrincipal()).getToken();
    }
}

