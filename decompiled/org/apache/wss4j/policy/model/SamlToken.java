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

public class SamlToken
extends AbstractToken {
    private boolean requireKeyIdentifierReference;
    private SamlTokenType samlTokenType;

    public SamlToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSamlToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SamlToken)) {
            return false;
        }
        SamlToken that = (SamlToken)object;
        if (this.requireKeyIdentifierReference != that.requireKeyIdentifierReference) {
            return false;
        }
        if (this.samlTokenType != that.samlTokenType) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.samlTokenType != null) {
            result = 31 * result + this.samlTokenType.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.requireKeyIdentifierReference);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SamlToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, SamlToken samlToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (samlToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    samlToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName requireKeyIdentifierRef = this.getVersion().getSPConstants().getRequireKeyIdentifierReference();
                if (requireKeyIdentifierRef.getLocalPart().equals(assertionName) && requireKeyIdentifierRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (samlToken.isRequireKeyIdentifierReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    samlToken.setRequireKeyIdentifierReference(true);
                    continue;
                }
                SamlTokenType samlTokenType = SamlTokenType.lookUp(assertionName);
                if (samlTokenType == null) continue;
                if (samlToken.getSamlTokenType() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                samlToken.setSamlTokenType(samlTokenType);
            }
        }
    }

    public boolean isRequireKeyIdentifierReference() {
        return this.requireKeyIdentifierReference;
    }

    public void setRequireKeyIdentifierReference(boolean requireKeyIdentifierReference) {
        this.requireKeyIdentifierReference = requireKeyIdentifierReference;
    }

    public SamlTokenType getSamlTokenType() {
        return this.samlTokenType;
    }

    protected void setSamlTokenType(SamlTokenType samlTokenType) {
        this.samlTokenType = samlTokenType;
    }

    public static enum SamlTokenType {
        WssSamlV10Token10,
        WssSamlV11Token10,
        WssSamlV10Token11,
        WssSamlV11Token11,
        WssSamlV20Token11;

        private static final Map<String, SamlTokenType> LOOKUP;

        public static SamlTokenType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, SamlTokenType>();
            for (SamlTokenType u : EnumSet.allOf(SamlTokenType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

