/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.AbstractTransportFactory;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.DestinationRegistryImpl;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HTTPConduitConfigurer;
import org.apache.cxf.transport.http.HTTPConduitFactory;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.http.URLConnectionHTTPConduit;
import org.apache.cxf.transport.servlet.ServletDestinationFactory;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.wsdl.http.AddressType;

@NoJSR250Annotations
public class HTTPTransportFactory
extends AbstractTransportFactory
implements ConduitInitiator,
DestinationFactory {
    public static final List<String> DEFAULT_NAMESPACES = Collections.unmodifiableList(Arrays.asList("http://cxf.apache.org/transports/http", "http://cxf.apache.org/transports/http/configuration", "http://schemas.xmlsoap.org/wsdl/http", "http://schemas.xmlsoap.org/wsdl/http/"));
    private static final Logger LOG = LogUtils.getL7dLogger(HTTPTransportFactory.class);
    private static final Set<String> URI_PREFIXES = new HashSet<String>();
    protected DestinationRegistry registry;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = this.lock.readLock();
    private final Lock w = this.lock.writeLock();

    public HTTPTransportFactory() {
        this(new DestinationRegistryImpl());
    }

    public HTTPTransportFactory(DestinationRegistry registry) {
        this(DEFAULT_NAMESPACES, registry);
    }

    protected HTTPTransportFactory(List<String> transportIds, DestinationRegistry registry) {
        super(transportIds);
        if (registry == null) {
            registry = new DestinationRegistryImpl();
        }
        this.registry = registry;
    }

    public DestinationRegistry getRegistry() {
        return this.registry;
    }

    public void setRegistry(DestinationRegistry newRegistry) {
        block4: {
            this.w.lock();
            try {
                if (this.registry.getDestinations().isEmpty()) {
                    this.registry = newRegistry;
                    break block4;
                }
                String m4 = new Message("CANNOT_CHANGE_REGISTRY_ALREADY_IN_USE", LOG, new Object[0]).toString();
                LOG.log(Level.SEVERE, m4);
                throw new RuntimeException(m4);
            }
            finally {
                this.w.unlock();
            }
        }
    }

    public void setActivationNamespaces(Collection<String> ans) {
        this.setTransportIds(new ArrayList<String>(ans));
    }

    public EndpointInfo createEndpointInfo(ServiceInfo serviceInfo, BindingInfo b, List<?> ees) {
        if (ees != null) {
            for (Object extensor : ees) {
                if (!(extensor instanceof AddressType)) continue;
                AddressType httpAdd = (AddressType)extensor;
                HttpEndpointInfo info = new HttpEndpointInfo(serviceInfo, "http://schemas.xmlsoap.org/wsdl/http/");
                ((EndpointInfo)info).setAddress(httpAdd.getLocation());
                ((AbstractPropertiesHolder)info).addExtensor(httpAdd);
                return info;
            }
        }
        HttpEndpointInfo hei = new HttpEndpointInfo(serviceInfo, "http://schemas.xmlsoap.org/wsdl/http/");
        AddressType at = new AddressType();
        hei.addExtensor(at);
        return hei;
    }

    public void createPortExtensors(EndpointInfo ei, Service service2) {
    }

    @Override
    public Set<String> getUriPrefixes() {
        return URI_PREFIXES;
    }

    protected void configure(Bus b, Object bean) {
        this.configure(b, bean, null, null);
    }

    protected void configure(Bus bus, Object bean, String name, String extraName) {
        Configurer configurer = bus.getExtension(Configurer.class);
        if (null != configurer) {
            configurer.configureBean(name, bean);
            if (extraName != null) {
                configurer.configureBean(extraName, bean);
            }
        }
    }

    @Override
    public Conduit getConduit(EndpointInfo endpointInfo, Bus bus) throws IOException {
        return this.getConduit(endpointInfo, endpointInfo.getTarget(), bus);
    }

    @Override
    public Conduit getConduit(EndpointInfo endpointInfo, EndpointReferenceType target, Bus bus) throws IOException {
        HTTPConduitConfigurer c1;
        String address;
        HTTPConduitFactory factory2 = this.findFactory(endpointInfo, bus);
        HTTPConduit conduit = null;
        if (factory2 != null) {
            conduit = factory2.createConduit(this, bus, endpointInfo, target);
        }
        if (conduit == null) {
            conduit = new URLConnectionHTTPConduit(bus, endpointInfo, target);
        }
        if ((address = conduit.getAddress()) != null && address.indexOf(63) != -1) {
            address = address.substring(0, address.indexOf(63));
        }
        if ((c1 = bus.getExtension(HTTPConduitConfigurer.class)) != null) {
            c1.configure(conduit.getBeanName(), address, conduit);
        }
        this.configure(bus, conduit, conduit.getBeanName(), address);
        conduit.finalizeConfig();
        return conduit;
    }

    protected HTTPConduitFactory findFactory(EndpointInfo endpointInfo, Bus bus) {
        HTTPConduitFactory f = endpointInfo.getProperty(HTTPConduitFactory.class.getName(), HTTPConduitFactory.class);
        if (f == null) {
            f = bus.getExtension(HTTPConduitFactory.class);
        }
        return f;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Destination getDestination(EndpointInfo endpointInfo, Bus bus) throws IOException {
        if (endpointInfo == null) {
            throw new IllegalArgumentException("EndpointInfo cannot be null");
        }
        this.r.lock();
        try {
            DestinationRegistry destinationRegistry = this.registry;
            synchronized (destinationRegistry) {
                AbstractHTTPDestination d = this.registry.getDestinationForPath(endpointInfo.getAddress());
                if (d == null) {
                    HttpDestinationFactory jettyFactory = bus.getExtension(HttpDestinationFactory.class);
                    String addr = endpointInfo.getAddress();
                    if (jettyFactory == null && addr != null && addr.startsWith("http")) {
                        String m4 = new Message("NO_HTTP_DESTINATION_FACTORY_FOUND", LOG, new Object[0]).toString();
                        LOG.log(Level.SEVERE, m4);
                        throw new IOException(m4);
                    }
                    HttpDestinationFactory factory2 = null;
                    factory2 = jettyFactory != null && (addr == null || addr.startsWith("http")) ? jettyFactory : new ServletDestinationFactory();
                    d = factory2.createDestination(endpointInfo, bus, this.registry);
                    this.registry.addDestination(d);
                    this.configure(bus, d);
                    d.finalizeConfig();
                }
                AbstractHTTPDestination abstractHTTPDestination = d;
                return abstractHTTPDestination;
            }
        }
        finally {
            this.r.unlock();
        }
    }

    static {
        URI_PREFIXES.add("http://");
        URI_PREFIXES.add("https://");
    }

    private static class HttpEndpointInfo
    extends EndpointInfo {
        AddressType saddress;

        HttpEndpointInfo(ServiceInfo serv, String trans) {
            super(serv, trans);
        }

        @Override
        public void setAddress(String s2) {
            super.setAddress(s2);
            if (this.saddress != null) {
                this.saddress.setLocation(s2);
            }
        }

        @Override
        public void addExtensor(Object el) {
            super.addExtensor(el);
            if (el instanceof AddressType) {
                this.saddress = (AddressType)el;
            }
        }
    }
}

