/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.logging.RegexLoggingFilter;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.ManagedEndpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerLifeCycleManager;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.MultipleEndpointObserver;

public class ServerImpl
implements Server {
    private static final Logger LOG = LogUtils.getL7dLogger(ServerImpl.class);
    protected final Endpoint endpoint;
    protected final Bus bus;
    protected final BindingFactory bindingFactory;
    private Destination destination;
    private ServerRegistry serverRegistry;
    private ServerLifeCycleManager slcMgr;
    private InstrumentationManager iMgr;
    private ManagedEndpoint mep;
    private boolean stopped = true;

    public ServerImpl(Bus bus, Endpoint endpoint, DestinationFactory destinationFactory, BindingFactory bindingFactory) throws BusException, IOException {
        this.endpoint = endpoint;
        this.bus = bus;
        this.bindingFactory = bindingFactory;
        this.initDestination(destinationFactory);
    }

    private void initDestination(DestinationFactory destinationFactory) throws BusException, IOException {
        EndpointInfo ei = this.endpoint.getEndpointInfo();
        if (destinationFactory == null) {
            if (ei.getAddress() != null && ei.getAddress().indexOf("local://") != -1) {
                destinationFactory = this.bus.getExtension(DestinationFactoryManager.class).getDestinationFactoryForUri(ei.getAddress());
            }
            if (destinationFactory == null) {
                destinationFactory = this.bus.getExtension(DestinationFactoryManager.class).getDestinationFactory(ei.getTransportId());
            }
        }
        this.destination = destinationFactory.getDestination(ei, this.bus);
        String wantFilter = ei.getAddress();
        if (wantFilter != null && wantFilter.startsWith("jms")) {
            RegexLoggingFilter filter2 = new RegexLoggingFilter();
            filter2.setPattern("jms(.*?)password=+([^ ]+)");
            filter2.setGroup(2);
            wantFilter = filter2.filter(wantFilter).toString();
        }
        LOG.info("Setting the server's publish address to be " + wantFilter);
        this.serverRegistry = this.bus.getExtension(ServerRegistry.class);
        this.mep = new ManagedEndpoint(this.bus, this.endpoint, this);
        this.slcMgr = this.bus.getExtension(ServerLifeCycleManager.class);
        if (this.slcMgr != null) {
            this.slcMgr.registerListener(this.mep);
        }
        this.iMgr = this.bus.getExtension(InstrumentationManager.class);
        if (this.iMgr != null) {
            try {
                this.iMgr.register(this.mep);
            }
            catch (JMException jmex) {
                LOG.log(Level.WARNING, "Registering ManagedEndpoint failed.", jmex);
            }
        }
    }

    @Override
    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public void start() {
        if (!this.stopped) {
            return;
        }
        LOG.fine("Server is starting.");
        this.bindingFactory.addListener(this.destination, this.endpoint);
        if (null != this.serverRegistry) {
            LOG.fine("register the server to serverRegistry ");
            this.serverRegistry.register(this);
        }
        if (this.slcMgr == null) {
            this.slcMgr = this.bus.getExtension(ServerLifeCycleManager.class);
            if (this.slcMgr != null && this.mep != null) {
                this.slcMgr.registerListener(this.mep);
            }
        }
        if (this.slcMgr != null) {
            this.slcMgr.startServer(this);
        }
        this.stopped = false;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    @Override
    public boolean isStarted() {
        return !this.stopped;
    }

    @Override
    public void stop() {
        MessageObserver mo;
        if (this.stopped) {
            return;
        }
        LOG.fine("Server is stopping.");
        for (Closeable c : this.endpoint.getCleanupHooks()) {
            try {
                c.close();
            }
            catch (IOException iOException) {}
        }
        if (this.slcMgr != null) {
            this.slcMgr.stopServer(this);
        }
        if ((mo = this.getDestination().getMessageObserver()) instanceof MultipleEndpointObserver) {
            ((MultipleEndpointObserver)mo).getEndpoints().remove(this.endpoint);
            if (((MultipleEndpointObserver)mo).getEndpoints().isEmpty()) {
                this.getDestination().setMessageObserver(null);
            }
        } else {
            this.getDestination().setMessageObserver(null);
        }
        this.stopped = true;
    }

    @Override
    public void destroy() {
        this.stop();
        this.getDestination().shutdown();
        if (null != this.serverRegistry) {
            LOG.fine("unregister the server to serverRegistry ");
            this.serverRegistry.unregister(this);
        }
        if (this.iMgr != null) {
            try {
                this.iMgr.unregister(this.mep);
            }
            catch (JMException jmex) {
                LOG.log(Level.WARNING, "Unregistering ManagedEndpoint failed.", jmex);
            }
            this.iMgr = null;
        }
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }
}

