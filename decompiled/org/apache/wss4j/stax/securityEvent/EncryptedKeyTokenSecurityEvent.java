/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class EncryptedKeyTokenSecurityEvent
extends TokenSecurityEvent<SecurityToken> {
    public EncryptedKeyTokenSecurityEvent() {
        super(WSSecurityEventConstants.EncryptedKeyToken);
    }
}

