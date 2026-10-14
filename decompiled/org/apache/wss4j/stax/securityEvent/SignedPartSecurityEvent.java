/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import java.util.List;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class SignedPartSecurityEvent
extends AbstractSecuredElementSecurityEvent {
    public SignedPartSecurityEvent(InboundSecurityToken inboundSecurityToken, boolean signed, List<XMLSecurityConstants.ContentType> protectionOrder) {
        super(WSSecurityEventConstants.SIGNED_PART, inboundSecurityToken, protectionOrder, signed, false);
    }
}

