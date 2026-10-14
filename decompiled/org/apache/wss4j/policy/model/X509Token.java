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

public class X509Token
extends AbstractToken {
    private boolean requireKeyIdentifierReference;
    private boolean requireIssuerSerialReference;
    private boolean requireEmbeddedTokenReference;
    private boolean requireThumbprintReference;
    private TokenType tokenType;

    public X509Token(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getX509Token();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof X509Token)) {
            return false;
        }
        X509Token that = (X509Token)object;
        if (this.tokenType != that.tokenType) {
            return false;
        }
        if (this.requireKeyIdentifierReference != that.requireKeyIdentifierReference || this.requireIssuerSerialReference != that.requireIssuerSerialReference || this.requireEmbeddedTokenReference != that.requireEmbeddedTokenReference || this.requireThumbprintReference != that.requireThumbprintReference) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.tokenType != null) {
            result = 31 * result + this.tokenType.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.requireKeyIdentifierReference);
        result = 31 * result + Boolean.hashCode(this.requireIssuerSerialReference);
        result = 31 * result + Boolean.hashCode(this.requireEmbeddedTokenReference);
        result = 31 * result + Boolean.hashCode(this.requireThumbprintReference);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new X509Token(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, X509Token x509Token) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (x509Token.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    x509Token.setDerivedKeys(derivedKeys);
                    continue;
                }
                TokenType tokenType = TokenType.lookUp(assertionName);
                if (tokenType != null) {
                    if (x509Token.getTokenType() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    if (TokenType.WssX509V1Token10 == tokenType && SPConstants.SPVersion.SP11 != this.getVersion()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    x509Token.setTokenType(tokenType);
                    continue;
                }
                QName requireKeyIdentifierRef = this.getVersion().getSPConstants().getRequireKeyIdentifierReference();
                QName requireIssuerSerialRef = this.getVersion().getSPConstants().getRequireIssuerSerialReference();
                QName requireEmbeddedRef = this.getVersion().getSPConstants().getRequireEmbeddedTokenReference();
                QName requireThumbprintRef = this.getVersion().getSPConstants().getRequireThumbprintReference();
                if (requireKeyIdentifierRef.getLocalPart().equals(assertionName) && requireKeyIdentifierRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (x509Token.isRequireKeyIdentifierReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    x509Token.setRequireKeyIdentifierReference(true);
                    continue;
                }
                if (requireIssuerSerialRef.getLocalPart().equals(assertionName) && requireIssuerSerialRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (x509Token.isRequireIssuerSerialReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    x509Token.setRequireIssuerSerialReference(true);
                    continue;
                }
                if (requireEmbeddedRef.getLocalPart().equals(assertionName) && requireEmbeddedRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (x509Token.isRequireEmbeddedTokenReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    x509Token.setRequireEmbeddedTokenReference(true);
                    continue;
                }
                if (!requireThumbprintRef.getLocalPart().equals(assertionName) || !requireThumbprintRef.getNamespaceURI().equals(assertionNamespace)) continue;
                if (x509Token.isRequireThumbprintReference()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                x509Token.setRequireThumbprintReference(true);
            }
        }
    }

    public boolean isRequireKeyIdentifierReference() {
        return this.requireKeyIdentifierReference;
    }

    protected void setRequireKeyIdentifierReference(boolean requireKeyIdentifierReference) {
        this.requireKeyIdentifierReference = requireKeyIdentifierReference;
    }

    public boolean isRequireIssuerSerialReference() {
        return this.requireIssuerSerialReference;
    }

    protected void setRequireIssuerSerialReference(boolean requireIssuerSerialReference) {
        this.requireIssuerSerialReference = requireIssuerSerialReference;
    }

    public boolean isRequireEmbeddedTokenReference() {
        return this.requireEmbeddedTokenReference;
    }

    protected void setRequireEmbeddedTokenReference(boolean requireEmbeddedTokenReference) {
        this.requireEmbeddedTokenReference = requireEmbeddedTokenReference;
    }

    public boolean isRequireThumbprintReference() {
        return this.requireThumbprintReference;
    }

    protected void setRequireThumbprintReference(boolean requireThumbprintReference) {
        this.requireThumbprintReference = requireThumbprintReference;
    }

    public TokenType getTokenType() {
        return this.tokenType;
    }

    protected void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public static enum TokenType {
        WssX509V1Token10,
        WssX509V3Token10,
        WssX509Pkcs7Token10,
        WssX509PkiPathV1Token10,
        WssX509V1Token11,
        WssX509V3Token11,
        WssX509Pkcs7Token11,
        WssX509PkiPathV1Token11;

        private static final Map<String, TokenType> LOOKUP;

        public static TokenType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, TokenType>();
            for (TokenType u : EnumSet.allOf(TokenType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

