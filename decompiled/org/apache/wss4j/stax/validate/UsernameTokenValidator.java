/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.binding.wss10.UsernameTokenType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public interface UsernameTokenValidator
extends Validator {
    public <T extends UsernameSecurityToken & InboundSecurityToken> T validate(UsernameTokenType var1, TokenContext var2) throws WSSecurityException;
}

