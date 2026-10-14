/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.geronimo.osgi.registry.api.ProviderRegistry
 *  org.osgi.framework.BundleContext
 *  org.osgi.util.tracker.ServiceTracker
 */
package org.apache.geronimo.osgi.locator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.geronimo.osgi.registry.api.ProviderRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ProviderLocator {
    private static BundleContext context;
    private static Object registryTracker;

    private ProviderLocator() {
    }

    public static void init(BundleContext c) {
        try {
            registryTracker = new ServiceTracker(c, "org.apache.geronimo.osgi.registry.api.ProviderRegistry", null);
            ((ServiceTracker)registryTracker).open();
            context = c;
        }
        catch (Throwable e) {
            registryTracker = null;
        }
    }

    public static void destroy() {
        if (registryTracker != null) {
            ((ServiceTracker)registryTracker).close();
            registryTracker = null;
        }
    }

    public static Class<?> locate(String providerId) {
        Object registry = ProviderLocator.getRegistry();
        if (registry == null) {
            return null;
        }
        return ((ProviderRegistry)registry).locate(providerId);
    }

    public static List<Class<?>> locateAll(String providerId) {
        Object registry = ProviderLocator.getRegistry();
        if (registry == null) {
            return new ArrayList();
        }
        return ((ProviderRegistry)registry).locateAll(providerId);
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return ProviderLocator.loadClass(className, null, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> loadClass(String className, Class<?> contextClass) throws ClassNotFoundException {
        return ProviderLocator.loadClass(className, contextClass, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> loadClass(String className, Class<?> contextClass, ClassLoader loader) throws ClassNotFoundException {
        Class<?> cls = ProviderLocator.locate(className);
        if (cls != null) {
            return cls;
        }
        if (loader != null) {
            try {
                return loader.loadClass(className);
            }
            catch (ClassNotFoundException x) {
                // empty catch block
            }
        }
        if (contextClass != null) {
            loader = contextClass.getClassLoader();
        }
        return Class.forName(className, true, loader);
    }

    public static Object getService(String iface, Class<?> contextClass, ClassLoader loader) throws Exception {
        Object service2;
        Object registry = ProviderLocator.getRegistry();
        if (registry != null && (service2 = ((ProviderRegistry)registry).getService(iface)) != null) {
            return service2;
        }
        Class<?> cls = ProviderLocator.locateServiceClass(iface, contextClass, loader);
        if (cls != null) {
            return cls.newInstance();
        }
        return null;
    }

    public static Class<?> getServiceClass(String iface, Class<?> contextClass, ClassLoader loader) throws ClassNotFoundException {
        Class cls;
        Object registry = ProviderLocator.getRegistry();
        if (registry != null && (cls = ((ProviderRegistry)registry).getServiceClass(iface)) != null) {
            return cls;
        }
        return ProviderLocator.locateServiceClass(iface, contextClass, loader);
    }

    public static List<Object> getServices(String iface, Class<?> contextClass, ClassLoader loader) throws Exception {
        Collection<Class<?>> classes;
        List globalServices;
        ArrayList<Object> services = new ArrayList<Object>();
        Object registry = ProviderLocator.getRegistry();
        if (registry != null && (globalServices = ((ProviderRegistry)registry).getServices(iface)) != null) {
            services.addAll(globalServices);
        }
        if ((classes = ProviderLocator.locateServiceClasses(iface, contextClass, loader)) != null) {
            for (Class<?> cls : classes) {
                services.add(cls.newInstance());
            }
        }
        return services;
    }

    public static List<Class<?>> getServiceClasses(String iface, Class<?> contextClass, ClassLoader loader) throws Exception {
        Collection<Class<?>> classes;
        List globalServices;
        LinkedHashSet serviceClasses = new LinkedHashSet();
        Object registry = ProviderLocator.getRegistry();
        if (registry != null && (globalServices = ((ProviderRegistry)registry).getServiceClasses(iface)) != null) {
            serviceClasses.addAll(globalServices);
        }
        if ((classes = ProviderLocator.locateServiceClasses(iface, contextClass, loader)) != null) {
            serviceClasses.addAll(classes);
        }
        return new ArrayList(serviceClasses);
    }

    private static String locateServiceClassName(String iface, Class<?> contextClass, ClassLoader loader) {
        String name = ProviderLocator.locateServiceClassName(iface, loader);
        if (name != null) {
            return name;
        }
        if (contextClass != null && (name = ProviderLocator.locateServiceClassName(iface, contextClass.getClassLoader())) != null) {
            return name;
        }
        return null;
    }

    private static String locateServiceClassName(String iface, ClassLoader loader) {
        if (loader != null) {
            try {
                String service2 = "META-INF/services/" + iface;
                Enumeration<URL> providers = loader.getResources(service2);
                while (providers.hasMoreElements()) {
                    List<String> providerNames = ProviderLocator.parseServiceDefinition(providers.nextElement());
                    if (providerNames.isEmpty()) continue;
                    return providerNames.get(0);
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    private static Class<?> locateServiceClass(String iface, Class<?> contextClass, ClassLoader loader) throws ClassNotFoundException {
        String className = ProviderLocator.locateServiceClassName(iface, contextClass, loader);
        if (className == null) {
            return null;
        }
        return ProviderLocator.loadClass(className, contextClass, loader);
    }

    private static Collection<String> locateServiceClassNames(String iface, Class<?> contextClass, ClassLoader loader) {
        LinkedHashSet<String> names = new LinkedHashSet<String>();
        ProviderLocator.locateServiceClassNames(iface, loader, names);
        if (contextClass != null) {
            ProviderLocator.locateServiceClassNames(iface, contextClass.getClassLoader(), names);
        }
        return names;
    }

    static void locateServiceClassNames(String iface, ClassLoader loader, Set names) {
        if (loader != null) {
            try {
                String service2 = "META-INF/services/" + iface;
                Enumeration<URL> providers = loader.getResources(service2);
                while (providers.hasMoreElements()) {
                    List<String> providerNames = ProviderLocator.parseServiceDefinition(providers.nextElement());
                    names.addAll(providerNames);
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private static Collection<Class<?>> locateServiceClasses(String iface, Class<?> contextClass, ClassLoader loader) throws ClassNotFoundException {
        Collection<String> classNames = ProviderLocator.locateServiceClassNames(iface, contextClass, loader);
        LinkedHashSet classes = new LinkedHashSet();
        for (String name : classNames) {
            classes.add(ProviderLocator.loadClass(name, contextClass, loader));
        }
        return classes;
    }

    private static List<String> parseServiceDefinition(URL u) {
        String url2 = u.toString();
        ArrayList<String> classes = new ArrayList<String>();
        if (url2.endsWith("/")) {
            return classes;
        }
        String providerId = url2.substring(url2.lastIndexOf("/") + 1);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));
            String line = br.readLine();
            while (line != null) {
                int comment = line.indexOf(35);
                if (comment != -1) {
                    line = line.substring(0, comment);
                }
                if ((line = line.trim()).length() > 0) {
                    classes.add(line);
                }
                line = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            // empty catch block
        }
        return classes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String lookupByJREPropertyFile(String path, String property) throws IOException {
        String jreDirectory = System.getProperty("java.home");
        File configurationFile = new File(jreDirectory + File.separator + path);
        if (configurationFile.exists() && configurationFile.canRead()) {
            Properties properties = new Properties();
            FileInputStream in = null;
            try {
                in = new FileInputStream(configurationFile);
                properties.load(in);
                String string = properties.getProperty(property);
                return string;
            }
            finally {
                if (in != null) {
                    try {
                        ((InputStream)in).close();
                    }
                    catch (Exception e) {}
                }
            }
        }
        return null;
    }

    private static Object getRegistry() {
        if (registryTracker == null) {
            return null;
        }
        return ((ServiceTracker)registryTracker).getService();
    }
}

