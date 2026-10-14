/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.service.cm.ManagedServiceFactory
 *  org.osgi.service.http.HttpService
 *  org.osgi.util.tracker.ServiceTracker
 *  org.osgi.util.tracker.ServiceTrackerCustomizer
 */
package org.apache.cxf.transport.http.osgi;

import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.bus.blueprint.NamespaceHandlerRegisterer;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.DestinationRegistryImpl;
import org.apache.cxf.transport.http.HTTPConduitConfigurer;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transport.http.blueprint.HttpBPHandler;
import org.apache.cxf.transport.http.osgi.ConfigAdminHttpConduitConfigurer;
import org.apache.cxf.transport.http.osgi.HttpServiceTrackerCust;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class HTTPTransportActivator
implements BundleActivator {
    private static final String DISABLE_DEFAULT_HTTP_TRANSPORT = "org.apache.cxf.osgi.http.transport.disable";
    private ServiceTracker<HttpService, ?> httpServiceTracker;

    public void start(BundleContext context) throws Exception {
        ConfigAdminHttpConduitConfigurer conduitConfigurer = new ConfigAdminHttpConduitConfigurer();
        this.registerService(context, ManagedServiceFactory.class, conduitConfigurer, "org.apache.cxf.http.conduits");
        this.registerService(context, HTTPConduitConfigurer.class, conduitConfigurer, "org.apache.cxf.http.conduit-configurer");
        if (PropertyUtils.isTrue(context.getProperty(DISABLE_DEFAULT_HTTP_TRANSPORT))) {
            return;
        }
        DestinationRegistryImpl destinationRegistry = new DestinationRegistryImpl();
        HTTPTransportFactory transportFactory = new HTTPTransportFactory(destinationRegistry);
        HttpServiceTrackerCust customizer = new HttpServiceTrackerCust(destinationRegistry, context);
        this.httpServiceTracker = new ServiceTracker(context, HttpService.class, (ServiceTrackerCustomizer)customizer);
        this.httpServiceTracker.open();
        context.registerService(DestinationRegistry.class.getName(), (Object)destinationRegistry, null);
        context.registerService(HTTPTransportFactory.class.getName(), (Object)transportFactory, null);
        BlueprintNameSpaceHandlerFactory factory2 = new BlueprintNameSpaceHandlerFactory(){

            @Override
            public Object createNamespaceHandler() {
                return new HttpBPHandler();
            }
        };
        NamespaceHandlerRegisterer.register(context, factory2, "http://cxf.apache.org/transports/http/configuration");
    }

    private <T> ServiceRegistration<T> registerService(BundleContext context, Class<T> serviceInterface, T serviceObject, String servicePid) {
        return context.registerService(serviceInterface, serviceObject, CollectionUtils.singletonDictionary("service.pid", servicePid));
    }

    public void stop(BundleContext context) throws Exception {
        if (this.httpServiceTracker != null) {
            this.httpServiceTracker.close();
        }
    }
}

