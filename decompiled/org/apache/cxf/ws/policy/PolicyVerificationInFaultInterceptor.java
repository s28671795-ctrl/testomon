/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.PolicyEngine;

public class PolicyVerificationInFaultInterceptor
extends AbstractPolicyInterceptor {
    public static final PolicyVerificationInFaultInterceptor INSTANCE = new PolicyVerificationInFaultInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyVerificationInFaultInterceptor.class);

    public PolicyVerificationInFaultInterceptor() {
        super("pre-invoke");
    }

    @Override
    protected void handle(Message message) {
        BindingFaultInfo bfi;
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return;
        }
        if (!MessageUtils.isRequestor(message)) {
            LOG.fine("Not a requestor.");
            return;
        }
        Exchange exchange = message.getExchange();
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
        Exception ex = message.getContent(Exception.class);
        if (null == ex) {
            ex = exchange.get(Exception.class);
        }
        if ((bfi = this.getBindingFaultInfo(message, ex, boi)) == null) {
            return;
        }
        this.getTransportAssertions(message);
        EffectivePolicy effectivePolicy = pe.getEffectiveClientFaultPolicy(ei, boi, bfi, message);
        if (effectivePolicy != null) {
            aim.checkEffectivePolicy(effectivePolicy.getPolicy());
            LOG.fine("Verified policies for inbound message.");
        }
    }
}

