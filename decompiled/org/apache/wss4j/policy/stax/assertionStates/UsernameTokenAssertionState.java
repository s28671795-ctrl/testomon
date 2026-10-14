/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.UsernameTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class UsernameTokenAssertionState
extends TokenAssertionState {
    public UsernameTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            UsernameToken usernameToken = (UsernameToken)this.getAssertion();
            String namespace = usernameToken.getName().getNamespaceURI();
            if (usernameToken.getPasswordType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, usernameToken.getPasswordType().name()));
            }
            if (usernameToken.isCreated()) {
                this.getPolicyAsserter().assertPolicy(SP13Constants.CREATED);
            }
            if (usernameToken.isNonce()) {
                this.getPolicyAsserter().assertPolicy(SP13Constants.NONCE);
            }
            if (usernameToken.getUsernameTokenType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, usernameToken.getUsernameTokenType().name()));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.USERNAME_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException, XMLSecurityException {
        if (!(tokenSecurityEvent instanceof UsernameTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a UsernameSecurityTokenEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        UsernameSecurityToken usernameSecurityToken = (UsernameSecurityToken)tokenSecurityEvent.getSecurityToken();
        UsernameTokenSecurityEvent usernameTokenSecurityEvent = (UsernameTokenSecurityEvent)tokenSecurityEvent;
        UsernameToken usernameToken = (UsernameToken)abstractToken;
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (usernameToken.getPasswordType() != null) {
            switch (usernameToken.getPasswordType()) {
                case NoPassword: {
                    if (usernameTokenSecurityEvent.getUsernameTokenPasswordType() != WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE) {
                        this.setErrorMessage("UsernameToken contains a password but the policy prohibits it");
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "NoPassword"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "NoPassword"));
                    break;
                }
                case HashPassword: {
                    if (usernameTokenSecurityEvent.getUsernameTokenPasswordType() != WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST) {
                        this.setErrorMessage("UsernameToken does not contain a hashed password");
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "HashPassword"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "HashPassword"));
                }
            }
        } else {
            if (usernameTokenSecurityEvent.getUsernameTokenPasswordType() == WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE) {
                this.setErrorMessage("UsernameToken must contain a password");
                this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                return false;
            }
            if (usernameTokenSecurityEvent.getUsernameTokenPasswordType() == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST) {
                this.setErrorMessage("UsernameToken password must not be hashed");
                this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                return false;
            }
        }
        if (usernameToken.isCreated()) {
            if (usernameSecurityToken.getCreatedTime() == null || usernameTokenSecurityEvent.getUsernameTokenPasswordType() != WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT) {
                this.setErrorMessage("UsernameToken does not contain a created timestamp or password is not plain text");
                this.getPolicyAsserter().unassertPolicy(SP13Constants.CREATED, this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(SP13Constants.CREATED);
        }
        if (usernameToken.isNonce()) {
            if (usernameSecurityToken.getNonce() == null || usernameTokenSecurityEvent.getUsernameTokenPasswordType() != WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT) {
                this.setErrorMessage("UsernameToken does not contain a nonce or password is not plain text");
                this.getPolicyAsserter().unassertPolicy(SP13Constants.NONCE, this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(SP13Constants.NONCE);
        }
        if (usernameToken.getUsernameTokenType() != null) {
            switch (usernameToken.getUsernameTokenType()) {
                case WssUsernameToken10: {
                    if (usernameTokenSecurityEvent.getUsernameTokenProfile() != null && usernameTokenSecurityEvent.getUsernameTokenProfile().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0")) {
                        this.setErrorMessage("Policy enforces UsernameToken profile 1.0 but we got 1.1");
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "WssUsernameToken10"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "WssUsernameToken10"));
                    break;
                }
                case WssUsernameToken11: {
                    if (usernameTokenSecurityEvent.getUsernameTokenProfile() != null && !usernameTokenSecurityEvent.getUsernameTokenProfile().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0")) {
                        this.setErrorMessage("Policy enforces UsernameToken profile 1.1 but we got 1.0");
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "WssUsernameToken11"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "WssUsernameToken11"));
                }
            }
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

