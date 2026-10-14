/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.validate.SignatureTokenValidator;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public interface SamlTokenValidator
extends SignatureTokenValidator {
    public <T extends SamlSecurityToken & InboundSecurityToken> T validate(SamlAssertionWrapper var1, InboundSecurityToken var2, TokenContext var3) throws WSSecurityException;
}

