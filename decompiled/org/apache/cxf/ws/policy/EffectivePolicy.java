/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public interface EffectivePolicy {
    public Policy getPolicy();

    public Collection<Assertion> getChosenAlternative();

    public List<Interceptor<? extends Message>> getInterceptors();
}

