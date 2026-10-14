/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.policy;

import org.apache.cxf.message.Message;
import org.apache.cxf.policy.PolicyCalculator;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;

public interface PolicyDataEngine {
    public <T> T getPolicy(Message var1, T var2, PolicyCalculator<T> var3);

    public <T> T getClientEndpointPolicy(Message var1, EndpointInfo var2, Conduit var3, PolicyCalculator<T> var4);

    public <T> T getServerEndpointPolicy(Message var1, EndpointInfo var2, Destination var3, PolicyCalculator<T> var4);

    public <T> void assertMessage(Message var1, T var2, PolicyCalculator<T> var3);
}

