/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.security.cert.X509Certificate;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.X509TokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class X509TokenAssertionState
extends TokenAssertionState {
    public X509TokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            X509Token token = (X509Token)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.isRequireKeyIdentifierReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
            } else if (token.isRequireIssuerSerialReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireIssuerSerialReference"));
            } else if (token.isRequireEmbeddedTokenReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireEmbeddedTokenReference"));
            } else if (token.isRequireThumbprintReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireThumbprintReference"));
            }
            if (token.getTokenType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, token.getTokenType().name()));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{SecurityEventConstants.X509Token};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException, XMLSecurityException {
        if (!(tokenSecurityEvent instanceof X509TokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a X509TokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        X509Token x509Token = (X509Token)abstractToken;
        SecurityToken securityToken = tokenSecurityEvent.getSecurityToken();
        SecurityTokenConstants.TokenType tokenType = securityToken.getTokenType();
        if (!(WSSecurityTokenConstants.X509V3Token.equals(tokenType) || WSSecurityTokenConstants.X509V1Token.equals(tokenType) || WSSecurityTokenConstants.X509Pkcs7Token.equals(tokenType) || WSSecurityTokenConstants.X509PkiPathV1Token.equals(tokenType))) {
            throw new WSSPolicyException("Invalid Token for this assertion");
        }
        try {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            X509Certificate x509Certificate = securityToken.getX509Certificates()[0];
            if (x509Token.getIssuerName() != null) {
                String certificateIssuerName = x509Certificate.getIssuerX500Principal().getName();
                if (!x509Token.getIssuerName().equals(certificateIssuerName)) {
                    this.setErrorMessage("IssuerName in Policy (" + x509Token.getIssuerName() + ") didn't match with the one in the certificate (" + certificateIssuerName + ")");
                    this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                    return false;
                }
            }
            if (x509Token.isRequireKeyIdentifierReference()) {
                if (!WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(securityToken.getKeyIdentifier()) && !WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier.equals(securityToken.getKeyIdentifier())) {
                    this.setErrorMessage("Policy enforces KeyIdentifierReference but we got " + securityToken.getKeyIdentifier());
                    this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireKeyIdentifierReference"), this.getErrorMessage());
                    return false;
                }
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
            } else if (x509Token.isRequireIssuerSerialReference()) {
                if (!WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(securityToken.getKeyIdentifier())) {
                    this.setErrorMessage("Policy enforces IssuerSerialReference but we got " + securityToken.getKeyIdentifier());
                    this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireIssuerSerialReference"), this.getErrorMessage());
                    return false;
                }
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireIssuerSerialReference"));
            } else if (x509Token.isRequireEmbeddedTokenReference()) {
                if (!WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(securityToken.getKeyIdentifier())) {
                    this.setErrorMessage("Policy enforces EmbeddedTokenReference but we got " + securityToken.getKeyIdentifier());
                    this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireEmbeddedTokenReference"), this.getErrorMessage());
                    return false;
                }
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireEmbeddedTokenReference"));
            } else if (x509Token.isRequireThumbprintReference()) {
                if (!WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(securityToken.getKeyIdentifier())) {
                    this.setErrorMessage("Policy enforces ThumbprintReference but we got " + securityToken.getKeyIdentifier());
                    this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireThumbprintReference"), this.getErrorMessage());
                    return false;
                }
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireThumbprintReference"));
            }
            if (x509Certificate.getVersion() == 2) {
                this.setErrorMessage("X509Certificate Version " + x509Certificate.getVersion() + " not supported");
                this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                return false;
            }
            if (x509Token.getTokenType() != null) {
                switch (x509Token.getTokenType()) {
                    case WssX509V3Token10: 
                    case WssX509V3Token11: {
                        if (!WSSecurityTokenConstants.X509V3Token.equals(securityToken.getTokenType()) || x509Certificate.getVersion() != 3) {
                            this.setErrorMessage("X509Certificate Version " + x509Certificate.getVersion() + " mismatch; Policy enforces " + (Object)((Object)x509Token.getTokenType()));
                            this.getPolicyAsserter().unassertPolicy(new QName(namespace, x509Token.getTokenType().name()), this.getErrorMessage());
                            return false;
                        }
                        this.getPolicyAsserter().assertPolicy(new QName(namespace, x509Token.getTokenType().name()));
                        break;
                    }
                    case WssX509V1Token10: 
                    case WssX509V1Token11: {
                        if (!WSSecurityTokenConstants.X509V1Token.equals(securityToken.getTokenType()) || x509Certificate.getVersion() != 1) {
                            this.setErrorMessage("X509Certificate Version " + x509Certificate.getVersion() + " mismatch; Policy enforces " + (Object)((Object)x509Token.getTokenType()));
                            this.getPolicyAsserter().unassertPolicy(new QName(namespace, "WssX509V1Token11"), this.getErrorMessage());
                            return false;
                        }
                        this.getPolicyAsserter().assertPolicy(new QName(namespace, "WssX509V1Token11"));
                        break;
                    }
                    case WssX509PkiPathV1Token10: 
                    case WssX509PkiPathV1Token11: {
                        if (!WSSecurityTokenConstants.X509PkiPathV1Token.equals(securityToken.getTokenType())) {
                            this.setErrorMessage("Policy enforces " + (Object)((Object)x509Token.getTokenType()) + " but we got " + securityToken.getTokenType());
                            this.getPolicyAsserter().unassertPolicy(new QName(namespace, x509Token.getTokenType().name()), this.getErrorMessage());
                            return false;
                        }
                        this.getPolicyAsserter().assertPolicy(new QName(namespace, x509Token.getTokenType().name()));
                        break;
                    }
                    case WssX509Pkcs7Token10: 
                    case WssX509Pkcs7Token11: {
                        this.setErrorMessage("Unsupported token type: " + securityToken.getTokenType());
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, x509Token.getTokenType().name()), this.getErrorMessage());
                        return false;
                    }
                }
            }
        }
        catch (XMLSecurityException e) {
            this.setErrorMessage(e.getMessage());
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

