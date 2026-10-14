/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
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
import org.apache.cxf.ws.policy.EndpointPolicyImpl;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.cxf.ws.policy.PolicyVerificationOutInterceptor;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class PolicyOutInterceptor
extends AbstractPolicyInterceptor {
    public static final PolicyOutInterceptor INSTANCE = new PolicyOutInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyOutInterceptor.class);

    public PolicyOutInterceptor() {
        super("org.apache.cxf.ws.policy.PolicyOutInterceptor", "setup");
    }

    @Override
    protected void handle(Message msg) {
        EffectivePolicy effectivePolicy;
        Exchange exchange = msg.getExchange();
        Bus bus = exchange.getBus();
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
        PolicyEngine pe = bus.getExtension(PolicyEngine.class);
        if (null == pe) {
            return;
        }
        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        Policy p = (Policy)msg.getContextualProperty("org.apache.cxf.ws.policy.override");
        if (p != null) {
            EndpointPolicyImpl endpi = new EndpointPolicyImpl(p);
            effectivePolicy = new EffectivePolicyImpl();
            ((EffectivePolicyImpl)effectivePolicy).initialise(endpi, pe, false, msg);
            msg.put(EffectivePolicy.class, effectivePolicy);
            PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", ((EffectivePolicyImpl)effectivePolicy).getPolicy());
            PolicyOutInterceptor.addInterceptors(((EffectivePolicyImpl)effectivePolicy).getInterceptors(), msg);
            assertions.addAll(((EffectivePolicyImpl)effectivePolicy).getChosenAlternative());
        } else if (MessageUtils.isRequestor(msg)) {
            Conduit conduit = exchange.getConduit(msg);
            effectivePolicy = pe.getEffectiveClientRequestPolicy(ei, boi, conduit, msg);
            msg.put(EffectivePolicy.class, effectivePolicy);
            if (effectivePolicy != null) {
                PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", effectivePolicy.getPolicy());
                PolicyOutInterceptor.addInterceptors(effectivePolicy.getInterceptors(), msg);
                assertions.addAll(effectivePolicy.getChosenAlternative());
            }
        } else {
            Destination destination = exchange.getDestination();
            List<List<Assertion>> incoming = CastUtils.cast((List)exchange.get("ws-policy.validated.alternatives"));
            EffectivePolicy effectivePolicy2 = pe.getEffectiveServerResponsePolicy(ei, boi, destination, incoming, msg);
            msg.put(EffectivePolicy.class, effectivePolicy2);
            if (effectivePolicy2 != null) {
                PolicyUtils.logPolicy(LOG, Level.FINEST, "Using effective policy: ", effectivePolicy2.getPolicy());
                PolicyOutInterceptor.addInterceptors(effectivePolicy2.getInterceptors(), msg);
                assertions.addAll(effectivePolicy2.getChosenAlternative());
            }
        }
        if (null != assertions && !assertions.isEmpty()) {
            if (LOG.isLoggable(Level.FINEST)) {
                StringBuilder buf = new StringBuilder();
                buf.append("Chosen alternative: ");
                String nl = SystemPropertyAction.getProperty("line.separator");
                buf.append(nl);
                for (Assertion a : assertions) {
                    PolicyUtils.printPolicyComponent(a, buf, 1);
                }
                LOG.finest(buf.toString());
            }
            msg.put(AssertionInfoMap.class, new AssertionInfoMap(assertions));
            msg.getInterceptorChain().add(PolicyVerificationOutInterceptor.INSTANCE);
        }
    }

    private static void addInterceptors(List<Interceptor<? extends Message>> interceptors, Message msg) {
        for (Interceptor<? extends Message> oi : interceptors) {
            msg.getInterceptorChain().add(oi);
            LOG.log(Level.FINE, "Added interceptor of type {0}", oi.getClass().getSimpleName());
        }
    }
}

