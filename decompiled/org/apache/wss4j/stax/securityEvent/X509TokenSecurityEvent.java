/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.X509SecurityToken;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;

public class X509TokenSecurityEvent
extends TokenSecurityEvent<X509SecurityToken> {
    public X509TokenSecurityEvent() {
        super(WSSecurityEventConstants.X509Token);
    }
}

