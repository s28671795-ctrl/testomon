/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.logical;

import javax.xml.ws.Binding;
import org.apache.cxf.jaxws.handler.AbstractJAXWSHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageContextImpl;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerFaultInInterceptor;
import org.apache.cxf.message.Message;

public class LogicalHandlerFaultInInterceptor
extends AbstractJAXWSHandlerInterceptor<Message> {
    public LogicalHandlerFaultInInterceptor(Binding binding) {
        super(binding, "pre-protocol-frontend");
        this.addAfter(SOAPHandlerFaultInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) {
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        HandlerChainInvoker invoker = this.getInvoker(message);
        if (invoker.getLogicalHandlers().isEmpty()) {
            return;
        }
        LogicalMessageContextImpl lctx = new LogicalMessageContextImpl(message);
        invoker.setLogicalMessageContext(lctx);
        boolean requestor = this.isRequestor(message);
        if (invoker.invokeLogicalHandlersHandleFault(requestor, lctx) || !requestor) {
            // empty if block
        }
        if (!this.isOutbound(message) && this.isMEPComlete(message)) {
            this.onCompletion(message);
        }
    }
}

