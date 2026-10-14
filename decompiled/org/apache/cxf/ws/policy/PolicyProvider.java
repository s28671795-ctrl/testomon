/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.neethi.Policy;

public interface PolicyProvider {
    public Policy getEffectivePolicy(ServiceInfo var1, Message var2);

    public Policy getEffectivePolicy(EndpointInfo var1, Message var2);

    public Policy getEffectivePolicy(BindingOperationInfo var1, Message var2);

    public Policy getEffectivePolicy(BindingMessageInfo var1, Message var2);

    public Policy getEffectivePolicy(BindingFaultInfo var1, Message var2);
}

