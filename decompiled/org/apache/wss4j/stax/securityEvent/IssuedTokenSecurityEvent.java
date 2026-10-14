/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public abstract class IssuedTokenSecurityEvent<T extends SecurityToken>
extends TokenSecurityEvent<T> {
    public IssuedTokenSecurityEvent(SecurityEventConstants.Event securityEventType) {
        super(securityEventType);
    }

    public abstract String getIssuerName() throws WSSecurityException;
}

