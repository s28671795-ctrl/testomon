/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractToken;

public abstract class AbstractSecurityPolicyValidator
implements SecurityPolicyValidator {
    protected boolean isTokenRequired(AbstractToken token, Message message) {
        SPConstants.IncludeTokenType inclusion = token.getIncludeTokenType();
        if (inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
            return false;
        }
        if (inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS) {
            return true;
        }
        boolean initiator = MessageUtils.isRequestor(message);
        if (initiator && inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR) {
            return true;
        }
        return !initiator && (inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ONCE || inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT);
    }
}

