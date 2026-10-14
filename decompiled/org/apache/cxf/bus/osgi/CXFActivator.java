/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.service.cm.ConfigurationAdmin
 *  org.osgi.service.cm.ManagedServiceFactory
 *  org.osgi.util.tracker.ServiceTracker
 */
package org.apache.cxf.bus.osgi;

import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.bus.blueprint.NamespaceHandlerRegisterer;
import org.apache.cxf.bus.extension.Extension;
import org.apache.cxf.bus.extension.ExtensionRegistry;
import org.apache.cxf.bus.osgi.CXFExtensionBundleListener;
import org.apache.cxf.bus.osgi.ManagedWorkQueueList;
import org.apache.cxf.bus.osgi.OSGIBusListener;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.internal.CXFAPINamespaceHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.util.tracker.ServiceTracker;

public class CXFActivator
implements BundleActivator {
    private List<Extension> extensions;
    private ManagedWorkQueueList workQueues;
    private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker;
    private CXFExtensionBundleListener cxfBundleListener;
    private ServiceRegistration<ManagedServiceFactory> workQueueServiceRegistration;

    public void start(BundleContext context) throws Exception {
        this.workQueues = new ManagedWorkQueueList();
        this.cxfBundleListener = new CXFExtensionBundleListener(context.getBundle().getBundleId());
        context.addBundleListener((BundleListener)this.cxfBundleListener);
        this.cxfBundleListener.registerExistingBundles(context);
        this.configAdminTracker = new ServiceTracker(context, ConfigurationAdmin.class, null);
        this.configAdminTracker.open();
        this.workQueues.setConfigAdminTracker(this.configAdminTracker);
        this.workQueueServiceRegistration = this.registerManagedServiceFactory(context, ManagedServiceFactory.class, this.workQueues, "org.apache.cxf.workqueues");
        this.extensions = new ArrayList<Extension>();
        this.extensions.add(this.createOsgiBusListenerExtension(context));
        this.extensions.add(CXFActivator.createManagedWorkQueueListExtension(this.workQueues));
        ExtensionRegistry.addExtensions(this.extensions);
        BlueprintNameSpaceHandlerFactory factory2 = new BlueprintNameSpaceHandlerFactory(){

            @Override
            public Object createNamespaceHandler() {
                return new CXFAPINamespaceHandler();
            }
        };
        NamespaceHandlerRegisterer.register(context, factory2, "http://cxf.apache.org/blueprint/core", "http://cxf.apache.org/configuration/beans", "http://cxf.apache.org/configuration/parameterized-types", "http://cxf.apache.org/configuration/security", "http://schemas.xmlsoap.org/wsdl/", "http://www.w3.org/2005/08/addressing", "http://schemas.xmlsoap.org/ws/2004/08/addressing");
    }

    private <T> ServiceRegistration<T> registerManagedServiceFactory(BundleContext context, Class<T> serviceClass, T service2, String servicePid) {
        return context.registerService(serviceClass, service2, CollectionUtils.singletonDictionary("service.pid", servicePid));
    }

    private Extension createOsgiBusListenerExtension(BundleContext context) {
        Extension busListener = new Extension(OSGIBusListener.class);
        busListener.setArgs(new Object[]{context});
        return busListener;
    }

    private static Extension createManagedWorkQueueListExtension(final ManagedWorkQueueList workQueues) {
        return new Extension(ManagedWorkQueueList.class){

            @Override
            public Object getLoadedObject() {
                return workQueues;
            }

            @Override
            public Extension cloneNoObject() {
                return this;
            }
        };
    }

    public void stop(BundleContext context) throws Exception {
        context.removeBundleListener((BundleListener)this.cxfBundleListener);
        this.cxfBundleListener.shutdown();
        this.workQueues.shutDown();
        this.workQueueServiceRegistration.unregister();
        this.configAdminTracker.close();
        ExtensionRegistry.removeExtensions(this.extensions);
    }
}

