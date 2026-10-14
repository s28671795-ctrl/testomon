/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.X509SecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class X509DefaultSecurityTokenImpl
extends X509SecurityTokenImpl {
    private String alias;

    X509DefaultSecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, Crypto crypto, CallbackHandler callbackHandler, String alias, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties) {
        super(WSSecurityTokenConstants.X509V3Token, wsInboundSecurityContext, crypto, callbackHandler, id, keyIdentifier, securityProperties, false);
        this.alias = alias;
    }

    @Override
    protected String getAlias() throws XMLSecurityException {
        return this.alias;
    }
}

