/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.soap;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import org.apache.cxf.binding.soap.HeaderUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.handler.AbstractProtocolHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.soap.SOAPMessageContextImpl;
import org.apache.cxf.staxutils.StaxUtils;

public class SOAPHandlerFaultInInterceptor
extends AbstractProtocolHandlerInterceptor<SoapMessage>
implements SoapInterceptor {
    public SOAPHandlerFaultInInterceptor(Binding binding) {
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
        SOAPMessage msg;
        if (this.binding.getHandlerChain().isEmpty()) {
            return;
        }
        if (this.getInvoker(message).getProtocolHandlers().isEmpty()) {
            return;
        }
        this.checkUnderstoodHeaders(message);
        MessageContext context = this.createProtocolMessageContext(message);
        HandlerChainInvoker invoker = this.getInvoker(message);
        invoker.setProtocolMessageContext(context);
        if (!invoker.invokeProtocolHandlersHandleFault(this.isRequestor(message), context)) {
            this.handleAbort(message, context);
        }
        if ((msg = message.getContent(SOAPMessage.class)) != null) {
            XMLStreamReader xmlReader = this.createXMLStreamReaderFromSOAPMessage(msg);
            message.setContent(XMLStreamReader.class, xmlReader);
        }
    }

    private void checkUnderstoodHeaders(SoapMessage soapMessage) {
        Set<QName> paramHeaders = HeaderUtil.getHeaderQNameInOperationParam(soapMessage);
        if (soapMessage.getHeaders().isEmpty() && paramHeaders.isEmpty()) {
            this.getUnderstoodHeaders();
        }
    }

    private void handleAbort(SoapMessage message, MessageContext context) {
        if (this.isRequestor(message)) {
            if (!this.getInvoker(message).isOutbound()) {
                this.onCompletion(message);
            }
        } else if (!this.getInvoker(message).isOutbound()) {
            // empty if block
        }
    }

    @Override
    protected MessageContext createProtocolMessageContext(SoapMessage message) {
        return new SOAPMessageContextImpl(message);
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
}

