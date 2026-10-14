/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.securityToken.SecureConversationSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class SecureConversationSecurityTokenImpl
extends AbstractInboundSecurityToken
implements SecureConversationSecurityToken {
    public SecureConversationSecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier) {
        super(wsInboundSecurityContext, id, keyIdentifier, false);
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN;
    }
}

