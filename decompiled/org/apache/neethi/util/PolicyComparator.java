/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.util;

import java.util.List;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;

public final class PolicyComparator {
    private PolicyComparator() {
    }

    public static boolean compare(Policy arg1, Policy arg2) {
        if (arg1.getName() != null ? arg2.getName() == null || !arg1.getName().equals(arg2.getName()) : arg2.getName() != null) {
            return false;
        }
        if (arg1.getNamespace() != null && !arg1.getNamespace().equals(arg2.getNamespace())) {
            return false;
        }
        if (arg1.getId() != null ? arg2.getId() == null || !arg1.getId().equals(arg2.getId()) : arg2.getId() != null) {
            return false;
        }
        return PolicyComparator.compare(arg1.getPolicyComponents(), arg2.getPolicyComponents());
    }

    public static boolean compare(PolicyComponent arg1, PolicyComponent arg2) {
        if (!arg1.getClass().equals(arg2.getClass())) {
            return false;
        }
        if (arg1 instanceof Policy) {
            return PolicyComparator.compare((Policy)arg1, (Policy)arg2);
        }
        if (arg1 instanceof All) {
            return PolicyComparator.compare((All)arg1, (All)arg2);
        }
        if (arg1 instanceof ExactlyOne) {
            return PolicyComparator.compare((ExactlyOne)arg1, (ExactlyOne)arg2);
        }
        if (arg1 instanceof Assertion) {
            return PolicyComparator.compare((Assertion)arg1, (Assertion)arg2);
        }
        return false;
    }

    public static boolean compare(All arg1, All arg2) {
        return PolicyComparator.compare(arg1.getPolicyComponents(), arg2.getPolicyComponents());
    }

    public static boolean compare(ExactlyOne arg1, ExactlyOne arg2) {
        return PolicyComparator.compare(arg1.getPolicyComponents(), arg2.getPolicyComponents());
    }

    public static boolean compare(Assertion arg1, Assertion arg2) {
        return arg1.getName().equals(arg2.getName());
    }

    private static boolean compare(List<PolicyComponent> arg1, List<PolicyComponent> arg2) {
        if (arg1.size() != arg2.size()) {
            return false;
        }
        for (PolicyComponent assertion1 : arg1) {
            boolean match = false;
            for (PolicyComponent assertion2 : arg2) {
                if (!PolicyComparator.compare(assertion1, assertion2)) continue;
                match = true;
                break;
            }
            if (match) continue;
            return false;
        }
        return true;
    }
}

