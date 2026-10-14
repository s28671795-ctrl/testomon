/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.AbstractElementSecurityEvent;

public class RequiredElementSecurityEvent
extends AbstractElementSecurityEvent {
    public RequiredElementSecurityEvent() {
        super(WSSecurityEventConstants.REQUIRED_ELEMENT);
    }
}

