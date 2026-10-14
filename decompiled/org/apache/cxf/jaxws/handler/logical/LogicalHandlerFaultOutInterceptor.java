/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.logical;

import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Binding;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.jaxws.handler.AbstractJAXWSHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageContextImpl;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class LogicalHandlerFaultOutInterceptor
extends AbstractJAXWSHandlerInterceptor<Message> {
    public static final String ORIGINAL_WRITER = "original_writer";
    LogicalHandlerFaultOutEndingInterceptor ending;

    public LogicalHandlerFaultOutInterceptor(Binding binding) {
        super(binding, "pre-marshal");
        this.ending = new LogicalHandlerFaultOutEndingInterceptor(binding);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        HandlerChainInvoker invoker = this.getInvoker(message);
        if (invoker.getLogicalHandlers().isEmpty()) {
            return;
        }
        XMLStreamWriter origWriter = message.getContent(XMLStreamWriter.class);
        Document doc = DOMUtils.newDocument();
        message.setContent(Node.class, doc);
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter(doc);
        try {
            writer.setNamespaceContext(origWriter.getNamespaceContext());
        }
        catch (XMLStreamException xMLStreamException) {
            // empty catch block
        }
        message.setContent(XMLStreamWriter.class, writer);
        message.put(ORIGINAL_WRITER, origWriter);
        message.getInterceptorChain().add(this.ending);
    }

    private static class LogicalHandlerFaultOutEndingInterceptor
    extends AbstractJAXWSHandlerInterceptor<Message> {
        LogicalHandlerFaultOutEndingInterceptor(Binding binding) {
            super(binding, "post-marshal");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) throws Fault {
            W3CDOMStreamWriter domWriter = (W3CDOMStreamWriter)message.getContent(XMLStreamWriter.class);
            XMLStreamWriter origWriter = (XMLStreamWriter)message.get(LogicalHandlerFaultOutInterceptor.ORIGINAL_WRITER);
            HandlerChainInvoker invoker = this.getInvoker(message);
            LogicalMessageContextImpl lctx = new LogicalMessageContextImpl(message);
            invoker.setLogicalMessageContext(lctx);
            boolean requestor = this.isRequestor(message);
            XMLStreamReader reader = (XMLStreamReader)message.get("LogicalHandlerInterceptor.INREADER");
            SOAPMessage origMessage = null;
            if (reader != null) {
                origMessage = message.getContent(SOAPMessage.class);
                message.setContent(XMLStreamReader.class, reader);
                message.removeContent(SOAPMessage.class);
            } else if (domWriter.getDocument().getDocumentElement() != null) {
                DOMSource source2 = new DOMSource(domWriter.getDocument());
                message.setContent(Source.class, source2);
                message.setContent(Node.class, domWriter.getDocument());
                message.setContent(XMLStreamReader.class, StaxUtils.createXMLStreamReader(domWriter.getDocument()));
            }
            try {
                if (invoker.invokeLogicalHandlersHandleFault(requestor, lctx)) {
                    // empty if block
                }
            }
            catch (RuntimeException exception) {
                Exchange exchange = message.getExchange();
                Fault ex = new Fault(exception);
                FaultMode mode = message.get(FaultMode.class);
                Message faultMessage = exchange.getOutMessage();
                if (null == faultMessage) {
                    faultMessage = new MessageImpl();
                    faultMessage.setExchange(message.getExchange());
                    faultMessage = exchange.getEndpoint().getBinding().createMessage(faultMessage);
                }
                faultMessage.setContent(Exception.class, ex);
                if (null != mode) {
                    faultMessage.put(FaultMode.class, mode);
                }
                exchange.setOutMessage(null);
                exchange.setOutFaultMessage(faultMessage);
                InterceptorChain ic = message.getInterceptorChain();
                ic.reset();
                this.onCompletion(message);
                faultMessage.setInterceptorChain(ic);
                ic.doIntercept(faultMessage);
                return;
            }
            if (origMessage != null) {
                message.setContent(SOAPMessage.class, origMessage);
            }
            try {
                reader = message.getContent(XMLStreamReader.class);
                message.removeContent(XMLStreamReader.class);
                if (reader != null) {
                    StaxUtils.copy(reader, origWriter);
                } else if (domWriter.getDocument().getDocumentElement() != null) {
                    StaxUtils.copy(domWriter.getDocument(), origWriter);
                }
                message.setContent(XMLStreamWriter.class, origWriter);
                return;
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
        }
    }
}

