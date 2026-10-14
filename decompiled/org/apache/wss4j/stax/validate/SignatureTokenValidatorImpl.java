/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.validate.SignatureTokenValidator;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class SignatureTokenValidatorImpl
implements SignatureTokenValidator {
    @Override
    public void validate(InboundSecurityToken inboundSecurityToken, WSSSecurityProperties wssSecurityProperties) throws WSSecurityException {
        try {
            inboundSecurityToken.verify();
        }
        catch (XMLSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, (Exception)e);
        }
    }
}

