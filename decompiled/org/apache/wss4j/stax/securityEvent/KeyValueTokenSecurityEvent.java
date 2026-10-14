/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.KeyValueSecurityToken;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;

public class KeyValueTokenSecurityEvent
extends TokenSecurityEvent<KeyValueSecurityToken> {
    public KeyValueTokenSecurityEvent() {
        super(WSSecurityEventConstants.KeyValueToken);
    }
}

