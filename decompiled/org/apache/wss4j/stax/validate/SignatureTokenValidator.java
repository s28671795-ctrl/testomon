/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public interface SignatureTokenValidator
extends Validator {
    public void validate(InboundSecurityToken var1, WSSSecurityProperties var2) throws WSSecurityException;
}

