/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;

public class NoSecuritySecurityEvent
extends SecurityEvent {
    public NoSecuritySecurityEvent() {
        super(WSSecurityEventConstants.NO_SECURITY);
    }
}

