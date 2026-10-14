/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.KerberosTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class KerberosTokenAssertionState
extends TokenAssertionState {
    public KerberosTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            KerberosToken token = (KerberosToken)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.isRequireKeyIdentifierReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
            }
            if (token.getApReqTokenType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, token.getApReqTokenType().name()));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.KERBEROS_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof KerberosTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a KerberosTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        KerberosToken kerberosToken = (KerberosToken)abstractToken;
        KerberosTokenSecurityEvent kerberosTokenSecurityEvent = (KerberosTokenSecurityEvent)tokenSecurityEvent;
        KerberosServiceSecurityToken kerberosServiceSecurityToken = (KerberosServiceSecurityToken)kerberosTokenSecurityEvent.getSecurityToken();
        if (kerberosToken.getIssuerName() != null && !kerberosToken.getIssuerName().equals(kerberosTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + kerberosToken.getIssuerName() + ") didn't match with the one in the IssuedToken (" + kerberosTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (kerberosToken.isRequireKeyIdentifierReference()) {
            SecurityTokenConstants.KeyIdentifier kerberosKeyIdentifier = kerberosServiceSecurityToken.getKeyIdentifier();
            if (!WSSecurityTokenConstants.KEYIDENTIFIER_EMBEDDED_KEY_IDENTIFIER_REF.equals(kerberosKeyIdentifier)) {
                this.setErrorMessage("Policy enforces KeyIdentifierReference but we got " + kerberosKeyIdentifier);
                this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireKeyIdentifierReference"), this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if (kerberosToken.getApReqTokenType() != null) {
            switch (kerberosToken.getApReqTokenType()) {
                case WssKerberosV5ApReqToken11: {
                    if (!kerberosTokenSecurityEvent.isKerberosV5ApReqToken11()) {
                        this.setErrorMessage("Policy enforces " + (Object)((Object)kerberosToken.getApReqTokenType()));
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "WssKerberosV5ApReqToken11"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "WssKerberosV5ApReqToken11"));
                    break;
                }
                case WssGssKerberosV5ApReqToken11: {
                    if (!kerberosTokenSecurityEvent.isGssKerberosV5ApReqToken11()) {
                        this.setErrorMessage("Policy enforces " + (Object)((Object)kerberosToken.getApReqTokenType()));
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "WssGssKerberosV5ApReqToken11"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "WssGssKerberosV5ApReqToken11"));
                }
            }
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

