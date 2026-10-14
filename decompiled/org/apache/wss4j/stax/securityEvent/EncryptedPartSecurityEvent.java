/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import java.util.List;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class EncryptedPartSecurityEvent
extends AbstractSecuredElementSecurityEvent {
    public EncryptedPartSecurityEvent(InboundSecurityToken inboundSecurityToken, boolean encrypted, List<XMLSecurityConstants.ContentType> protectionOrder) {
        super(WSSecurityEventConstants.ENCRYPTED_PART, inboundSecurityToken, protectionOrder, false, encrypted);
    }
}

