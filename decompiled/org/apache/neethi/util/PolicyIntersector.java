/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.IntersectableAssertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;

public class PolicyIntersector {
    private boolean strict;

    public PolicyIntersector() {
        this.strict = true;
    }

    public PolicyIntersector(boolean s2) {
        this.strict = s2;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void setStrict(boolean s2) {
        this.strict = s2;
    }

    private Assertion intersect(Assertion a1, Assertion a2) {
        if (a1 instanceof IntersectableAssertion) {
            if (!((IntersectableAssertion)a1).isCompatible(a2, this.strict)) {
                return null;
            }
            return ((IntersectableAssertion)a1).intersect(a2, this.strict);
        }
        if (a1.getName().equals(a2.getName())) {
            if (a1 instanceof PolicyContainingAssertion && a2 instanceof PolicyContainingAssertion) {
                Policy p2;
                PolicyContainingAssertion pc1 = (PolicyContainingAssertion)a1;
                PolicyContainingAssertion pc2 = (PolicyContainingAssertion)a2;
                PolicyIntersector pi = new PolicyIntersector(this.strict);
                Policy p1 = pc1.getPolicy();
                if (pi.compatiblePolicies(p1, p2 = pc2.getPolicy())) {
                    return a1;
                }
            } else {
                return a1;
            }
        }
        return null;
    }

    private Assertion findCompatibleAssertion(Assertion assertion, Collection<? extends PolicyComponent> alt, boolean remove) {
        Iterator<? extends PolicyComponent> iterator2 = alt.iterator();
        while (iterator2.hasNext()) {
            Assertion compatible;
            PolicyComponent a = iterator2.next();
            if (!(a instanceof Assertion) || null == (compatible = this.intersect(assertion, (Assertion)a))) continue;
            if (remove) {
                iterator2.remove();
            }
            return compatible;
        }
        return null;
    }

    boolean compatibleAlternatives(Collection<? extends PolicyComponent> alt1, Collection<? extends PolicyComponent> alt2) {
        if (alt1.isEmpty() && alt2.isEmpty()) {
            return true;
        }
        All all = this.createCompatibleAlternatives(alt1, alt2, true);
        if (all == null) {
            return false;
        }
        return !all.getAssertions().isEmpty();
    }

    All createCompatibleAlternatives(Collection<? extends PolicyComponent> alt1, Collection<? extends PolicyComponent> alt2, boolean remove) {
        Assertion assertion;
        All all = new All();
        if (alt1.isEmpty() && alt2.isEmpty()) {
            return all;
        }
        alt1 = new ArrayList<PolicyComponent>(alt1);
        alt2 = new ArrayList<PolicyComponent>(alt2);
        Iterator<? extends PolicyComponent> iterator2 = alt1.iterator();
        while (iterator2.hasNext()) {
            PolicyComponent policyComponent = iterator2.next();
            if (!(policyComponent instanceof Assertion)) continue;
            assertion = this.findCompatibleAssertion((Assertion)policyComponent, alt2, remove);
            if (assertion != null) {
                if (remove) {
                    iterator2.remove();
                }
                all.addPolicyComponent(assertion);
                continue;
            }
            if (!this.strict && ((Assertion)policyComponent).isIgnorable()) {
                all.addPolicyComponent(policyComponent);
                continue;
            }
            if (!this.strict && ((Assertion)policyComponent).isIgnorable()) continue;
            return null;
        }
        for (PolicyComponent policyComponent : alt2) {
            if (!(policyComponent instanceof Assertion)) continue;
            assertion = this.findCompatibleAssertion((Assertion)policyComponent, alt1, remove);
            if (assertion != null) {
                all.addPolicyComponent(assertion);
                continue;
            }
            if (!this.strict && ((Assertion)policyComponent).isIgnorable()) {
                all.addPolicyComponent(policyComponent);
                continue;
            }
            if (!this.strict && ((Assertion)policyComponent).isIgnorable()) continue;
            return null;
        }
        return all;
    }

    public boolean compatiblePolicies(Policy p1, Policy p2) {
        Iterator<List<Assertion>> i1 = p1.getAlternatives();
        while (i1.hasNext()) {
            List<Assertion> alt1 = i1.next();
            Iterator<List<Assertion>> i2 = p2.getAlternatives();
            if (!i2.hasNext() && alt1.isEmpty()) {
                return true;
            }
            while (i2.hasNext()) {
                List<Assertion> alt2 = i2.next();
                if (!this.compatibleAlternatives(alt1, alt2)) continue;
                return true;
            }
        }
        return false;
    }

    public Policy intersect(Policy p1, Policy p2) {
        return this.intersect(p1, p2, false);
    }

    public Policy intersect(Policy p1, Policy p2, boolean allowDups) {
        Policy compatible = new Policy(p1.getPolicyRegistry(), p1.getNamespace());
        ExactlyOne eo = new ExactlyOne(compatible);
        if (!this.compatiblePolicies(p1, p2)) {
            return compatible;
        }
        Iterator<List<Assertion>> i1 = p1.getAlternatives();
        while (i1.hasNext()) {
            List<Assertion> alt1 = i1.next();
            Iterator<List<Assertion>> i2 = p2.getAlternatives();
            while (i2.hasNext()) {
                List<Assertion> alt2 = i2.next();
                All all = this.createCompatibleAlternatives(alt1, alt2, !allowDups);
                if (all == null) continue;
                eo.addPolicyComponent(all);
            }
        }
        return compatible;
    }
}

