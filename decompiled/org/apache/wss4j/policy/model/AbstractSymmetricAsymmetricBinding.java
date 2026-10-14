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
import org.apache.wss4j.policy.model.AbstractBinding;

public abstract class AbstractSymmetricAsymmetricBinding
extends AbstractBinding {
    private ProtectionOrder protectionOrder = ProtectionOrder.SignBeforeEncrypting;
    private boolean encryptSignature = false;
    private boolean protectTokens = false;
    private boolean onlySignEntireHeadersAndBody = false;

    protected AbstractSymmetricAsymmetricBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedSymmetricAsymmetricBindingBasePolicy(nestedPolicy, this);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractSymmetricAsymmetricBinding)) {
            return false;
        }
        AbstractSymmetricAsymmetricBinding that = (AbstractSymmetricAsymmetricBinding)object;
        if (this.protectionOrder != that.protectionOrder || this.encryptSignature != that.encryptSignature || this.protectTokens != that.protectTokens || this.onlySignEntireHeadersAndBody != that.onlySignEntireHeadersAndBody) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.protectionOrder != null) {
            result = 31 * result + this.protectionOrder.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.encryptSignature);
        result = 31 * result + Boolean.hashCode(this.protectTokens);
        result = 31 * result + Boolean.hashCode(this.onlySignEntireHeadersAndBody);
        return 31 * result + super.hashCode();
    }

    protected void parseNestedSymmetricAsymmetricBindingBasePolicy(Policy nestedPolicy, AbstractSymmetricAsymmetricBinding asymmetricBindingBase) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                ProtectionOrder protectionOrder = ProtectionOrder.lookUp(assertionName);
                if (protectionOrder != null) {
                    if (asymmetricBindingBase.getProtectionOrder() == ProtectionOrder.EncryptBeforeSigning) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    asymmetricBindingBase.setProtectionOrder(protectionOrder);
                    continue;
                }
                QName encryptSignature = this.getVersion().getSPConstants().getEncryptSignature();
                if (encryptSignature.getLocalPart().equals(assertionName) && encryptSignature.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBindingBase.isEncryptSignature()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    asymmetricBindingBase.setEncryptSignature(true);
                    continue;
                }
                QName protectTokens = this.getVersion().getSPConstants().getProtectTokens();
                if (protectTokens.getLocalPart().equals(assertionName) && protectTokens.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBindingBase.isProtectTokens()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    asymmetricBindingBase.setProtectTokens(true);
                    continue;
                }
                QName onlySign = this.getVersion().getSPConstants().getOnlySignEntireHeadersAndBody();
                if (!onlySign.getLocalPart().equals(assertionName) || !onlySign.getNamespaceURI().equals(assertionNamespace)) continue;
                if (asymmetricBindingBase.isOnlySignEntireHeadersAndBody()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                asymmetricBindingBase.setOnlySignEntireHeadersAndBody(true);
            }
        }
    }

    public ProtectionOrder getProtectionOrder() {
        return this.protectionOrder;
    }

    protected void setProtectionOrder(ProtectionOrder protectionOrder) {
        this.protectionOrder = protectionOrder;
    }

    public boolean isEncryptSignature() {
        return this.encryptSignature;
    }

    protected void setEncryptSignature(boolean encryptSignature) {
        this.encryptSignature = encryptSignature;
    }

    public boolean isProtectTokens() {
        return this.protectTokens;
    }

    protected void setProtectTokens(boolean protectTokens) {
        this.protectTokens = protectTokens;
    }

    public boolean isOnlySignEntireHeadersAndBody() {
        return this.onlySignEntireHeadersAndBody;
    }

    protected void setOnlySignEntireHeadersAndBody(boolean onlySignEntireHeadersAndBody) {
        this.onlySignEntireHeadersAndBody = onlySignEntireHeadersAndBody;
    }

    public static enum ProtectionOrder {
        EncryptBeforeSigning,
        SignBeforeEncrypting;

        private static final Map<String, ProtectionOrder> LOOKUP;

        public static ProtectionOrder lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, ProtectionOrder>();
            for (ProtectionOrder u : EnumSet.allOf(ProtectionOrder.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

