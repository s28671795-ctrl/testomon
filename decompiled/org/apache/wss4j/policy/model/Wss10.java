/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class Wss10
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private Policy nestedPolicy;
    private boolean mustSupportRefKeyIdentifier;
    private boolean mustSupportRefIssuerSerial;
    private boolean mustSupportRefExternalURI;
    private boolean mustSupportRefEmbeddedToken;

    public Wss10(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedWss10Policy(nestedPolicy, this);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getWss10();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Wss10)) {
            return false;
        }
        Wss10 that = (Wss10)object;
        if (this.mustSupportRefKeyIdentifier != that.mustSupportRefKeyIdentifier || this.mustSupportRefIssuerSerial != that.mustSupportRefIssuerSerial || this.mustSupportRefExternalURI != that.mustSupportRefExternalURI || this.mustSupportRefEmbeddedToken != that.mustSupportRefEmbeddedToken) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.mustSupportRefKeyIdentifier);
        result = 31 * result + Boolean.hashCode(this.mustSupportRefIssuerSerial);
        result = 31 * result + Boolean.hashCode(this.mustSupportRefExternalURI);
        result = 31 * result + Boolean.hashCode(this.mustSupportRefEmbeddedToken);
        return 31 * result + super.hashCode();
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Wss10(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedWss10Policy(Policy nestedPolicy, Wss10 wss10) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName mustSupportRefKeyIdentifier = this.getVersion().getSPConstants().getMustSupportRefKeyIdentifier();
                if (mustSupportRefKeyIdentifier.getLocalPart().equals(assertionName) && mustSupportRefKeyIdentifier.getNamespaceURI().equals(assertionNamespace)) {
                    if (wss10.isMustSupportRefKeyIdentifier()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    wss10.setMustSupportRefKeyIdentifier(true);
                    continue;
                }
                QName mustSupportRefIssuerSerial = this.getVersion().getSPConstants().getMustSupportRefIssuerSerial();
                if (mustSupportRefIssuerSerial.getLocalPart().equals(assertionName) && mustSupportRefIssuerSerial.getNamespaceURI().equals(assertionNamespace)) {
                    if (wss10.isMustSupportRefIssuerSerial()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    wss10.setMustSupportRefIssuerSerial(true);
                    continue;
                }
                QName mustSupportRefExternal = this.getVersion().getSPConstants().getMustSupportRefExternalUri();
                if (mustSupportRefExternal.getLocalPart().equals(assertionName) && mustSupportRefExternal.getNamespaceURI().equals(assertionNamespace)) {
                    if (wss10.isMustSupportRefExternalURI()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    wss10.setMustSupportRefExternalURI(true);
                    continue;
                }
                QName mustSupportRefEmbedded = this.getVersion().getSPConstants().getMustSupportRefEmbeddedToken();
                if (!mustSupportRefEmbedded.getLocalPart().equals(assertionName) || !mustSupportRefEmbedded.getNamespaceURI().equals(assertionNamespace)) continue;
                if (wss10.isMustSupportRefEmbeddedToken()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                wss10.setMustSupportRefEmbeddedToken(true);
            }
        }
    }

    public boolean isMustSupportRefKeyIdentifier() {
        return this.mustSupportRefKeyIdentifier;
    }

    protected void setMustSupportRefKeyIdentifier(boolean mustSupportRefKeyIdentifier) {
        this.mustSupportRefKeyIdentifier = mustSupportRefKeyIdentifier;
    }

    public boolean isMustSupportRefIssuerSerial() {
        return this.mustSupportRefIssuerSerial;
    }

    protected void setMustSupportRefIssuerSerial(boolean mustSupportRefIssuerSerial) {
        this.mustSupportRefIssuerSerial = mustSupportRefIssuerSerial;
    }

    public boolean isMustSupportRefExternalURI() {
        return this.mustSupportRefExternalURI;
    }

    protected void setMustSupportRefExternalURI(boolean mustSupportRefExternalURI) {
        this.mustSupportRefExternalURI = mustSupportRefExternalURI;
    }

    public boolean isMustSupportRefEmbeddedToken() {
        return this.mustSupportRefEmbeddedToken;
    }

    protected void setMustSupportRefEmbeddedToken(boolean mustSupportRefEmbeddedToken) {
        this.mustSupportRefEmbeddedToken = mustSupportRefEmbeddedToken;
    }
}

