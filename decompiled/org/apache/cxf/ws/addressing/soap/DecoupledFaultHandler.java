/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.soap;

import java.util.Iterator;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.soap.MAPCodec;

public class DecoupledFaultHandler
extends AbstractSoapInterceptor {
    public static final String WSA_ACTION = "http://schemas.xmlsoap.org/wsdl/soap/envelope/fault";

    public DecoupledFaultHandler() {
        super("pre-protocol");
        this.addBefore(MAPCodec.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) {
    }

    @Override
    public void handleFault(SoapMessage message) {
        Exchange exchange;
        Message inMessage;
        AddressingProperties maps;
        if (!ContextUtils.isRequestor(message) && (maps = ContextUtils.retrieveMAPs(inMessage = (exchange = message.getExchange()).getInMessage(), false, false, true)) != null && !ContextUtils.isGenericAddress(maps.getFaultTo())) {
            if (exchange.getOutMessage() != null) {
                message = (SoapMessage)exchange.getOutMessage();
            }
            Iterator<Header> iterator2 = message.getHeaders().iterator();
            while (iterator2.hasNext()) {
                Header header = iterator2.next();
                if (this.isWSAHeader(header)) continue;
                iterator2.remove();
            }
            exchange.setOneWay(false);
            exchange.setOutMessage(message);
            message.put(ContextUtils.ACTION, (Object)WSA_ACTION);
            Destination destination = this.createDecoupledDestination(exchange, maps.getFaultTo());
            exchange.setDestination(destination);
        }
    }

    protected Destination createDecoupledDestination(Exchange exchange, EndpointReferenceType epr) {
        return ContextUtils.createDecoupledDestination(exchange, epr);
    }

    private boolean isWSAHeader(Header header) {
        return header.getName().getNamespaceURI().startsWith("http://www.w3.org/2005/08/addressing");
    }
}

