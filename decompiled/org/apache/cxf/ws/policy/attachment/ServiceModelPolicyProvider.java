/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.policy.attachment.AbstractPolicyProvider;
import org.apache.neethi.Policy;

@NoJSR250Annotations
public class ServiceModelPolicyProvider
extends AbstractPolicyProvider {
    public ServiceModelPolicyProvider(Bus b) {
        super(b);
    }

    @Override
    public Policy getEffectivePolicy(BindingFaultInfo bfi, Message m4) {
        return bfi.getExtensor(Policy.class);
    }

    @Override
    public Policy getEffectivePolicy(BindingMessageInfo bmi, Message m4) {
        return bmi.getExtensor(Policy.class);
    }

    @Override
    public Policy getEffectivePolicy(BindingOperationInfo bi, Message m4) {
        return bi.getExtensor(Policy.class);
    }

    @Override
    public Policy getEffectivePolicy(EndpointInfo ei, Message m4) {
        return ei.getExtensor(Policy.class);
    }

    @Override
    public Policy getEffectivePolicy(ServiceInfo si, Message m4) {
        return si.getExtensor(Policy.class);
    }
}

