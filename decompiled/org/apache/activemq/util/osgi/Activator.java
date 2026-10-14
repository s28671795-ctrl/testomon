/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.SynchronousBundleListener
 *  org.osgi.framework.wiring.BundleCapability
 *  org.osgi.framework.wiring.BundleWire
 *  org.osgi.framework.wiring.BundleWiring
 */
package org.apache.activemq.util.osgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.Service;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.util.FactoryFinder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator
implements BundleActivator,
SynchronousBundleListener,
FactoryFinder.ObjectFactory {
    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);
    private final ConcurrentMap<String, Class<?>> serviceCache = new ConcurrentHashMap();
    private final ConcurrentMap<Long, BundleWrapper> bundleWrappers = new ConcurrentHashMap<Long, BundleWrapper>();
    private BundleContext bundleContext;
    private Set<BundleCapability> packageCapabilities = new HashSet<BundleCapability>();

    public synchronized void start(BundleContext bundleContext) throws Exception {
        FactoryFinder.setObjectFactory(this);
        this.debug("activating");
        this.bundleContext = bundleContext;
        this.cachePackageCapabilities(Service.class, Transport.class, DiscoveryAgent.class, PersistenceAdapter.class);
        this.debug("checking existing bundles");
        bundleContext.addBundleListener((BundleListener)this);
        for (Bundle bundle2 : bundleContext.getBundles()) {
            if (bundle2.getState() != 4 && bundle2.getState() != 8 && bundle2.getState() != 32 && bundle2.getState() != 16) continue;
            this.register(bundle2);
        }
        this.debug("activated");
    }

    private void cachePackageCapabilities(Class<?> ... classes) {
        BundleWiring ourWiring = (BundleWiring)this.bundleContext.getBundle().adapt(BundleWiring.class);
        HashSet<String> packageNames = new HashSet<String>();
        for (Class<?> clazz : classes) {
            packageNames.add(clazz.getPackage().getName());
        }
        List ourExports = ourWiring.getCapabilities("osgi.wiring.package");
        for (BundleCapability ourExport : ourExports) {
            String ourPkgName = (String)ourExport.getAttributes().get("osgi.wiring.package");
            if (!packageNames.contains(ourPkgName)) continue;
            this.packageCapabilities.add(ourExport);
        }
    }

    public synchronized void stop(BundleContext bundleContext) throws Exception {
        this.debug("deactivating");
        bundleContext.removeBundleListener((BundleListener)this);
        while (!this.bundleWrappers.isEmpty()) {
            this.unregister((Long)this.bundleWrappers.keySet().iterator().next());
        }
        this.debug("deactivated");
        this.bundleContext = null;
    }

    public void bundleChanged(BundleEvent event) {
        if (event.getType() == 32) {
            this.register(event.getBundle());
        } else if (event.getType() == 64 || event.getType() == 16) {
            this.unregister(event.getBundle().getBundleId());
        }
    }

    protected void register(Bundle bundle2) {
        this.debug("checking bundle " + bundle2.getBundleId());
        if (this.isOurBundle(bundle2) || this.isImportingUs(bundle2)) {
            this.debug("Registering bundle for extension resolution: " + bundle2.getBundleId());
            this.bundleWrappers.put(bundle2.getBundleId(), new BundleWrapper(bundle2));
        }
    }

    private boolean isOurBundle(Bundle bundle2) {
        return bundle2.getBundleId() == this.bundleContext.getBundle().getBundleId();
    }

    protected void unregister(long bundleId) {
        BundleWrapper bundle2 = (BundleWrapper)this.bundleWrappers.remove(bundleId);
        if (bundle2 != null) {
            for (String path : bundle2.cachedServices) {
                this.debug("unregistering service for key: " + path);
                this.serviceCache.remove(path);
            }
        }
    }

    @Override
    public Object create(String path) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        Class clazz = (Class)this.serviceCache.get(path);
        if (clazz == null) {
            StringBuffer warnings = new StringBuffer();
            int wrrningCounter = 1;
            for (BundleWrapper wrapper : this.bundleWrappers.values()) {
                URL resource = wrapper.bundle.getResource(path);
                if (resource == null) continue;
                Properties properties = this.loadProperties(resource);
                String className = properties.getProperty("class");
                if (className == null) {
                    warnings.append("(" + wrrningCounter++ + ") Invalid service file in bundle " + wrapper + ": 'class' property not defined.");
                    continue;
                }
                try {
                    clazz = wrapper.bundle.loadClass(className);
                }
                catch (ClassNotFoundException e) {
                    warnings.append("(" + wrrningCounter++ + ") Bundle " + wrapper + " could not load " + className + ": " + e);
                    continue;
                }
                this.serviceCache.put(path, clazz);
                wrapper.cachedServices.add(path);
                break;
            }
            if (clazz == null) {
                String msg = "Service not found: '" + path + "'";
                if (warnings.length() != 0) {
                    msg = msg + ", " + warnings;
                }
                throw new IOException(msg);
            }
        }
        return clazz.newInstance();
    }

    private void debug(Object msg) {
        LOG.debug(msg.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Properties loadProperties(URL resource) throws IOException {
        InputStream in = resource.openStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Properties properties = new Properties();
            properties.load(in);
            Properties properties2 = properties;
            return properties2;
        }
        finally {
            try {
                in.close();
            }
            catch (Exception exception) {}
        }
    }

    private boolean isImportingUs(Bundle bundle2) {
        BundleWiring wiring = (BundleWiring)bundle2.adapt(BundleWiring.class);
        List imports = wiring.getRequiredWires("osgi.wiring.package");
        for (BundleWire importWire : imports) {
            if (!this.packageCapabilities.contains(importWire.getCapability())) continue;
            return true;
        }
        return false;
    }

    private static class BundleWrapper {
        private final Bundle bundle;
        private final List<String> cachedServices = new ArrayList<String>();

        public BundleWrapper(Bundle bundle2) {
            this.bundle = bundle2;
        }
    }
}

