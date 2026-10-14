/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.w3c.dom.Element;

public class SecurityContextToken
extends AbstractToken {
    private boolean requireExternalUriReference;
    private boolean sc13SecurityContextToken;
    private boolean sc10SecurityContextToken;

    public SecurityContextToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedSecurityContextTokenPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSecurityContextToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecurityContextToken)) {
            return false;
        }
        SecurityContextToken that = (SecurityContextToken)object;
        if (this.requireExternalUriReference != that.requireExternalUriReference || this.sc13SecurityContextToken != that.sc13SecurityContextToken || this.sc10SecurityContextToken != that.sc10SecurityContextToken) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.requireExternalUriReference);
        result = 31 * result + Boolean.hashCode(this.sc13SecurityContextToken);
        result = 31 * result + Boolean.hashCode(this.sc10SecurityContextToken);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SecurityContextToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedSecurityContextTokenPolicy(Policy nestedPolicy, SecurityContextToken securityContextToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (securityContextToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    securityContextToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName requireExternalUriRef = this.getVersion().getSPConstants().getRequireExternalUriReference();
                if (requireExternalUriRef.getLocalPart().equals(assertionName) && requireExternalUriRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (securityContextToken.isRequireExternalUriReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    securityContextToken.setRequireExternalUriReference(true);
                    continue;
                }
                QName sc13SCT = this.getVersion().getSPConstants().getSc13SecurityContextToken();
                if (sc13SCT.getLocalPart().equals(assertionName) && sc13SCT.getNamespaceURI().equals(assertionNamespace)) {
                    if (securityContextToken.isSc13SecurityContextToken()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    securityContextToken.setSc13SecurityContextToken(true);
                    continue;
                }
                QName sc10SCT = this.getVersion().getSPConstants().getSc10SecurityContextToken();
                if (!sc10SCT.getLocalPart().equals(assertionName) || !sc10SCT.getNamespaceURI().equals(assertionNamespace)) continue;
                if (securityContextToken.isSc10SecurityContextToken()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                securityContextToken.setSc10SecurityContextToken(true);
            }
        }
    }

    public boolean isRequireExternalUriReference() {
        return this.requireExternalUriReference;
    }

    protected void setRequireExternalUriReference(boolean requireExternalUriReference) {
        this.requireExternalUriReference = requireExternalUriReference;
    }

    public boolean isSc13SecurityContextToken() {
        return this.sc13SecurityContextToken;
    }

    protected void setSc13SecurityContextToken(boolean sc13SecurityContextToken) {
        this.sc13SecurityContextToken = sc13SecurityContextToken;
    }

    public boolean isSc10SecurityContextToken() {
        return this.sc10SecurityContextToken;
    }

    protected void setSc10SecurityContextToken(boolean sc10SecurityContextToken) {
        this.sc10SecurityContextToken = sc10SecurityContextToken;
    }
}

