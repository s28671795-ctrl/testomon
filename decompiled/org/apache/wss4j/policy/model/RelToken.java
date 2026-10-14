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

public class RelToken
extends AbstractToken {
    private boolean requireKeyIdentifierReference;
    private RelTokenType relTokenType;

    public RelToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getRelToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RelToken)) {
            return false;
        }
        RelToken that = (RelToken)object;
        if (this.requireKeyIdentifierReference != that.requireKeyIdentifierReference) {
            return false;
        }
        if (this.relTokenType != that.relTokenType) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.relTokenType != null) {
            result = 31 * result + this.relTokenType.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.requireKeyIdentifierReference);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new RelToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, RelToken relToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (relToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    relToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName requireKeyIdentifierRef = this.getVersion().getSPConstants().getRequireKeyIdentifierReference();
                if (requireKeyIdentifierRef.getLocalPart().equals(assertionName) && requireKeyIdentifierRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (relToken.isRequireKeyIdentifierReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    relToken.setRequireKeyIdentifierReference(true);
                    continue;
                }
                RelTokenType samlTokenType = RelTokenType.lookUp(assertionName);
                if (samlTokenType == null) continue;
                if (relToken.getRelTokenType() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                relToken.setRelTokenType(samlTokenType);
            }
        }
    }

    public boolean isRequireKeyIdentifierReference() {
        return this.requireKeyIdentifierReference;
    }

    public void setRequireKeyIdentifierReference(boolean requireKeyIdentifierReference) {
        this.requireKeyIdentifierReference = requireKeyIdentifierReference;
    }

    public RelTokenType getRelTokenType() {
        return this.relTokenType;
    }

    protected void setRelTokenType(RelTokenType relTokenType) {
        this.relTokenType = relTokenType;
    }

    public static enum RelTokenType {
        WssRelV10Token10,
        WssRelV20Token10,
        WssRelV10Token11,
        WssRelV20Token11;

        private static final Map<String, RelTokenType> LOOKUP;

        public static RelTokenType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, RelTokenType>();
            for (RelTokenType u : EnumSet.allOf(RelTokenType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

