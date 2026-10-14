/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jaxws_spi;

import java.io.IOException;
import javax.xml.ws.spi.http.HttpContext;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.http.DestinationRegistryImpl;
import org.apache.cxf.transport.http_jaxws_spi.HttpHandlerImpl;
import org.apache.cxf.transport.http_jaxws_spi.JAXWSHttpSpiDestination;

public class JAXWSHttpSpiTransportFactory
extends SoapTransportFactory
implements DestinationFactory {
    private HttpContext context;
    private JAXWSHttpSpiDestination destination;

    public JAXWSHttpSpiTransportFactory(HttpContext context) {
        this.context = context;
    }

    @Override
    public Destination getDestination(EndpointInfo endpointInfo, Bus bus) throws IOException {
        if (this.destination == null) {
            this.destination = new JAXWSHttpSpiDestination(bus, new DestinationRegistryImpl(), endpointInfo);
            HttpHandlerImpl handler = new HttpHandlerImpl(this.destination);
            this.context.setHandler(handler);
        }
        return this.destination;
    }
}

