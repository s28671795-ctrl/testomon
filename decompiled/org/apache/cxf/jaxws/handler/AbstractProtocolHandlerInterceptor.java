/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import javax.xml.ws.Binding;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.jaxws.handler.AbstractJAXWSHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.message.Message;

public abstract class AbstractProtocolHandlerInterceptor<T extends Message>
extends AbstractJAXWSHandlerInterceptor<T> {
    protected AbstractProtocolHandlerInterceptor(Binding binding) {
        super(binding, "user-protocol");
    }

    protected AbstractProtocolHandlerInterceptor(Binding binding, String phase) {
        super(binding, phase);
    }

    @Override
    public void handleMessage(T message) {
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        MessageContext context = this.createProtocolMessageContext(message);
        HandlerChainInvoker invoker = this.getInvoker(message);
        invoker.setProtocolMessageContext(context);
        invoker.invokeProtocolHandlers(this.isRequestor(message), context);
        this.onCompletion(message);
    }

    protected MessageContext createProtocolMessageContext(T message) {
        return new WrappedMessageContext((Message)message, MessageContext.Scope.HANDLER);
    }
}

