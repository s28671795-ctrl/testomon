/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.ServiceModelUtil;

public abstract class AbstractJAXWSHandlerInterceptor<T extends Message>
extends AbstractPhaseInterceptor<T> {
    protected Binding binding;

    protected AbstractJAXWSHandlerInterceptor(Binding b, String phase) {
        super(phase);
        this.binding = b;
    }

    protected boolean isOutbound(T message) {
        return this.isOutbound(message, message.getExchange());
    }

    private boolean isOutbound(T message, Exchange ex) {
        return message == ex.getOutMessage() || message == ex.getOutFaultMessage();
    }

    protected HandlerChainInvoker getInvoker(T message) {
        boolean outbound;
        Exchange ex = message.getExchange();
        HandlerChainInvoker invoker = ex.get(HandlerChainInvoker.class);
        if (null == invoker) {
            invoker = new HandlerChainInvoker(this.binding.getHandlerChain(), this.isOutbound(message));
            ex.put(HandlerChainInvoker.class, invoker);
        }
        if (outbound = this.isOutbound(message, ex)) {
            invoker.setOutbound();
        } else {
            invoker.setInbound();
        }
        invoker.setRequestor(this.isRequestor(message));
        if (ex.isOneWay() || this.isRequestor(message) && !outbound || !this.isRequestor(message) && outbound) {
            invoker.setResponseExpected(false);
        } else {
            invoker.setResponseExpected(true);
        }
        return invoker;
    }

    protected Binding getBinding() {
        return this.binding;
    }

    public void onCompletion(T message) {
        this.getInvoker(message).mepComplete((Message)message);
    }

    public boolean isMEPComlete(T message) {
        HandlerChainInvoker invoker = this.getInvoker(message);
        if (invoker.isRequestor()) {
            if (invoker.isInbound()) {
                return true;
            }
            if (!invoker.isResponseExpected()) {
                return true;
            }
        } else {
            if (!invoker.isInbound()) {
                return true;
            }
            if (!invoker.isResponseExpected()) {
                return true;
            }
        }
        return false;
    }

    protected void setupBindingOperationInfo(Exchange exch, Object data) {
        if (exch.getBindingOperationInfo() == null) {
            QName opName = this.getOpQName(exch, data);
            if (opName == null) {
                return;
            }
            BindingOperationInfo bop = ServiceModelUtil.getOperationForWrapperElement(exch, opName, false);
            if (bop == null) {
                bop = ServiceModelUtil.getOperation(exch, opName);
            }
            if (bop != null) {
                exch.put(BindingOperationInfo.class, bop);
                if (bop.getOutput() == null) {
                    exch.setOneWay(true);
                }
            }
        }
    }

    protected QName getOpQName(Exchange ex, Object data) {
        return null;
    }
}

