/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EffectivePolicyImpl;
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class ServerPolicyOutFaultInterceptor
extends AbstractPolicyInterceptor {
    public static final ServerPolicyOutFaultInterceptor INSTANCE = new ServerPolicyOutFaultInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(ServerPolicyOutFaultInterceptor.class);

    public ServerPolicyOutFaultInterceptor() {
        super("org.apache.cxf.ws.policy.ServerPolicyOutFaultInterceptor", "setup");
    }

    @Override
    protected void handle(Message msg) {
        if (MessageUtils.isRequestor(msg)) {
            LOG.fine("Is a requestor.");
            return;
        }
        Exchange exchange = msg.getExchange();
        assert (null != exchange);
        BindingOperationInfo boi = exchange.getBindingOperationInfo();
        if (null == boi) {
            LOG.fine("No binding operation info.");
            return;
        }
        Endpoint e = exchange.getEndpoint();
        if (null == e) {
            LOG.fine("No endpoint.");
            return;
        }
        EndpointInfo ei = e.getEndpointInfo();
        Bus bus = exchange.getBus();
        PolicyEngine pe = bus.getExtension(PolicyEngine.class);
        if (null == pe) {
            return;
        }
        Destination destination = exchange.getDestination();
        Exception ex = exchange.get(Exception.class);
        ArrayList<Interceptor<? extends Message>> faultInterceptors = new ArrayList<Interceptor<? extends Message>>();
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        Policy p = (Policy)msg.getContextualProperty("org.apache.cxf.ws.policy.override");
        if (p != null) {
            EndpointPolicyImpl endpi = new EndpointPolicyImpl(p);
            EffectivePolicyImpl effectivePolicyImpl = new EffectivePolicyImpl();
            effectivePolicyImpl.initialise(endpi, pe, false, true, msg);
            PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", effectivePolicyImpl.getPolicy());
            faultInterceptors.addAll(effectivePolicyImpl.getInterceptors());
            assertions.addAll(effectivePolicyImpl.getChosenAlternative());
        } else {
            BindingFaultInfo bfi = this.getBindingFaultInfo(msg, ex, boi);
            if (bfi == null && msg.get(FaultMode.class) != FaultMode.UNCHECKED_APPLICATION_FAULT && msg.get(FaultMode.class) != FaultMode.CHECKED_APPLICATION_FAULT) {
                return;
            }
            EffectivePolicy effectivePolicy = pe.getEffectiveServerFaultPolicy(ei, boi, bfi, destination, msg);
            if (effectivePolicy != null) {
                faultInterceptors.addAll(effectivePolicy.getInterceptors());
                assertions.addAll(effectivePolicy.getChosenAlternative());
            }
        }
        for (Interceptor interceptor : faultInterceptors) {
            msg.getInterceptorChain().add(interceptor);
            LOG.log(Level.FINE, "Added interceptor of type {0}", interceptor.getClass().getSimpleName());
        }
        if (null != assertions && !assertions.isEmpty()) {
            msg.put(AssertionInfoMap.class, new AssertionInfoMap(assertions));
        }
    }
}

