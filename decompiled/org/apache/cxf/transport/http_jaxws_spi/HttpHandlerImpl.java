/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jaxws_spi;

import java.io.IOException;
import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapAddress;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http_jaxws_spi.HttpServletRequestAdapter;
import org.apache.cxf.transport.http_jaxws_spi.HttpServletResponseAdapter;
import org.apache.cxf.transport.http_jaxws_spi.JAXWSHttpSpiDestination;
import org.apache.cxf.wsdl.http.AddressType;

public class HttpHandlerImpl
extends HttpHandler {
    private JAXWSHttpSpiDestination destination;

    public HttpHandlerImpl(JAXWSHttpSpiDestination destination) {
        this.destination = destination;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            EndpointInfo ei = this.destination.getEndpointInfo();
            if (ei != null) {
                String ad = ei.getAddress();
                String path = exchange.getHttpContext().getPath();
                if (ad != null && ad.equals(path)) {
                    EndpointInfo endpointInfo = ei;
                    synchronized (endpointInfo) {
                        String contextPath = exchange.getContextPath();
                        ei.setAddress(contextPath + path);
                        if (ei.getExtensor(AddressType.class) != null) {
                            ei.getExtensor(AddressType.class).setLocation(contextPath + path);
                        } else if (ei.getExtensor(SoapAddress.class) != null) {
                            ei.getExtensor(SoapAddress.class).setLocationURI(contextPath + path);
                        }
                    }
                }
            }
            this.destination.doService(new HttpServletRequestAdapter(exchange), new HttpServletResponseAdapter(exchange));
        }
        finally {
            exchange.close();
        }
    }
}

