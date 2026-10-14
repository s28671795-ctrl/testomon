/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.soap;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.cxf.binding.soap.HeaderUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.jaxws.handler.AbstractProtocolHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.soap.SOAPMessageContextImpl;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.transport.MessageObserver;
import org.w3c.dom.Element;

public class SOAPHandlerInterceptor
extends AbstractProtocolHandlerInterceptor<SoapMessage>
implements SoapInterceptor {
    private static final SAAJOutInterceptor SAAJ_OUT = new SAAJOutInterceptor();
    AbstractSoapInterceptor ending = new AbstractSoapInterceptor(SOAPHandlerInterceptor.class.getName() + ".ENDING", "user-protocol"){

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            SOAPHandlerInterceptor.this.handleMessageInternal(message);
        }
    };

    public SOAPHandlerInterceptor(Binding binding) {
        super(binding, "pre-protocol-frontend");
    }

    @Override
    public Set<URI> getRoles() {
        return new HashSet<URI>();
    }

    @Override
    public Set<QName> getUnderstoodHeaders() {
        HashSet<QName> understood = new HashSet<QName>();
        for (Handler h2 : this.getBinding().getHandlerChain()) {
            Set headers;
            if (!(h2 instanceof SOAPHandler) || (headers = CastUtils.cast(((SOAPHandler)h2).getHeaders())) == null) continue;
            understood.addAll(headers);
        }
        return understood;
    }

    @Override
    public void handleMessage(SoapMessage message) {
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        if (this.getInvoker(message).getProtocolHandlers().isEmpty()) {
            return;
        }
        this.checkUnderstoodHeaders(message);
        if (this.getInvoker(message).isOutbound()) {
            if (!SOAPHandlerInterceptor.chainAlreadyContainsSAAJ(message)) {
                SAAJ_OUT.handleMessage(message);
            }
            message.getInterceptorChain().add(this.ending);
        } else {
            boolean isFault = this.handleMessageInternal(message);
            SOAPMessage msg = message.getContent(SOAPMessage.class);
            if (msg != null) {
                XMLStreamReader xmlReader = this.createXMLStreamReaderFromSOAPMessage(msg);
                message.setContent(XMLStreamReader.class, xmlReader);
                try {
                    SAAJInInterceptor.replaceHeaders(msg, message);
                }
                catch (SOAPException e) {
                    e.printStackTrace();
                }
            }
            if (isFault) {
                Endpoint ep = message.getExchange().getEndpoint();
                message.getInterceptorChain().abort();
                if (ep.getInFaultObserver() != null) {
                    ep.getInFaultObserver().onMessage(message);
                }
            }
        }
    }

    private void checkUnderstoodHeaders(SoapMessage soapMessage) {
        Set<QName> paramHeaders = HeaderUtil.getHeaderQNameInOperationParam(soapMessage);
        if (soapMessage.getHeaders().isEmpty() && paramHeaders.isEmpty()) {
            this.getUnderstoodHeaders();
        }
    }

    private boolean handleMessageInternal(SoapMessage message) {
        MessageContext context = this.createProtocolMessageContext(message);
        if (context == null) {
            return true;
        }
        HandlerChainInvoker invoker = this.getInvoker(message);
        invoker.setProtocolMessageContext(context);
        if (!invoker.invokeProtocolHandlers(this.isRequestor(message), context)) {
            this.handleAbort(message, context);
        }
        if (this.isRequestor(message) && invoker.getLogicalHandlers().isEmpty() && !this.isOutbound(message) && this.isMEPComlete(message)) {
            this.onCompletion(message);
        } else if (this.isOutbound(message) && this.isMEPComlete(message)) {
            this.onCompletion(message);
        }
        return false;
    }

    private void handleAbort(SoapMessage message, MessageContext context) {
        if (this.isRequestor(message)) {
            if (this.getInvoker(message).isOutbound()) {
                message.getInterceptorChain().abort();
                MessageObserver observer = message.getExchange().get(MessageObserver.class);
                if (!message.getExchange().isOneWay() && observer != null) {
                    Endpoint e = message.getExchange().getEndpoint();
                    Message responseMsg = new MessageImpl();
                    responseMsg.setExchange(message.getExchange());
                    responseMsg = e.getBinding().createMessage(responseMsg);
                    message.getExchange().setInMessage(responseMsg);
                    SOAPMessage soapMessage = ((SOAPMessageContext)context).getMessage();
                    if (soapMessage != null) {
                        responseMsg.setContent(SOAPMessage.class, soapMessage);
                        XMLStreamReader xmlReader = this.createXMLStreamReaderFromSOAPMessage(soapMessage);
                        responseMsg.setContent(XMLStreamReader.class, xmlReader);
                    }
                    responseMsg.put("starting_at_interceptor_id", SOAPHandlerInterceptor.class.getName());
                    observer.onMessage(responseMsg);
                }
            } else {
                this.onCompletion(message);
            }
        } else if (!this.getInvoker(message).isOutbound()) {
            message.getInterceptorChain().abort();
            Endpoint e = message.getExchange().getEndpoint();
            if (!message.getExchange().isOneWay()) {
                Message responseMsg = new MessageImpl();
                responseMsg.setExchange(message.getExchange());
                responseMsg = e.getBinding().createMessage(responseMsg);
                message.getExchange().setOutMessage(responseMsg);
                SOAPMessage soapMessage = ((SOAPMessageContext)context).getMessage();
                responseMsg.setContent(SOAPMessage.class, soapMessage);
                InterceptorChain chain = OutgoingChainInterceptor.getOutInterceptorChain(message.getExchange());
                responseMsg.setInterceptorChain(chain);
                chain.doInterceptStartingAfter(responseMsg, SoapPreProtocolOutInterceptor.class.getName());
            }
        }
    }

    @Override
    protected MessageContext createProtocolMessageContext(SoapMessage message) {
        SOAPMessageContextImpl sm = new SOAPMessageContextImpl(message);
        Exchange exch = message.getExchange();
        this.setupBindingOperationInfo(exch, sm);
        SOAPMessage msg = sm.getMessage();
        if (msg != null) {
            try {
                ArrayList<SOAPElement> params = new ArrayList<SOAPElement>();
                message.put("javax.xml.ws.reference.parameters", (Object)params);
                SOAPHeader head = SAAJUtils.getHeader(msg);
                if (head != null) {
                    Iterator it = CastUtils.cast(head.getChildElements());
                    while (it != null && it.hasNext()) {
                        SOAPElement el;
                        Node nd = (Node)it.next();
                        if (!(nd instanceof SOAPElement) || !(el = (SOAPElement)nd).hasAttributeNS("http://www.w3.org/2005/08/addressing", "IsReferenceParameter") || !"1".equals(el.getAttributeNS("http://www.w3.org/2005/08/addressing", "IsReferenceParameter")) && !Boolean.parseBoolean(el.getAttributeNS("http://www.w3.org/2005/08/addressing", "IsReferenceParameter"))) continue;
                        params.add(el);
                    }
                }
                if (this.isRequestor(message) && msg.getSOAPPart().getEnvelope().getBody() != null && msg.getSOAPPart().getEnvelope().getBody().hasFault()) {
                    return null;
                }
            }
            catch (SOAPException e) {
                throw new Fault(e);
            }
        }
        return sm;
    }

    private XMLStreamReader createXMLStreamReaderFromSOAPMessage(SOAPMessage soapMessage) {
        XMLStreamReader xmlReader = null;
        try {
            DOMSource bodySource = new DOMSource(soapMessage.getSOAPPart().getEnvelope().getBody());
            xmlReader = StaxUtils.createXMLStreamReader(bodySource);
            xmlReader.nextTag();
            xmlReader.nextTag();
        }
        catch (SOAPException | XMLStreamException e) {
            e.printStackTrace();
        }
        return xmlReader;
    }

    @Override
    public void handleFault(SoapMessage message) {
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        if (this.getInvoker(message).getProtocolHandlers().isEmpty()) {
            return;
        }
        if (this.getInvoker(message).isOutbound() && !SOAPHandlerInterceptor.chainAlreadyContainsSAAJ(message)) {
            SAAJ_OUT.handleFault(message);
        }
    }

    @Override
    protected QName getOpQName(Exchange ex, Object data) {
        SOAPMessageContextImpl sm = (SOAPMessageContextImpl)data;
        try {
            org.w3c.dom.Node nd;
            SOAPMessage msg = sm.getMessage();
            if (msg == null) {
                return null;
            }
            SOAPBody body2 = SAAJUtils.getBody(msg);
            if (body2 == null) {
                return null;
            }
            for (nd = body2.getFirstChild(); nd != null && !(nd instanceof Element); nd = nd.getNextSibling()) {
            }
            if (nd != null) {
                return new QName(nd.getNamespaceURI(), nd.getLocalName());
            }
        }
        catch (SOAPException sOAPException) {
            // empty catch block
        }
        return null;
    }

    private static boolean chainAlreadyContainsSAAJ(SoapMessage message) {
        ListIterator<Interceptor<? extends Message>> listIterator = message.getInterceptorChain().getIterator();
        while (listIterator.hasNext()) {
            if (!(listIterator.next() instanceof SAAJOutInterceptor)) continue;
            return true;
        }
        return false;
    }
}

