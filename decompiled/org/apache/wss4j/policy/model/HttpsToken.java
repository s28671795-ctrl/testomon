/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.w3c.dom.Element;

public class HttpsToken
extends AbstractToken {
    private AuthenticationType authenticationType;

    public HttpsToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy, boolean requireClientCert) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
        if (requireClientCert) {
            this.setAuthenticationType(AuthenticationType.RequireClientCertificate);
        }
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getHttpsToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof HttpsToken)) {
            return false;
        }
        HttpsToken that = (HttpsToken)object;
        if (this.authenticationType != that.authenticationType) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.authenticationType != null) {
            result = 31 * result + this.authenticationType.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        boolean requireClientCert = this.authenticationType == AuthenticationType.RequireClientCertificate;
        return new HttpsToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy, requireClientCert);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, HttpsToken httpsToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                AuthenticationType authenticationType = AuthenticationType.lookUp(assertionName);
                if (authenticationType == null) continue;
                if (httpsToken.getAuthenticationType() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                httpsToken.setAuthenticationType(authenticationType);
            }
        }
    }

    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    protected void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public static enum AuthenticationType {
        HttpBasicAuthentication,
        HttpDigestAuthentication,
        RequireClientCertificate;

        private static final Map<String, AuthenticationType> LOOKUP;

        public static AuthenticationType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, AuthenticationType>();
            for (AuthenticationType u : EnumSet.allOf(AuthenticationType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

