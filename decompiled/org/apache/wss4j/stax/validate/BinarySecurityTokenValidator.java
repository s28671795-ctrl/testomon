/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public interface BinarySecurityTokenValidator
extends Validator {
    public InboundSecurityToken validate(BinarySecurityTokenType var1, TokenContext var2) throws WSSecurityException;
}

