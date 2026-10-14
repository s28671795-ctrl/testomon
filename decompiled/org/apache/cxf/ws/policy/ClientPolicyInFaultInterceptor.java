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
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicyImpl;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class ClientPolicyInFaultInterceptor
extends AbstractPolicyInterceptor {
    public static final ClientPolicyInFaultInterceptor INSTANCE = new ClientPolicyInFaultInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(ClientPolicyInFaultInterceptor.class);

    public ClientPolicyInFaultInterceptor() {
        super("org.apache.cxf.ws.policy.ClientPolicyInFaultInterceptor", "receive");
    }

    @Override
    protected void handle(Message msg) {
        if (!MessageUtils.isRequestor(msg)) {
            LOG.fine("Not a requestor.");
            return;
        }
        Exchange exchange = msg.getExchange();
        assert (null != exchange);
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
        Conduit conduit = exchange.getConduit(msg);
        LOG.fine("conduit: " + conduit);
        ArrayList<Interceptor<? extends Message>> faultInterceptors = new ArrayList<Interceptor<? extends Message>>();
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        Policy p = (Policy)msg.getContextualProperty("org.apache.cxf.ws.policy.override");
        if (p != null) {
            EndpointPolicyImpl endpi = new EndpointPolicyImpl(p);
            EffectivePolicyImpl effectivePolicyImpl = new EffectivePolicyImpl();
            effectivePolicyImpl.initialise(endpi, pe, true, true, msg);
            PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", effectivePolicyImpl.getPolicy());
            faultInterceptors.addAll(effectivePolicyImpl.getInterceptors());
            assertions.addAll(effectivePolicyImpl.getChosenAlternative());
        } else {
            EndpointPolicy ep = pe.getClientEndpointPolicy(ei, conduit, msg);
            LOG.fine("ep: " + ep);
            if (ep != null) {
                faultInterceptors.addAll(ep.getFaultInterceptors(msg));
                assertions.addAll(ep.getFaultVocabulary(msg));
            }
        }
        LOG.fine("faultInterceptors: " + faultInterceptors);
        for (Interceptor interceptor : faultInterceptors) {
            msg.getInterceptorChain().add(interceptor);
            LOG.log(Level.FINE, "Added interceptor of type {0}", interceptor.getClass().getSimpleName());
        }
        if (!assertions.isEmpty()) {
            msg.put(AssertionInfoMap.class, new AssertionInfoMap(assertions));
        }
    }
}

