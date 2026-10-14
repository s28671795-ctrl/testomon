/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jaxws_spi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.continuations.SuspendedInvocationException;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HTTPSession;

public class JAXWSHttpSpiDestination
extends AbstractHTTPDestination {
    static final Logger LOG = LogUtils.getL7dLogger(JAXWSHttpSpiDestination.class);

    public JAXWSHttpSpiDestination(Bus b, DestinationRegistry registry, EndpointInfo ei) throws IOException {
        super(b, registry, ei, ei.getAddress(), false);
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        try {
            this.serviceRequest(req, resp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
        }
    }

    protected void serviceRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Service http request on thread: " + Thread.currentThread());
        }
        MessageImpl inMessage = new MessageImpl();
        ExchangeImpl exchange = new ExchangeImpl();
        exchange.setInMessage(inMessage);
        this.setupMessage(inMessage, null, req.getServletContext(), req, resp);
        inMessage.setDestination(this);
        exchange.setSession(new HTTPSession(req));
        try {
            this.incomingObserver.onMessage(inMessage);
            resp.flushBuffer();
        }
        catch (SuspendedInvocationException ex) {
            if (ex.getRuntimeException() != null) {
                throw ex.getRuntimeException();
            }
        }
        catch (Fault ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw ex;
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        finally {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Finished servicing http request on thread: " + Thread.currentThread());
            }
        }
    }

    @Override
    protected String getBasePath(String contextPath) throws IOException {
        return contextPath + this.getAddress().getAddress().getValue();
    }
}

