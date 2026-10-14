/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.BundleReference
 */
package org.glassfish.hk2.osgiresourcelocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.glassfish.hk2.osgiresourcelocator.ServiceLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.BundleReference;

public final class ServiceLoaderImpl
extends ServiceLoader {
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private BundleListener bundleTracker;
    private BundleContext bundleContext;
    private ProvidersList providersList = new ProvidersList();

    public ServiceLoaderImpl() {
        ClassLoader cl = this.getClass().getClassLoader();
        if (cl instanceof BundleReference) {
            this.bundleContext = this.getBundleContextSecured(((BundleReference)BundleReference.class.cast(cl)).getBundle());
        }
        if (this.bundleContext == null) {
            throw new RuntimeException("There is no bundle context available yet. Instatiate this class in STARTING or ACTIVE state only");
        }
    }

    private BundleContext getBundleContextSecured(final Bundle bundle2) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(new PrivilegedAction<BundleContext>(){

                @Override
                public BundleContext run() {
                    return bundle2.getBundleContext();
                }
            });
        }
        return bundle2.getBundleContext();
    }

    public void trackBundles() {
        assert (this.bundleTracker == null);
        this.bundleTracker = new BundleTracker();
        this.bundleContext.addBundleListener(this.bundleTracker);
        for (Bundle bundle2 : this.bundleContext.getBundles()) {
            this.addProviders(bundle2);
        }
    }

    @Override
    <T> Iterable<? extends T> lookupProviderInstances1(Class<T> serviceClass, ServiceLoader.ProviderFactory<T> factory2) {
        if (factory2 == null) {
            factory2 = new DefaultFactory();
        }
        ArrayList<T> providers = new ArrayList<T>();
        for (Class c : this.lookupProviderClasses1(serviceClass)) {
            try {
                T providerInstance = factory2.make(c, serviceClass);
                if (providerInstance != null) {
                    providers.add(providerInstance);
                    continue;
                }
                this.debug(factory2 + " returned null provider instance!!!");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return providers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    <T> Iterable<Class> lookupProviderClasses1(Class<T> serviceClass) {
        ArrayList<Class> providerClasses = new ArrayList<Class>();
        this.rwLock.readLock().lock();
        try {
            String serviceName = serviceClass.getName();
            for (ProvidersPerBundle providersPerBundle : this.providersList.getAllProviders()) {
                List<String> providerNames;
                Bundle bundle2 = this.bundleContext.getBundle(providersPerBundle.getBundleId());
                if (bundle2 == null || (providerNames = providersPerBundle.getServiceToProvidersMap().get(serviceName)) == null) continue;
                for (String providerName : providerNames) {
                    try {
                        Class providerClass = this.loadClassSecured(bundle2, providerName);
                        if (!this.isCompatible(providerClass, serviceClass)) continue;
                        providerClasses.add(providerClass);
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            ArrayList<Class> arrayList = providerClasses;
            return arrayList;
        }
        finally {
            this.rwLock.readLock().unlock();
        }
    }

    private Class loadClassSecured(final Bundle bundle2, final String name) throws ClassNotFoundException {
        if (System.getSecurityManager() != null) {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<Class>(){

                    @Override
                    public Class run() throws ClassNotFoundException {
                        return bundle2.loadClass(name);
                    }
                });
            }
            catch (PrivilegedActionException e) {
                throw (ClassNotFoundException)ClassNotFoundException.class.cast(e.getException());
            }
        }
        return bundle2.loadClass(name);
    }

    private boolean isCompatible(Class providerClass, Class serviceClass) {
        try {
            boolean isCompatible;
            Class<?> serviceClassSeenByProviderClass = Class.forName(serviceClass.getName(), false, providerClass.getClassLoader());
            boolean bl = isCompatible = serviceClassSeenByProviderClass == serviceClass;
            if (!isCompatible) {
                this.debug(providerClass + " loaded by " + providerClass.getClassLoader() + " sees " + serviceClass + " from " + serviceClassSeenByProviderClass.getClassLoader() + ", where as caller uses " + serviceClass + " loaded by " + serviceClass.getClassLoader());
            }
            return isCompatible;
        }
        catch (ClassNotFoundException e) {
            this.debug("Unable to reach " + serviceClass + " from " + providerClass + ", which is loaded by " + providerClass.getClassLoader(), e);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<String> load(InputStream is) throws IOException {
        ArrayList<String> providerNames = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(is);
            String commentPattern = "#";
            while (scanner.hasNextLine()) {
                StringTokenizer st;
                String line = scanner.nextLine();
                if (line.startsWith("#") || !(st = new StringTokenizer(line)).hasMoreTokens()) continue;
                providerNames.add(st.nextToken());
            }
        }
        finally {
            is.close();
        }
        return providerNames;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addProviders(Bundle bundle2) {
        this.rwLock.writeLock().lock();
        try {
            String SERVICE_LOCATION = "META-INF/services";
            if (bundle2.getEntry("META-INF/services") == null) {
                return;
            }
            Enumeration entries = bundle2.getEntryPaths("META-INF/services");
            if (entries != null) {
                ProvidersPerBundle providers = new ProvidersPerBundle(bundle2.getBundleId());
                while (entries.hasMoreElements()) {
                    String entry = (String)entries.nextElement();
                    String serviceName = entry.substring("META-INF/services".length() + 1);
                    URL url2 = bundle2.getEntry(entry);
                    try {
                        InputStream is = url2.openStream();
                        List<String> providerNames = this.load(is);
                        this.debug("Bundle = " + bundle2 + ", serviceName = " + serviceName + ", providerNames = " + providerNames);
                        providers.put(serviceName, providerNames);
                    }
                    catch (IOException iOException) {}
                }
                this.providersList.addProviders(providers);
            }
        }
        finally {
            this.rwLock.writeLock().unlock();
        }
    }

    private synchronized void removeProviders(Bundle bundle2) {
        this.rwLock.writeLock().lock();
        try {
            this.providersList.removeProviders(bundle2.getBundleId());
        }
        finally {
            this.rwLock.writeLock().unlock();
        }
    }

    private void debug(String s2) {
        if (Boolean.valueOf(this.bundleContext.getProperty("org.glassfish.hk2.osgiresourcelocator.debug")).booleanValue()) {
            System.out.println("org.glassfish.hk2.osgiresourcelocator:DEBUG: " + s2);
        }
    }

    private void debug(String s2, Throwable t2) {
        if (Boolean.valueOf(this.bundleContext.getProperty("org.glassfish.hk2.osgiresourcelocator.debug")).booleanValue()) {
            System.out.println("org.glassfish.hk2.osgiresourcelocator:DEBUG: " + s2);
            t2.printStackTrace(System.out);
        }
    }

    private static class DefaultFactory<T>
    implements ServiceLoader.ProviderFactory<T> {
        private DefaultFactory() {
        }

        @Override
        public T make(Class providerClass, Class<T> serviceClass) throws Exception {
            if (serviceClass.isAssignableFrom(providerClass)) {
                return providerClass.newInstance();
            }
            return null;
        }
    }

    private static class ProvidersList {
        private List<ProvidersPerBundle> allProviders = new LinkedList<ProvidersPerBundle>();

        private ProvidersList() {
        }

        void addProviders(ProvidersPerBundle providers) {
            long bundleId = providers.getBundleId();
            int idx = 0;
            for (ProvidersPerBundle providersPerBundle : this.getAllProviders()) {
                if (providersPerBundle.getBundleId() <= bundleId) continue;
                this.getAllProviders().add(idx, providers);
                return;
            }
            this.getAllProviders().add(providers);
        }

        void removeProviders(long bundleId) {
            Iterator<ProvidersPerBundle> iterator2 = this.getAllProviders().iterator();
            while (iterator2.hasNext()) {
                ProvidersPerBundle providersPerBundle = iterator2.next();
                if (providersPerBundle.getBundleId() != bundleId) continue;
                iterator2.remove();
                return;
            }
        }

        public List<ProvidersPerBundle> getAllProviders() {
            return this.allProviders;
        }
    }

    private static class ProvidersPerBundle {
        private long bundleId;
        Map<String, List<String>> serviceToProvidersMap = new HashMap<String, List<String>>();

        private ProvidersPerBundle(long bundleId) {
            this.bundleId = bundleId;
        }

        public long getBundleId() {
            return this.bundleId;
        }

        public void put(String serviceName, List<String> providerNames) {
            this.serviceToProvidersMap.put(serviceName, providerNames);
        }

        public Map<String, List<String>> getServiceToProvidersMap() {
            return this.serviceToProvidersMap;
        }
    }

    private class BundleTracker
    implements BundleListener {
        private BundleTracker() {
        }

        public void bundleChanged(BundleEvent event) {
            Bundle bundle2 = event.getBundle();
            switch (event.getType()) {
                case 1: {
                    ServiceLoaderImpl.this.addProviders(bundle2);
                    break;
                }
                case 16: {
                    ServiceLoaderImpl.this.removeProviders(bundle2);
                    break;
                }
                case 8: {
                    ServiceLoaderImpl.this.removeProviders(bundle2);
                    ServiceLoaderImpl.this.addProviders(bundle2);
                }
            }
        }
    }
}

