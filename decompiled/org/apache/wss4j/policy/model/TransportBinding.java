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
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.TransportToken;

public class TransportBinding
extends AbstractBinding {
    private TransportToken transportToken;

    public TransportBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getTransportBinding();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TransportBinding)) {
            return false;
        }
        TransportBinding that = (TransportBinding)object;
        if (this.transportToken != null && !this.transportToken.equals(that.transportToken) || this.transportToken == null && that.transportToken != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.transportToken != null) {
            result = 31 * result + this.transportToken.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new TransportBinding(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, TransportBinding transportBinding) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName transportToken = this.getVersion().getSPConstants().getTransportToken();
                if (!transportToken.getLocalPart().equals(assertionName) || !transportToken.getNamespaceURI().equals(assertionNamespace)) continue;
                if (transportBinding.getTransportToken() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                transportBinding.setTransportToken((TransportToken)assertion);
            }
        }
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    public TransportToken getTransportToken() {
        return this.transportToken;
    }

    protected void setTransportToken(TransportToken transportToken) {
        this.transportToken = transportToken;
    }
}

