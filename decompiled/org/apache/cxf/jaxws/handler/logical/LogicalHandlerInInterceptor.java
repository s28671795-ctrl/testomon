/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.logical;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Binding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.jaxws.handler.AbstractJAXWSHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageContextImpl;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;

public class LogicalHandlerInInterceptor
extends AbstractJAXWSHandlerInterceptor<Message> {
    public LogicalHandlerInInterceptor(Binding binding) {
        super(binding, "pre-protocol-frontend");
        this.addAfter(SOAPHandlerInterceptor.class.getName());
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
        if (!requestor) {
            this.setupBindingOperationInfo(message.getExchange(), lctx);
        }
        if (!invoker.invokeLogicalHandlers(requestor, lctx) && !requestor) {
            this.handleAbort(message, null);
        }
        if (!this.isOutbound(message) && this.isMEPComlete(message)) {
            this.onCompletion(message);
        }
    }

    private void handleAbort(Message message, W3CDOMStreamWriter writer) {
        message.getInterceptorChain().abort();
        if (!message.getExchange().isOneWay()) {
            Endpoint e = message.getExchange().getEndpoint();
            Message responseMsg = new MessageImpl();
            responseMsg.setExchange(message.getExchange());
            responseMsg = e.getBinding().createMessage(responseMsg);
            message.getExchange().setOutMessage(responseMsg);
            XMLStreamReader reader = message.getContent(XMLStreamReader.class);
            if (reader == null && writer != null) {
                reader = StaxUtils.createXMLStreamReader(writer.getDocument());
            }
            InterceptorChain chain = OutgoingChainInterceptor.getOutInterceptorChain(message.getExchange());
            responseMsg.setInterceptorChain(chain);
            responseMsg.put("LogicalHandlerInterceptor.INREADER", reader);
            chain.doIntercept(responseMsg);
        }
    }

    @Override
    protected QName getOpQName(Exchange ex, Object data) {
        LogicalMessageContextImpl sm = (LogicalMessageContextImpl)data;
        Source src = sm.getMessage().getPayload();
        if (src instanceof DOMSource) {
            DOMSource dsrc = (DOMSource)src;
            String ln = dsrc.getNode().getLocalName();
            String ns = dsrc.getNode().getNamespaceURI();
            return new QName(ns, ln);
        }
        return null;
    }
}

