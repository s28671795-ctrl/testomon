/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.binding.wssc.AbstractSecurityContextTokenType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public interface SecurityContextTokenValidator
extends Validator {
    public InboundSecurityToken validate(AbstractSecurityContextTokenType var1, String var2, TokenContext var3) throws WSSecurityException;
}

