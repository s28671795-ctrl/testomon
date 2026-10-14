/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.SPConstants;

public abstract class AbstractSecurityAssertion
implements Assertion {
    private boolean isOptional;
    private boolean isIgnorable;
    private volatile PolicyComponent normalized;
    private SPConstants.SPVersion version;

    protected AbstractSecurityAssertion(SPConstants.SPVersion version) {
        this.version = version;
    }

    @Override
    public boolean isOptional() {
        return this.isOptional;
    }

    public void setOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }

    @Override
    public boolean isIgnorable() {
        return this.isIgnorable;
    }

    public void setIgnorable(boolean isIgnorable) {
        this.isIgnorable = isIgnorable;
    }

    @Override
    public short getType() {
        return 5;
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        return policyComponent == this;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractSecurityAssertion)) {
            return false;
        }
        AbstractSecurityAssertion that = (AbstractSecurityAssertion)object;
        if (this.isOptional != that.isOptional) {
            return false;
        }
        if (this.isIgnorable != that.isIgnorable) {
            return false;
        }
        return (this.version == null || this.version.equals((Object)that.version)) && (this.version != null || that.version == null);
    }

    public int hashCode() {
        int result = 17;
        if (this.version != null) {
            result = 31 * result + this.version.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.isOptional);
        result = 31 * result + Boolean.hashCode(this.isIgnorable);
        return result;
    }

    @Override
    public PolicyComponent normalize() {
        if (this.normalized == null) {
            Policy policy = new Policy();
            ExactlyOne exactlyOne = new ExactlyOne();
            policy.addPolicyComponent(exactlyOne);
            if (this.isOptional()) {
                exactlyOne.addPolicyComponent(new All());
            }
            AbstractSecurityAssertion a = this.clone(null);
            a.normalized = a;
            a.setOptional(false);
            All all = new All();
            all.addPolicyComponent(a);
            exactlyOne.addPolicyComponent(all);
            this.normalized = policy;
        }
        return this.normalized;
    }

    public boolean isNormalized() {
        return this.normalized == this;
    }

    public PolicyComponent normalize(Policy nestedPolicy) {
        if (this.normalized == null) {
            Policy normalizedNestedPolicy = nestedPolicy.normalize(true);
            Policy policy = new Policy();
            ExactlyOne exactlyOne = new ExactlyOne();
            policy.addPolicyComponent(exactlyOne);
            if (this.isOptional()) {
                exactlyOne.addPolicyComponent(new All());
            }
            Iterator<List<Assertion>> alternatives = normalizedNestedPolicy.getAlternatives();
            while (alternatives.hasNext()) {
                List<Assertion> alternative = alternatives.next();
                Policy ncp = new Policy(nestedPolicy.getPolicyRegistry(), nestedPolicy.getNamespace());
                ExactlyOne nceo = new ExactlyOne();
                ncp.addPolicyComponent(nceo);
                All nca = new All();
                nceo.addPolicyComponent(nca);
                nca.addPolicyComponents(alternative);
                AbstractSecurityAssertion a = this.clone(ncp);
                a.normalized = a;
                a.setOptional(false);
                All all = new All();
                all.addPolicyComponent(a);
                exactlyOne.addPolicyComponent(all);
            }
            this.normalized = policy;
        }
        return this.normalized;
    }

    public SPConstants.SPVersion getVersion() {
        return this.version;
    }

    public void serialize(XMLStreamWriter writer, Policy nestedPolicy) throws XMLStreamException {
        writer.writeStartElement(this.getName().getPrefix(), this.getName().getLocalPart(), this.getName().getNamespaceURI());
        writer.writeNamespace(this.getName().getPrefix(), this.getName().getNamespaceURI());
        if (this.isOptional()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Optional", "true");
        }
        if (this.isIgnorable()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Ignorable", "true");
        }
        nestedPolicy.serialize(writer);
        writer.writeEndElement();
    }

    protected abstract AbstractSecurityAssertion cloneAssertion(Policy var1);

    public AbstractSecurityAssertion clone(Policy nestedPolicy) {
        AbstractSecurityAssertion assertion = this.cloneAssertion(nestedPolicy);
        assertion.setIgnorable(this.isIgnorable());
        assertion.setOptional(this.isOptional());
        return assertion;
    }

    public boolean isAsserted(Map<QName, List<AssertionState>> assertionStatesMap) {
        List<AssertionState> assertionStateList = assertionStatesMap.get(this.getName());
        if (assertionStateList != null) {
            for (AssertionState assertionState : assertionStateList) {
                if (assertionState.getAssertion() != this || assertionState.isAsserted()) continue;
                return false;
            }
        }
        return true;
    }
}

