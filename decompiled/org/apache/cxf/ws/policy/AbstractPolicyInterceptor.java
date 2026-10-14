/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.PolicyUtils;

public abstract class AbstractPolicyInterceptor
extends AbstractPhaseInterceptor<Message> {
    public AbstractPolicyInterceptor(String phase) {
        super(phase);
    }

    public AbstractPolicyInterceptor(String id, String phase) {
        super(id, phase);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        this.handle(message);
    }

    protected void getTransportAssertions(Message message) {
        Exchange ex = message.getExchange();
        Conduit conduit = ex.getConduit(message);
        Assertor assertor = PolicyUtils.createAsserter(conduit);
        if (assertor == null) {
            Destination destination = ex.getDestination();
            assertor = PolicyUtils.createAsserter(destination);
        }
        if (null != assertor) {
            assertor.assertMessage(message);
        }
    }

    protected BindingFaultInfo getBindingFaultInfo(Message msg, Exception ex, BindingOperationInfo boi) {
        BindingFaultInfo bfi = msg.get(BindingFaultInfo.class);
        if (null == bfi && ex != null) {
            Class faultClass;
            Throwable cause = ex.getCause();
            if (null == cause) {
                return null;
            }
            for (BindingFaultInfo b : boi.getFaults()) {
                faultClass = b.getFaultInfo().getProperty(Class.class.getName(), Class.class);
                if (faultClass == null || !faultClass.isAssignableFrom(cause.getClass())) continue;
                bfi = b;
                msg.put(BindingFaultInfo.class, bfi);
                break;
            }
            if (null == bfi && null != boi.getWrappedOperation()) {
                for (BindingFaultInfo b : boi.getWrappedOperation().getFaults()) {
                    faultClass = b.getFaultInfo().getProperty(Class.class.getName(), Class.class);
                    if (faultClass == null || !faultClass.isAssignableFrom(cause.getClass())) continue;
                    bfi = b;
                    msg.put(BindingFaultInfo.class, bfi);
                    break;
                }
            }
        }
        return bfi;
    }

    protected abstract void handle(Message var1) throws PolicyException;
}

