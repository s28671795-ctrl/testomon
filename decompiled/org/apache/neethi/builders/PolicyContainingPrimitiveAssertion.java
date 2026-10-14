/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders;

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
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.neethi.builders.PrimitiveAssertion;

public class PolicyContainingPrimitiveAssertion
extends PrimitiveAssertion
implements PolicyContainingAssertion {
    protected Policy nested;

    public PolicyContainingPrimitiveAssertion(QName name, boolean optional, boolean ignorable, Policy p) {
        super(name, optional, ignorable);
        this.nested = p;
    }

    public PolicyContainingPrimitiveAssertion(QName name, boolean optional, boolean ignorable, Map<QName, String> atts, Policy p) {
        super(name, optional, ignorable, atts);
        this.nested = p;
    }

    @Override
    public PolicyComponent normalize() {
        Policy normalisedNested = this.nested.normalize(true);
        Policy p = new Policy(this.nested.getPolicyRegistry(), this.nested.getNamespace());
        ExactlyOne ea = new ExactlyOne();
        p.addPolicyComponent(ea);
        if (this.isOptional()) {
            ea.addPolicyComponent(new All());
        }
        Iterator<List<Assertion>> alternatives = normalisedNested.getAlternatives();
        while (alternatives.hasNext()) {
            All all = new All();
            List<Assertion> alternative = alternatives.next();
            Policy n = new Policy(this.nested.getPolicyRegistry(), this.nested.getNamespace());
            Assertion a = this.clone(false, n);
            ExactlyOne nea = new ExactlyOne();
            n.addPolicyComponent(nea);
            All na = new All();
            nea.addPolicyComponent(na);
            na.addPolicyComponents(alternative);
            all.addPolicyComponent(a);
            ea.addPolicyComponent(all);
        }
        return p;
    }

    protected Assertion clone(boolean optional, Policy n) {
        return new PolicyContainingPrimitiveAssertion(this.name, optional, this.ignorable, (Map<QName, String>)this.attributes, n);
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        if (this == policyComponent) {
            return true;
        }
        if (!super.equal(policyComponent)) {
            return false;
        }
        PolicyContainingPrimitiveAssertion other = (PolicyContainingPrimitiveAssertion)policyComponent;
        return this.getPolicy().equal(other.getPolicy());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PolicyContainingPrimitiveAssertion)) {
            return false;
        }
        PolicyContainingPrimitiveAssertion pa = (PolicyContainingPrimitiveAssertion)obj;
        if (!super.equals(obj)) {
            return false;
        }
        return this.getPolicy().equal(pa.getPolicy());
    }

    public void setPolicy(Policy n) {
        this.nested = n;
    }

    @Override
    public Policy getPolicy() {
        return this.nested;
    }

    @Override
    protected void writeContents(XMLStreamWriter writer) throws XMLStreamException {
        this.nested.serialize(writer);
    }

    @Override
    protected boolean hasContents() {
        return true;
    }
}

