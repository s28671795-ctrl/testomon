/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.selector;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.neethi.Assertion;
import org.apache.neethi.builders.PrimitiveAssertion;

public abstract class BaseAlternativeSelector
implements AlternativeSelector {
    protected boolean isCompatibleWithRequest(List<Assertion> alternative, List<List<Assertion>> request) {
        if (request == null) {
            return true;
        }
        for (List<Assertion> r : request) {
            if (!this.isCompatible(alternative, r)) continue;
            return true;
        }
        return false;
    }

    protected boolean isCompatible(List<Assertion> alternative, List<Assertion> r) {
        ArrayList<QName> rNames = new ArrayList<QName>(r.size());
        for (Assertion ra : r) {
            rNames.add(ra.getName());
        }
        block1: for (Assertion a : alternative) {
            for (Assertion ra : r) {
                if (a.equals(ra)) {
                    rNames.remove(ra.getName());
                    continue block1;
                }
                if (!(a instanceof PrimitiveAssertion) || !(ra instanceof PrimitiveAssertion) || !((PrimitiveAssertion)a).equal(ra)) continue;
                rNames.remove(ra.getName());
                continue block1;
            }
        }
        return rNames.isEmpty();
    }
}

