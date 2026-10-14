/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.PolicyAssertion;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.neethi.PolicyOperator;

public class AssertionInfoMap
extends HashMap<QName, Collection<AssertionInfo>> {
    private static final long serialVersionUID = -4059701923851991413L;
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AssertionInfoMap.class, "APIMessages");

    public AssertionInfoMap(Policy p) {
        this(AssertionInfoMap.getAssertions(p));
    }

    public AssertionInfoMap(Collection<? extends Assertion> assertions) {
        super(assertions.size() < 6 ? 6 : assertions.size());
        for (Assertion assertion : assertions) {
            this.putAssertionInfo(assertion);
        }
    }

    private void putAssertionInfo(Assertion a) {
        Policy p;
        if (a instanceof PolicyContainingAssertion && (p = ((PolicyContainingAssertion)a).getPolicy()) != null) {
            ArrayList<Assertion> pcs = new ArrayList<Assertion>();
            AssertionInfoMap.getAssertions(p, pcs);
            for (Assertion na : pcs) {
                this.putAssertionInfo(na);
            }
        }
        AssertionInfo ai = new AssertionInfo(a);
        ArrayList<AssertionInfo> ail = (ArrayList<AssertionInfo>)this.get(a.getName());
        if (ail == null) {
            ail = new ArrayList<AssertionInfo>();
            this.put(a.getName(), ail);
        }
        for (AssertionInfo ai2 : ail) {
            if (ai2.getAssertion() != a) continue;
            return;
        }
        ail.add(ai);
    }

    public Collection<AssertionInfo> getAssertionInfo(QName name) {
        List<AssertionInfo> ail = (List<AssertionInfo>)this.get(name);
        return ail != null ? ail : CastUtils.cast(Collections.EMPTY_LIST, AssertionInfo.class);
    }

    public boolean supportsAlternative(PolicyComponent assertion, List<QName> errors2) {
        Policy p;
        boolean pass = true;
        if (assertion instanceof PolicyAssertion) {
            PolicyAssertion a = (PolicyAssertion)assertion;
            if (!a.isAsserted(this) && !a.isOptional()) {
                errors2.add(a.getName());
                pass = false;
            }
        } else if (assertion instanceof Assertion) {
            Assertion ass = (Assertion)assertion;
            Collection<AssertionInfo> ail = this.getAssertionInfo(ass.getName());
            boolean found = false;
            for (AssertionInfo ai : ail) {
                if (!ai.getAssertion().equal(ass) && !ai.getAssertion().equals(ass)) continue;
                found = true;
                if (ai.isAsserted() || ass.isOptional()) continue;
                errors2.add(ass.getName());
                pass = false;
            }
            if (!found) {
                errors2.add(ass.getName());
                return false;
            }
        }
        if (assertion instanceof PolicyContainingAssertion && (p = ((PolicyContainingAssertion)assertion).getPolicy()) != null) {
            Iterator<List<Assertion>> alternatives = p.getAlternatives();
            while (alternatives.hasNext()) {
                List<Assertion> pc = alternatives.next();
                for (Assertion p2 : pc) {
                    pass &= this.supportsAlternative(p2, errors2);
                }
            }
        }
        return pass;
    }

    public boolean supportsAlternative(Collection<? extends PolicyComponent> alternative, List<QName> errors2) {
        boolean pass = true;
        for (PolicyComponent policyComponent : alternative) {
            pass &= this.supportsAlternative(policyComponent, errors2);
        }
        return pass;
    }

    public List<List<Assertion>> checkEffectivePolicy(Policy policy) {
        ArrayList<List<Assertion>> validated = new ArrayList<List<Assertion>>(4);
        ArrayList<QName> errors2 = new ArrayList<QName>();
        Iterator<List<Assertion>> alternatives = policy.getAlternatives();
        while (alternatives.hasNext()) {
            List<Assertion> pc = alternatives.next();
            if (!this.supportsAlternative(pc, errors2)) continue;
            validated.add(pc);
        }
        if (!validated.isEmpty()) {
            return validated;
        }
        LinkedHashSet<String> msgs = new LinkedHashSet<String>();
        for (QName name : errors2) {
            Collection<AssertionInfo> ais = this.getAssertionInfo(name);
            boolean found = false;
            for (AssertionInfo ai : ais) {
                if (ai.isAsserted()) continue;
                String s2 = name.toString();
                if (ai.getErrorMessage() != null) {
                    s2 = s2 + ": " + ai.getErrorMessage();
                }
                msgs.add(s2);
                found = true;
            }
            if (found) continue;
            msgs.add(name.toString());
        }
        StringBuilder error = new StringBuilder();
        for (String msg : msgs) {
            error.append('\n').append(msg);
        }
        throw new PolicyException(new Message("NO_ALTERNATIVE_EXC", BUNDLE, error.toString()));
    }

    public void check() {
        for (Collection ais : this.values()) {
            for (AssertionInfo ai : ais) {
                if (ai.isAsserted()) continue;
                throw new PolicyException(new Message("NOT_ASSERTED_EXC", BUNDLE, ai.getAssertion().getName()));
            }
        }
    }

    private static Collection<Assertion> getAssertions(PolicyOperator p) {
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        AssertionInfoMap.getAssertions(p, assertions);
        return assertions;
    }

    private static void getAssertions(PolicyOperator p, Collection<Assertion> assertions) {
        List<PolicyComponent> pcs = p.getPolicyComponents();
        for (PolicyComponent pc : pcs) {
            if (pc instanceof Assertion) {
                assertions.add((Assertion)pc);
                continue;
            }
            AssertionInfoMap.getAssertions((PolicyOperator)pc, assertions);
        }
    }
}

