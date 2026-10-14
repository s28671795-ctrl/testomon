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
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.handler.AbstractJAXWSHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerInInterceptor;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.transport.MessageObserver;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LogicalHandlerOutInterceptor
extends AbstractJAXWSHandlerInterceptor<Message> {
    public static final String ORIGINAL_WRITER = LogicalHandlerOutInterceptor.class.getName() + ".original_writer";
    private LogicalHandlerOutEndingInterceptor ending;

    public LogicalHandlerOutInterceptor(Binding binding) {
        super(binding, "pre-marshal");
        this.ending = new LogicalHandlerOutEndingInterceptor(binding);
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
        Node nd = message.getContent(Node.class);
        SOAPMessage m4 = message.getContent(SOAPMessage.class);
        Document document = null;
        if (m4 != null) {
            document = m4.getSOAPPart();
        } else if (nd != null) {
            document = nd.getOwnerDocument();
        } else {
            document = DOMUtils.newDocument();
            message.setContent(Node.class, document);
        }
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter(document.createDocumentFragment());
        message.setContent(XMLStreamWriter.class, writer);
        message.put(ORIGINAL_WRITER, origWriter);
        message.getInterceptorChain().add(this.ending);
    }

    @Override
    public void handleFault(Message message) {
        super.handleFault(message);
        XMLStreamWriter os = (XMLStreamWriter)message.get(ORIGINAL_WRITER);
        if (os != null) {
            message.setContent(XMLStreamWriter.class, os);
        }
    }

    private static class LogicalHandlerOutEndingInterceptor
    extends AbstractJAXWSHandlerInterceptor<Message> {
        LogicalHandlerOutEndingInterceptor(Binding binding) {
            super(binding, "post-marshal");
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            W3CDOMStreamWriter domWriter = (W3CDOMStreamWriter)message.getContent(XMLStreamWriter.class);
            XMLStreamWriter origWriter = (XMLStreamWriter)message.get(ORIGINAL_WRITER);
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
            } else if (domWriter.getCurrentFragment() != null) {
                Node nd;
                DocumentFragment frag = domWriter.getCurrentFragment();
                for (nd = frag.getFirstChild(); nd != null && !(nd instanceof Element); nd = nd.getNextSibling()) {
                }
                DOMSource source2 = new DOMSource(nd);
                message.setContent(Source.class, source2);
                message.setContent(XMLStreamReader.class, new W3CDOMStreamReader(domWriter.getCurrentFragment()));
            } else if (domWriter.getDocument().getDocumentElement() != null) {
                DOMSource source3 = new DOMSource(domWriter.getDocument());
                message.setContent(Source.class, source3);
                message.setContent(XMLStreamReader.class, StaxUtils.createXMLStreamReader(domWriter.getDocument()));
            }
            if (!invoker.invokeLogicalHandlers(requestor, lctx) && requestor) {
                message.getInterceptorChain().abort();
                if (!message.getExchange().isOneWay()) {
                    Endpoint e = message.getExchange().getEndpoint();
                    Message responseMsg = new MessageImpl();
                    responseMsg.setExchange(message.getExchange());
                    responseMsg = e.getBinding().createMessage(responseMsg);
                    MessageObserver observer = message.getExchange().get(MessageObserver.class);
                    if (observer != null) {
                        responseMsg.setContent(XMLStreamReader.class, message.getContent(XMLStreamReader.class));
                        message.getExchange().setInMessage(responseMsg);
                        responseMsg.put("starting_at_interceptor_id", LogicalHandlerInInterceptor.class.getName());
                        observer.onMessage(responseMsg);
                    }
                    return;
                }
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
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
        }
    }
}

