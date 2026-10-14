/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.soap;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.binding.soap.HeaderUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.handler.AbstractProtocolHandlerInterceptor;
import org.apache.cxf.jaxws.handler.HandlerChainInvoker;
import org.apache.cxf.jaxws.handler.soap.SOAPMessageContextImpl;
import org.w3c.dom.Node;

public class SOAPHandlerFaultOutInterceptor
extends AbstractProtocolHandlerInterceptor<SoapMessage>
implements SoapInterceptor {
    private static final SAAJOutInterceptor SAAJ_OUT = new SAAJOutInterceptor();
    private static final String ENDING_ID = SOAPHandlerFaultOutInterceptor.class.getName() + ".ENDING";
    AbstractSoapInterceptor ending = new AbstractSoapInterceptor(ENDING_ID, "user-protocol"){

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            SOAPHandlerFaultOutInterceptor.this.handleMessageInternal(message);
        }
    };

    public SOAPHandlerFaultOutInterceptor(Binding binding) {
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
            message.setContent(SOAPMessage.class, null);
            SAAJ_OUT.handleMessage(message);
            message.getInterceptorChain().add(this.ending);
        }
    }

    private void checkUnderstoodHeaders(SoapMessage soapMessage) {
        Set<QName> paramHeaders = HeaderUtil.getHeaderQNameInOperationParam(soapMessage);
        if (soapMessage.getHeaders().isEmpty() && paramHeaders.isEmpty()) {
            this.getUnderstoodHeaders();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleMessageInternal(SoapMessage message) {
        MessageContext context = this.createProtocolMessageContext(message);
        HandlerChainInvoker invoker = this.getInvoker(message);
        invoker.setProtocolMessageContext(context);
        try {
            if (invoker.invokeProtocolHandlersHandleFault(this.isRequestor(message), context)) {
                // empty if block
            }
        }
        catch (RuntimeException exception) {
            try {
                SOAPMessage originalMsg = message.getContent(SOAPMessage.class);
                SOAPBody body2 = originalMsg.getSOAPPart().getEnvelope().getBody();
                body2.removeContents();
                SOAPFault soapFault = body2.addFault();
                if (exception instanceof SOAPFaultException) {
                    SOAPFaultException sf = (SOAPFaultException)exception;
                    soapFault.setFaultString(sf.getFault().getFaultString());
                    SAAJUtils.setFaultCode(soapFault, sf.getFault().getFaultCodeAsQName());
                    soapFault.setFaultActor(sf.getFault().getFaultActor());
                    if (sf.getFault().hasDetail()) {
                        Node nd = originalMsg.getSOAPPart().importNode(sf.getFault().getDetail().getFirstChild(), true);
                        soapFault.addDetail().appendChild(nd);
                    }
                }
                if (exception instanceof Fault) {
                    SoapFault sf = SoapFault.createFault((Fault)exception, message.getVersion());
                    soapFault.setFaultString(sf.getReason());
                    SAAJUtils.setFaultCode(soapFault, sf.getFaultCode());
                    if (sf.hasDetails()) {
                        soapFault.addDetail();
                        Node nd = originalMsg.getSOAPPart().importNode(sf.getDetail(), true);
                        for (nd = nd.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                            soapFault.getDetail().appendChild(nd);
                        }
                    }
                }
                soapFault.setFaultString(exception.getMessage());
                SAAJUtils.setFaultCode(soapFault, new QName("http://cxf.apache.org/faultcode", "HandleFault"));
            }
            catch (SOAPException e) {
                e.printStackTrace();
            }
        }
        this.onCompletion(message);
    }

    @Override
    protected MessageContext createProtocolMessageContext(SoapMessage message) {
        return new SOAPMessageContextImpl(message);
    }
}

