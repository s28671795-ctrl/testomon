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

public class MinimalAlternativeSelector
extends BaseAlternativeSelector {
    @Override
    public Collection<Assertion> selectAlternative(Policy policy, PolicyEngine engine, Assertor assertor, List<List<Assertion>> request, Message msg) {
        List<Assertion> alternative;
        List<Assertion> choice = null;
        Iterator<List<Assertion>> alternatives = policy.getAlternatives();
        while (alternatives.hasNext()) {
            alternative = alternatives.next();
            if (!engine.supportsAlternative(alternative, assertor, msg) || !this.isCompatibleWithRequest(alternative, request)) continue;
            if (null == choice) {
                choice = alternative;
                continue;
            }
            if (request != null) {
                if (alternative.size() <= choice.size()) continue;
                choice = alternative;
                continue;
            }
            if (alternative.size() >= choice.size()) continue;
            choice = alternative;
        }
        if (choice == null) {
            alternatives = policy.getAlternatives();
            while (alternatives.hasNext()) {
                alternative = alternatives.next();
                if (!engine.supportsAlternative(alternative, assertor, msg) || null != choice && alternative.size() >= choice.size()) continue;
                choice = alternative;
            }
        }
        return choice;
    }
}

