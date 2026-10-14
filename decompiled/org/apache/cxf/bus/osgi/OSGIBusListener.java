/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.InvalidSyntaxException
 *  org.osgi.framework.ServiceReference
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.framework.Version
 */
package org.apache.cxf.bus.osgi;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.ExtensionManagerImpl;
import org.apache.cxf.bus.osgi.ManagedWorkQueueList;
import org.apache.cxf.bus.osgi.OSGiBeanLocator;
import org.apache.cxf.buslifecycle.BusCreationListener;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.endpoint.ClientLifeCycleManager;
import org.apache.cxf.endpoint.ServerLifeCycleListener;
import org.apache.cxf.endpoint.ServerLifeCycleManager;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.workqueue.WorkQueueManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;

public class OSGIBusListener
implements BusLifeCycleListener {
    public static final String CONTEXT_SYMBOLIC_NAME_PROPERTY = "cxf.context.symbolicname";
    public static final String CONTEXT_VERSION_PROPERTY = "cxf.context.version";
    public static final String CONTEXT_NAME_PROPERTY = "cxf.bus.id";
    private static final String SERVICE_PROPERTY_PRIVATE = "org.apache.cxf.bus.private.extension";
    private static final String SERVICE_PROPERTY_RESTRICTED = "org.apache.cxf.bus.restricted.extension";
    private static final String BUS_EXTENSION_BUNDLES_EXCLUDES = "bus.extension.bundles.excludes";
    Bus bus;
    ServiceRegistration<?> service;
    BundleContext defaultContext;
    private Pattern extensionBundlesExcludesPattern;

    public OSGIBusListener(Bus b) {
        this(b, null);
    }

    public OSGIBusListener(Bus b, Object[] args2) {
        String extExcludes;
        this.bus = b;
        if (args2 != null && args2.length > 0 && args2[0] instanceof BundleContext) {
            this.defaultContext = (BundleContext)args2[0];
        }
        if (!StringUtils.isEmpty(extExcludes = (String)this.bus.getProperty(BUS_EXTENSION_BUNDLES_EXCLUDES))) {
            try {
                this.extensionBundlesExcludesPattern = Pattern.compile(extExcludes);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        BusLifeCycleManager manager = this.bus.getExtension(BusLifeCycleManager.class);
        manager.registerLifeCycleListener(this);
        this.registerConfiguredBeanLocator();
        this.registerClientLifeCycleListeners();
        this.registerServerLifecycleListeners();
        this.registerBusFeatures();
        this.sendBusCreatedToBusCreationListeners();
    }

    private void registerConfiguredBeanLocator() {
        ConfiguredBeanLocator cbl = this.bus.getExtension(ConfiguredBeanLocator.class);
        if (cbl instanceof ExtensionManagerImpl) {
            this.bus.setExtension(new OSGiBeanLocator(cbl, this.defaultContext), ConfiguredBeanLocator.class);
        }
    }

    @Override
    public void initComplete() {
        ManagedWorkQueueList wqList = this.bus.getExtension(ManagedWorkQueueList.class);
        if (wqList != null) {
            WorkQueueManager manager = this.bus.getExtension(WorkQueueManager.class);
            wqList.addAllToWorkQueueManager(manager);
        }
        this.registerBusAsService();
    }

    @Override
    public void preShutdown() {
    }

    @Override
    public void postShutdown() {
        if (this.service != null) {
            this.service.unregister();
            this.service = null;
        }
    }

    private static ServiceReference<?>[] getServiceReferences(BundleContext context, Class<?> serviceClass) {
        ServiceReference[] refs = null;
        try {
            refs = context.getServiceReferences(serviceClass.getName(), null);
        }
        catch (InvalidSyntaxException invalidSyntaxException) {
            // empty catch block
        }
        if (refs == null) {
            refs = new ServiceReference[]{};
        }
        return refs;
    }

    private void sendBusCreatedToBusCreationListeners() {
        ServiceReference<?>[] refs;
        for (ServiceReference<?> ref : refs = OSGIBusListener.getServiceReferences(this.defaultContext, BusCreationListener.class)) {
            if (this.isPrivate(ref) || this.isExcluded(ref)) continue;
            BusCreationListener listener = (BusCreationListener)this.defaultContext.getService(ref);
            listener.busCreated(this.bus);
        }
    }

    private void registerServerLifecycleListeners() {
        ServiceReference<?>[] refs = OSGIBusListener.getServiceReferences(this.defaultContext, ServerLifeCycleListener.class);
        ServerLifeCycleManager clcm = this.bus.getExtension(ServerLifeCycleManager.class);
        for (ServiceReference<?> ref : refs) {
            if (this.isPrivate(ref) || this.isExcluded(ref)) continue;
            ServerLifeCycleListener listener = (ServerLifeCycleListener)this.defaultContext.getService(ref);
            clcm.registerListener(listener);
        }
    }

    private void registerClientLifeCycleListeners() {
        ServiceReference<?>[] refs = OSGIBusListener.getServiceReferences(this.defaultContext, ClientLifeCycleListener.class);
        ClientLifeCycleManager clcm = this.bus.getExtension(ClientLifeCycleManager.class);
        for (ServiceReference<?> ref : refs) {
            if (this.isPrivate(ref) || this.isExcluded(ref)) continue;
            ClientLifeCycleListener listener = (ClientLifeCycleListener)this.defaultContext.getService(ref);
            clcm.registerListener(listener);
        }
    }

    private void registerBusFeatures() {
        ServiceReference<?>[] refs;
        for (ServiceReference<?> ref : refs = OSGIBusListener.getServiceReferences(this.defaultContext, Feature.class)) {
            if (this.isPrivate(ref) || this.isExcluded(ref)) continue;
            Feature feature = (Feature)this.defaultContext.getService(ref);
            this.bus.getFeatures().add(feature);
        }
    }

    private boolean isPrivate(ServiceReference<?> ref) {
        Object o = ref.getProperty(SERVICE_PROPERTY_PRIVATE);
        Boolean pvt = Boolean.FALSE;
        if (o == null) {
            pvt = Boolean.FALSE;
        } else if (o instanceof String) {
            pvt = Boolean.parseBoolean((String)o);
        } else if (o instanceof Boolean) {
            pvt = (Boolean)o;
        }
        return pvt;
    }

    private boolean isExcluded(ServiceReference<?> ref) {
        String o = (String)ref.getProperty(SERVICE_PROPERTY_RESTRICTED);
        if (!StringUtils.isEmpty(o)) {
            BundleContext app = this.bus.getExtension(BundleContext.class);
            try {
                if (app != null && !app.getBundle().getSymbolicName().matches(o)) {
                    return true;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return this.extensionBundlesExcludesPattern != null && this.extensionBundlesExcludesPattern.matcher(ref.getBundle().getSymbolicName()).matches();
    }

    private Version getBundleVersion(Bundle bundle2) {
        Dictionary headers = bundle2.getHeaders();
        String version = (String)headers.get("Bundle-Version");
        return version != null ? Version.parseVersion((String)version) : Version.emptyVersion;
    }

    private void registerBusAsService() {
        BundleContext context = this.bus.getExtension(BundleContext.class);
        if (context != null) {
            HashMap<String, String> props = new HashMap<String, String>();
            props.put(CONTEXT_SYMBOLIC_NAME_PROPERTY, context.getBundle().getSymbolicName());
            props.put(CONTEXT_VERSION_PROPERTY, (String)this.getBundleVersion(context.getBundle()));
            props.put(CONTEXT_NAME_PROPERTY, this.bus.getId());
            this.service = context.registerService(Bus.class.getName(), (Object)this.bus, CollectionUtils.toDictionary(props));
        }
    }
}

