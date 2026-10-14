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

public class KerberosToken
extends AbstractToken {
    private boolean requireKeyIdentifierReference;
    private ApReqTokenType apReqTokenType;

    public KerberosToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getKerberosToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof KerberosToken)) {
            return false;
        }
        KerberosToken that = (KerberosToken)object;
        if (this.requireKeyIdentifierReference != that.requireKeyIdentifierReference) {
            return false;
        }
        if (this.apReqTokenType != that.apReqTokenType) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.apReqTokenType != null) {
            result = 31 * result + this.apReqTokenType.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.requireKeyIdentifierReference);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new KerberosToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, KerberosToken kerberosToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (kerberosToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    kerberosToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName requireKeyIdentifierRef = this.getVersion().getSPConstants().getRequireKeyIdentifierReference();
                if (requireKeyIdentifierRef.getLocalPart().equals(assertionName) && requireKeyIdentifierRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (kerberosToken.isRequireKeyIdentifierReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    kerberosToken.setRequireKeyIdentifierReference(true);
                    continue;
                }
                ApReqTokenType apReqTokenType = ApReqTokenType.lookUp(assertionName);
                if (apReqTokenType == null) continue;
                if (kerberosToken.getApReqTokenType() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                kerberosToken.setApReqTokenType(apReqTokenType);
            }
        }
    }

    public boolean isRequireKeyIdentifierReference() {
        return this.requireKeyIdentifierReference;
    }

    protected void setRequireKeyIdentifierReference(boolean requireKeyIdentifierReference) {
        this.requireKeyIdentifierReference = requireKeyIdentifierReference;
    }

    public ApReqTokenType getApReqTokenType() {
        return this.apReqTokenType;
    }

    protected void setApReqTokenType(ApReqTokenType apReqTokenType) {
        this.apReqTokenType = apReqTokenType;
    }

    public static enum ApReqTokenType {
        WssKerberosV5ApReqToken11,
        WssGssKerberosV5ApReqToken11;

        private static final Map<String, ApReqTokenType> LOOKUP;

        public static ApReqTokenType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, ApReqTokenType>();
            for (ApReqTokenType u : EnumSet.allOf(ApReqTokenType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

