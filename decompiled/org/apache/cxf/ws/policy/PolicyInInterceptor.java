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
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EffectivePolicyImpl;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.cxf.ws.policy.PolicyVerificationInInterceptor;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class PolicyInInterceptor
extends AbstractPolicyInterceptor {
    public static final PolicyInInterceptor INSTANCE = new PolicyInInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyInInterceptor.class);

    public PolicyInInterceptor() {
        super("org.apache.cxf.ws.policy.PolicyInInterceptor", "receive");
    }

    @Override
    protected void handle(Message msg) {
        Exchange exchange = msg.getExchange();
        Bus bus = exchange.getBus();
        Endpoint e = exchange.getEndpoint();
        if (null == e) {
            LOG.fine("No endpoint.");
            return;
        }
        EndpointInfo ei = e.getEndpointInfo();
        PolicyEngine pe = bus.getExtension(PolicyEngine.class);
        if (null == pe) {
            return;
        }
        ArrayList<Interceptor<? extends Message>> interceptors = new ArrayList<Interceptor<? extends Message>>();
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        Policy p = (Policy)msg.getContextualProperty("org.apache.cxf.ws.policy.override");
        if (p != null) {
            EndpointPolicyImpl endpi = new EndpointPolicyImpl(p);
            EffectivePolicyImpl effectivePolicyImpl = new EffectivePolicyImpl();
            effectivePolicyImpl.initialise(endpi, pe, true, msg);
            msg.put(EffectivePolicy.class, effectivePolicyImpl);
            PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", effectivePolicyImpl.getPolicy());
            interceptors.addAll(effectivePolicyImpl.getInterceptors());
            assertions.addAll(effectivePolicyImpl.getChosenAlternative());
        } else if (MessageUtils.isRequestor(msg)) {
            BindingOperationInfo boi = exchange.getBindingOperationInfo();
            if (boi == null) {
                Conduit conduit = exchange.getConduit(msg);
                EndpointPolicy ep2 = pe.getClientEndpointPolicy(ei, conduit, msg);
                if (ep2 != null) {
                    interceptors.addAll(ep2.getInterceptors(msg));
                    assertions.addAll(ep2.getVocabulary(msg));
                }
            } else {
                EffectivePolicy effectivePolicy = pe.getEffectiveClientResponsePolicy(ei, boi, msg);
                if (effectivePolicy != null) {
                    interceptors.addAll(effectivePolicy.getInterceptors());
                    if (effectivePolicy.getPolicy() != null) {
                        msg.put(AssertionInfoMap.class, new AssertionInfoMap(effectivePolicy.getPolicy()));
                        msg.getInterceptorChain().add(PolicyVerificationInInterceptor.INSTANCE);
                    }
                }
            }
        } else {
            Destination destination = exchange.getDestination();
            EndpointPolicy endpointPolicy = pe.getServerEndpointPolicy(ei, destination, msg);
            if (endpointPolicy != null) {
                interceptors.addAll(endpointPolicy.getInterceptors(msg));
                assertions.addAll(endpointPolicy.getVocabulary(msg));
            }
        }
        for (Interceptor interceptor : interceptors) {
            msg.getInterceptorChain().add(interceptor);
        }
        if (!assertions.isEmpty()) {
            msg.put(AssertionInfoMap.class, new AssertionInfoMap(assertions));
            msg.getInterceptorChain().add(PolicyVerificationInInterceptor.INSTANCE);
        }
    }
}

