/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.w3c.dom.Node;

public class HandlerChainInvoker {
    private static final Logger LOG = LogUtils.getL7dLogger(HandlerChainInvoker.class);
    private final List<Handler<?>> protocolHandlers = new ArrayList();
    private List<LogicalHandler<?>> logicalHandlers = new ArrayList();
    private final List<Handler<?>> invokedHandlers = new ArrayList();
    private final List<Handler<?>> closeHandlers = new ArrayList();
    private boolean outbound;
    private boolean isRequestor;
    private boolean responseExpected = true;
    private boolean faultExpected;
    private boolean closed;
    private boolean messageDirectionReversed;
    private Exception fault;
    private LogicalMessageContext logicalMessageContext;
    private MessageContext protocolMessageContext;

    public HandlerChainInvoker(List<Handler> hc) {
        this(hc, true);
    }

    public HandlerChainInvoker(List<Handler> hc, boolean isOutbound) {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "invoker for chain size: " + (hc != null ? hc.size() : 0));
        }
        if (hc != null) {
            for (Handler h2 : hc) {
                if (h2 instanceof LogicalHandler) {
                    this.logicalHandlers.add((LogicalHandler)h2);
                    continue;
                }
                this.protocolHandlers.add(h2);
            }
        }
        this.outbound = isOutbound;
    }

    public List<LogicalHandler<?>> getLogicalHandlers() {
        return this.logicalHandlers;
    }

    public List<Handler<?>> getProtocolHandlers() {
        return this.protocolHandlers;
    }

    public LogicalMessageContext getLogicalMessageContext() {
        return this.logicalMessageContext;
    }

    public void setLogicalMessageContext(LogicalMessageContext mc) {
        this.logicalMessageContext = mc;
    }

    public MessageContext getProtocolMessageContext() {
        return this.protocolMessageContext;
    }

    public void setProtocolMessageContext(MessageContext mc) {
        this.protocolMessageContext = mc;
    }

    public boolean invokeLogicalHandlers(boolean requestor, LogicalMessageContext context) {
        return this.invokeHandlerChain(this.logicalHandlers, context);
    }

    public boolean invokeLogicalHandlersHandleFault(boolean requestor, LogicalMessageContext context) {
        return this.invokeHandlerChainHandleFault(this.logicalHandlers, context);
    }

    public boolean invokeProtocolHandlers(boolean requestor, MessageContext context) {
        return this.invokeHandlerChain(this.protocolHandlers, context);
    }

    public boolean invokeProtocolHandlersHandleFault(boolean requestor, MessageContext context) {
        return this.invokeHandlerChainHandleFault(this.protocolHandlers, context);
    }

    public void setResponseExpected(boolean expected) {
        this.responseExpected = expected;
    }

    public boolean isResponseExpected() {
        return this.responseExpected;
    }

    public boolean isOutbound() {
        return this.outbound;
    }

    public boolean isInbound() {
        return !this.outbound;
    }

    public boolean isRequestor() {
        return this.isRequestor;
    }

    public void setRequestor(boolean requestor) {
        this.isRequestor = requestor;
    }

    public void setInbound() {
        this.outbound = false;
    }

    public void setOutbound() {
        this.outbound = true;
    }

    public boolean faultRaised() {
        return null != this.fault || this.faultExpected;
    }

    public Exception getFault() {
        return this.fault;
    }

    public void setFault(boolean fe) {
        this.faultExpected = fe;
    }

    public void mepComplete(Message message) {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "closing protocol handlers - handler count:" + this.invokedHandlers.size());
        }
        if (this.isClosed()) {
            return;
        }
        this.invokeReversedClose();
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void adoptLogicalHandlers(HandlerChainInvoker invoker) {
        this.logicalHandlers = invoker.getLogicalHandlers();
    }

    List<Handler<?>> getInvokedHandlers() {
        return Collections.unmodifiableList(this.invokedHandlers);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean invokeHandlerChain(List<? extends Handler<?>> handlerChain, MessageContext ctx) {
        if (handlerChain.isEmpty()) {
            LOG.log(Level.FINEST, "no handlers registered");
            return true;
        }
        if (this.isClosed()) {
            return false;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "invoking handlers, direction: " + (this.outbound ? "outbound" : "inbound"));
        }
        if (!this.outbound) {
            handlerChain = this.reverseHandlerChain(handlerChain);
        }
        boolean continueProcessing = true;
        MessageContext oldCtx = null;
        try {
            oldCtx = WebServiceContextImpl.setMessageContext(ctx);
            continueProcessing = this.invokeHandleMessage(handlerChain, ctx);
        }
        finally {
            if (oldCtx == null) {
                WebServiceContextImpl.clear();
            } else {
                WebServiceContextImpl.setMessageContext(oldCtx);
            }
        }
        return continueProcessing;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean invokeHandlerChainHandleFault(List<? extends Handler<?>> handlerChain, MessageContext ctx) {
        if (handlerChain.isEmpty()) {
            LOG.log(Level.FINEST, "no handlers registered");
            return true;
        }
        if (this.isClosed()) {
            return false;
        }
        if (this.fault != null) {
            if (!(this.fault instanceof ProtocolException)) {
                return true;
            }
            if (!this.responseExpected && !this.messageDirectionReversed) {
                return true;
            }
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "invoking handlers, direction: " + (this.outbound ? "outbound" : "inbound"));
        }
        this.setMessageOutboundProperty(ctx);
        if (!this.outbound) {
            handlerChain = this.reverseHandlerChain(handlerChain);
        }
        boolean continueProcessing = true;
        MessageContext oldCtx = null;
        try {
            oldCtx = WebServiceContextImpl.setMessageContext(ctx);
            continueProcessing = this.invokeHandleFault(handlerChain, ctx);
        }
        finally {
            if (oldCtx == null) {
                WebServiceContextImpl.clear();
            } else {
                WebServiceContextImpl.setMessageContext(oldCtx);
            }
        }
        return continueProcessing;
    }

    private boolean invokeHandleFault(List<? extends Handler<?>> handlerChain, MessageContext ctx) {
        boolean continueProcessing = true;
        try {
            for (Handler<?> h2 : handlerChain) {
                if (this.invokeThisHandler(h2)) {
                    this.closeHandlers.add(h2);
                    this.markHandlerInvoked(h2);
                    Handler<?> lh = h2;
                    continueProcessing = lh.handleFault(ctx);
                }
                if (continueProcessing) continue;
                break;
            }
        }
        catch (RuntimeException e) {
            LOG.log(Level.WARNING, "HANDLER_RAISED_RUNTIME_EXCEPTION", e);
            continueProcessing = false;
            throw e;
        }
        return continueProcessing;
    }

    private boolean invokeHandleMessage(List<? extends Handler<?>> handlerChain, MessageContext ctx) {
        boolean continueProcessing = true;
        try {
            for (Handler<?> h2 : handlerChain) {
                if (this.invokeThisHandler(h2)) {
                    this.closeHandlers.add(h2);
                    this.markHandlerInvoked(h2);
                    Handler<?> lh = h2;
                    continueProcessing = lh.handleMessage(ctx);
                }
                if (continueProcessing) continue;
                if (this.responseExpected) {
                    this.changeMessageDirection(ctx);
                    this.messageDirectionReversed = true;
                } else {
                    this.invokeReversedClose();
                }
                break;
            }
        }
        catch (ProtocolException e) {
            LOG.log(Level.FINE, "handleMessage raised exception", e);
            if (this.responseExpected) {
                this.changeMessageDirection(ctx);
                this.messageDirectionReversed = true;
            }
            if (this.isRequestor()) {
                if (this.responseExpected) {
                    this.setFaultMessage(ctx, e);
                    this.invokeReversedHandleFault(ctx);
                } else {
                    this.invokeReversedClose();
                }
                continueProcessing = false;
                this.setFault(e);
                if (this.responseExpected || this.isInbound()) {
                    if (e instanceof SOAPFaultException) {
                        throw this.mapSoapFault((SOAPFaultException)e);
                    }
                    throw e;
                }
            } else {
                continueProcessing = false;
                if (this.responseExpected || this.outbound) {
                    this.setFault(e);
                    if (e instanceof SOAPFaultException) {
                        throw this.mapSoapFault((SOAPFaultException)e);
                    }
                    throw e;
                }
                this.invokeReversedClose();
            }
        }
        catch (RuntimeException e) {
            LOG.log(Level.WARNING, "HANDLER_RAISED_RUNTIME_EXCEPTION", e);
            if (this.responseExpected) {
                this.changeMessageDirection(ctx);
                this.messageDirectionReversed = true;
            }
            if (this.isRequestor()) {
                this.invokeReversedClose();
                continueProcessing = false;
                this.setFault(e);
                throw e;
            }
            if (!this.responseExpected && !this.outbound) {
                this.invokeReversedClose();
                continueProcessing = false;
            }
            continueProcessing = false;
            this.setFault(e);
            throw e;
        }
        return continueProcessing;
    }

    private SoapFault mapSoapFault(SOAPFaultException sfe) {
        SoapFault sf = new SoapFault(sfe.getFault().getFaultString(), (Throwable)sfe, sfe.getFault().getFaultCodeAsQName());
        sf.setRole(sfe.getFault().getFaultActor());
        if (sfe.getFault().hasDetail()) {
            sf.setDetail(sfe.getFault().getDetail());
        }
        return sf;
    }

    private void setFaultMessage(MessageContext mc, Exception exception) {
        Message msg = ((WrappedMessageContext)mc).getWrappedMessage();
        msg.setContent(Exception.class, exception);
        msg.removeContent(XMLStreamReader.class);
        msg.removeContent(Source.class);
        try {
            SOAPMessage soapMessage = null;
            SoapVersion version = null;
            if (msg instanceof SoapMessage) {
                version = ((SoapMessage)msg).getVersion();
            }
            soapMessage = SAAJFactoryResolver.createMessageFactory(version).createMessage();
            msg.setContent(SOAPMessage.class, soapMessage);
            SOAPBody body2 = SAAJUtils.getBody(soapMessage);
            SOAPFault soapFault = body2.addFault();
            if (exception instanceof SOAPFaultException) {
                SOAPFaultException sf = (SOAPFaultException)exception;
                soapFault.setFaultString(sf.getFault().getFaultString());
                SAAJUtils.setFaultCode(soapFault, sf.getFault().getFaultCodeAsQName());
                soapFault.setFaultActor(sf.getFault().getFaultActor());
                if (sf.getFault().hasDetail()) {
                    Node nd = soapMessage.getSOAPPart().importNode(sf.getFault().getDetail(), true);
                    soapFault.addDetail();
                    for (nd = nd.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                        soapFault.getDetail().appendChild(nd);
                    }
                }
            } else if (exception instanceof Fault) {
                SoapFault sf = SoapFault.createFault((Fault)exception, ((SoapMessage)msg).getVersion());
                soapFault.setFaultString(sf.getReason());
                SAAJUtils.setFaultCode(soapFault, sf.getFaultCode());
                if (sf.hasDetails()) {
                    soapFault.addDetail();
                    Node nd = soapMessage.getSOAPPart().importNode(sf.getDetail(), true);
                    for (nd = nd.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                        soapFault.getDetail().appendChild(nd);
                    }
                }
            } else {
                SAAJUtils.setFaultCode(soapFault, new QName("http://cxf.apache.org/faultcode", "HandlerFault"));
                soapFault.setFaultString(exception.getMessage());
            }
        }
        catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    private boolean invokeReversedHandleFault(MessageContext ctx) {
        boolean continueProcessing = true;
        try {
            for (int index = this.invokedHandlers.size() - 2; index >= 0 && continueProcessing; --index) {
                Handler<?> h2 = this.invokedHandlers.get(index);
                if (h2 instanceof LogicalHandler) {
                    LogicalHandler lh = (LogicalHandler)h2;
                    continueProcessing = lh.handleFault(this.logicalMessageContext);
                } else {
                    Handler<?> ph = h2;
                    continueProcessing = ph.handleFault(this.protocolMessageContext);
                }
                if (continueProcessing) continue;
                this.invokeReversedClose();
                break;
            }
        }
        catch (RuntimeException e) {
            LOG.log(Level.WARNING, "HANDLER_RAISED_RUNTIME_EXCEPTION", e);
            this.invokeReversedClose();
            continueProcessing = false;
            this.closed = true;
            throw e;
        }
        this.invokeReversedClose();
        return continueProcessing;
    }

    private void invokeReversedClose() {
        for (int index = this.invokedHandlers.size() - 1; index >= 0; --index) {
            Handler<?> handler = this.invokedHandlers.get(index);
            if (handler instanceof LogicalHandler) {
                handler.close(this.logicalMessageContext);
            } else {
                handler.close(this.protocolMessageContext);
            }
            this.invokedHandlers.remove(index);
        }
        this.closed = true;
    }

    private boolean invokeThisHandler(Handler<?> h2) {
        boolean ret = true;
        if (this.messageDirectionReversed) {
            boolean bl = ret = this.invokedHandlers.contains(h2) && !this.isTheLastInvokedHandler(h2);
        }
        if (ret && LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "invoking handler of type " + h2.getClass().getName());
        }
        return ret;
    }

    private boolean isTheLastInvokedHandler(Handler<?> h2) {
        return this.invokedHandlers.contains(h2) && this.invokedHandlers.indexOf(h2) == this.invokedHandlers.size() - 1;
    }

    private void markHandlerInvoked(Handler<?> h2) {
        if (!this.invokedHandlers.contains(h2)) {
            this.invokedHandlers.add(h2);
        }
    }

    private void changeMessageDirection(MessageContext context) {
        this.outbound = !this.outbound;
        this.setMessageOutboundProperty(context);
    }

    private void setMessageOutboundProperty(MessageContext context) {
        context.put("javax.xml.ws.handler.message.outbound", this.outbound);
        if (this.logicalMessageContext != null) {
            this.logicalMessageContext.put("javax.xml.ws.handler.message.outbound", this.outbound);
        }
        if (this.protocolMessageContext != null) {
            this.protocolMessageContext.put("javax.xml.ws.handler.message.outbound", this.outbound);
        }
    }

    private <T extends Handler<?>> List<T> reverseHandlerChain(List<T> handlerChain) {
        ArrayList<T> reversedHandlerChain = new ArrayList<T>();
        reversedHandlerChain.addAll(handlerChain);
        Collections.reverse(reversedHandlerChain);
        return reversedHandlerChain;
    }

    protected final void setFault(Exception ex) {
        this.fault = ex;
    }
}

