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

public class UsernameToken
extends AbstractToken {
    private PasswordType passwordType;
    private boolean created;
    private boolean nonce;
    private UsernameTokenType usernameTokenType;

    public UsernameToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getUsernameToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof UsernameToken)) {
            return false;
        }
        UsernameToken that = (UsernameToken)object;
        if (this.passwordType != that.passwordType || this.usernameTokenType != that.usernameTokenType) {
            return false;
        }
        if (this.created != that.created || this.nonce != that.nonce) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.passwordType != null) {
            result = 31 * result + this.passwordType.hashCode();
        }
        if (this.usernameTokenType != null) {
            result = 31 * result + this.usernameTokenType.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.created);
        result = 31 * result + Boolean.hashCode(this.nonce);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new UsernameToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, UsernameToken usernameToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                PasswordType passwordType = PasswordType.lookUp(assertionName);
                if (passwordType != null) {
                    if (usernameToken.getPasswordType() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    usernameToken.setPasswordType(passwordType);
                    continue;
                }
                if (this.getVersion().getSPConstants().getCreated().getLocalPart().equals(assertionName) && this.getVersion().getSPConstants().getCreated().getNamespaceURI().equals(assertionNamespace)) {
                    if (usernameToken.isCreated()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    usernameToken.setCreated(true);
                    continue;
                }
                if (this.getVersion().getSPConstants().getNonce().getLocalPart().equals(assertionName) && this.getVersion().getSPConstants().getNonce().getNamespaceURI().equals(assertionNamespace)) {
                    if (usernameToken.isNonce()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    usernameToken.setNonce(true);
                    continue;
                }
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (usernameToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    usernameToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                UsernameTokenType usernameTokenType = UsernameTokenType.lookUp(assertionName);
                if (usernameTokenType == null) continue;
                if (usernameToken.getUsernameTokenType() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                usernameToken.setUsernameTokenType(usernameTokenType);
            }
        }
    }

    public PasswordType getPasswordType() {
        return this.passwordType;
    }

    protected void setPasswordType(PasswordType passwordType) {
        this.passwordType = passwordType;
    }

    public boolean isCreated() {
        return this.created;
    }

    protected void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isNonce() {
        return this.nonce;
    }

    protected void setNonce(boolean nonce) {
        this.nonce = nonce;
    }

    public UsernameTokenType getUsernameTokenType() {
        return this.usernameTokenType;
    }

    protected void setUsernameTokenType(UsernameTokenType usernameTokenType) {
        this.usernameTokenType = usernameTokenType;
    }

    public static enum UsernameTokenType {
        WssUsernameToken10,
        WssUsernameToken11;

        private static final Map<String, UsernameTokenType> LOOKUP;

        public static UsernameTokenType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, UsernameTokenType>();
            for (UsernameTokenType u : EnumSet.allOf(UsernameTokenType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }

    public static enum PasswordType {
        NoPassword,
        HashPassword;

        private static final Map<String, PasswordType> LOOKUP;

        public static PasswordType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, PasswordType>();
            for (PasswordType u : EnumSet.allOf(PasswordType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

