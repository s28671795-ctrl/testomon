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

public interface EndpointPolicy {
    public Policy getPolicy();

    public EndpointPolicy updatePolicy(Policy var1, Message var2);

    public Collection<Assertion> getChosenAlternative();

    public Collection<Assertion> getVocabulary(Message var1);

    public Collection<Assertion> getFaultVocabulary(Message var1);

    public List<Interceptor<? extends Message>> getInterceptors(Message var1);

    public List<Interceptor<? extends Message>> getFaultInterceptors(Message var1);
}

