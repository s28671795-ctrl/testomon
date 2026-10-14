/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public interface AlternativeSelector {
    public Collection<Assertion> selectAlternative(Policy var1, PolicyEngine var2, Assertor var3, List<List<Assertion>> var4, Message var5);
}

