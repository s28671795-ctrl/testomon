/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.HttpsToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.HttpsTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class HttpsTokenAssertionState
extends TokenAssertionState {
    public HttpsTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            HttpsToken token = (HttpsToken)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.getAuthenticationType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, token.getAuthenticationType().name()));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.HTTPS_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof HttpsTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a HttpsTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        HttpsTokenSecurityEvent httpsTokenSecurityEvent = (HttpsTokenSecurityEvent)tokenSecurityEvent;
        HttpsToken httpsToken = (HttpsToken)abstractToken;
        if (httpsToken.getIssuerName() != null && !httpsToken.getIssuerName().equals(httpsTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + httpsToken.getIssuerName() + ") didn't match with the one in the HttpsToken (" + httpsTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        if (!this.isInitiator() && httpsToken.getAuthenticationType() != null) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            switch (httpsToken.getAuthenticationType()) {
                case HttpBasicAuthentication: {
                    if (httpsTokenSecurityEvent.getAuthenticationType() != HttpsTokenSecurityEvent.AuthenticationType.HttpBasicAuthentication) {
                        this.setErrorMessage("Policy enforces HttpBasicAuthentication but we got " + (Object)((Object)httpsTokenSecurityEvent.getAuthenticationType()));
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "HttpBasicAuthentication"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "HttpBasicAuthentication"));
                    break;
                }
                case HttpDigestAuthentication: {
                    if (httpsTokenSecurityEvent.getAuthenticationType() != HttpsTokenSecurityEvent.AuthenticationType.HttpDigestAuthentication) {
                        this.setErrorMessage("Policy enforces HttpDigestAuthentication but we got " + (Object)((Object)httpsTokenSecurityEvent.getAuthenticationType()));
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "HttpDigestAuthentication"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "HttpDigestAuthentication"));
                    break;
                }
                case RequireClientCertificate: {
                    if (httpsTokenSecurityEvent.getAuthenticationType() != HttpsTokenSecurityEvent.AuthenticationType.HttpsClientCertificateAuthentication) {
                        this.setErrorMessage("Policy enforces HttpClientCertificateAuthentication but we got " + (Object)((Object)httpsTokenSecurityEvent.getAuthenticationType()));
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireClientCertificate"), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireClientCertificate"));
                }
            }
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

