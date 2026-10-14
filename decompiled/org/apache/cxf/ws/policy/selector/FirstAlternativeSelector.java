/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.selector;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.selector.BaseAlternativeSelector;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class FirstAlternativeSelector
extends BaseAlternativeSelector {
    @Override
    public Collection<Assertion> selectAlternative(Policy policy, PolicyEngine engine, Assertor assertor, List<List<Assertion>> request, Message msg) {
        List<Assertion> alternative;
        Iterator<List<Assertion>> alternatives = policy.getAlternatives();
        while (alternatives.hasNext()) {
            alternative = alternatives.next();
            if (!engine.supportsAlternative(alternative, assertor, msg) || !this.isCompatibleWithRequest(alternative, request)) continue;
            return alternative;
        }
        alternatives = policy.getAlternatives();
        while (alternatives.hasNext()) {
            alternative = alternatives.next();
            if (!engine.supportsAlternative(alternative, assertor, msg)) continue;
            return alternative;
        }
        return null;
    }
}

