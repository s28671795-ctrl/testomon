/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;

public abstract class BusFactory {
    public static final String BUS_FACTORY_PROPERTY_NAME = "org.apache.cxf.bus.factory";
    public static final String DEFAULT_BUS_FACTORY = "org.apache.cxf.bus.CXFBusFactory";
    protected static Bus defaultBus;
    protected static final Map<Thread, BusHolder> THREAD_BUSSES;
    protected static final ThreadLocal<BusHolder> THREAD_BUS;
    private static final Logger LOG;

    public abstract Bus createBus();

    public static synchronized Bus getDefaultBus() {
        return BusFactory.getDefaultBus(true);
    }

    public static synchronized Bus getDefaultBus(boolean createIfNeeded) {
        if (defaultBus == null && createIfNeeded) {
            defaultBus = BusFactory.newInstance().createBus();
        }
        if (defaultBus == null) {
            return null;
        }
        return defaultBus;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BusHolder getThreadBusHolder(boolean set) {
        BusHolder h2 = THREAD_BUS.get();
        if (h2 == null || h2.stale) {
            Thread cur = Thread.currentThread();
            Map<Thread, BusHolder> map = THREAD_BUSSES;
            synchronized (map) {
                h2 = THREAD_BUSSES.get(cur);
            }
            if (h2 == null || h2.stale) {
                h2 = new BusHolder();
                map = THREAD_BUSSES;
                synchronized (map) {
                    THREAD_BUSSES.put(cur, h2);
                }
            }
            if (set) {
                THREAD_BUS.set(h2);
            }
        }
        return h2;
    }

    public static synchronized void setDefaultBus(Bus bus) {
        defaultBus = bus;
        BusHolder b = BusFactory.getThreadBusHolder(false);
        b.bus = bus;
        if (bus == null) {
            b.stale = true;
            THREAD_BUS.remove();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setThreadDefaultBus(Bus bus) {
        if (bus == null) {
            BusHolder h2 = THREAD_BUS.get();
            if (h2 == null) {
                Thread cur = Thread.currentThread();
                Map<Thread, BusHolder> map = THREAD_BUSSES;
                synchronized (map) {
                    h2 = THREAD_BUSSES.get(cur);
                }
            }
            if (h2 != null) {
                h2.bus = null;
                h2.stale = true;
                THREAD_BUS.remove();
            }
        } else {
            BusHolder b = BusFactory.getThreadBusHolder(true);
            b.bus = bus;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Bus getAndSetThreadDefaultBus(Bus bus) {
        if (bus == null) {
            BusHolder b = THREAD_BUS.get();
            if (b == null) {
                Thread cur = Thread.currentThread();
                Map<Thread, BusHolder> map = THREAD_BUSSES;
                synchronized (map) {
                    b = THREAD_BUSSES.get(cur);
                }
            }
            if (b != null) {
                Bus orig = b.bus;
                b.bus = null;
                b.stale = true;
                THREAD_BUS.remove();
                return orig;
            }
            return null;
        }
        BusHolder b = BusFactory.getThreadBusHolder(true);
        Bus old = b.bus;
        b.bus = bus;
        return old;
    }

    public static Bus getThreadDefaultBus() {
        return BusFactory.getThreadDefaultBus(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Bus getThreadDefaultBus(boolean createIfNeeded) {
        if (createIfNeeded) {
            BusHolder b = BusFactory.getThreadBusHolder(false);
            if (b.bus == null) {
                b.bus = BusFactory.createThreadBus();
            }
            return b.bus;
        }
        BusHolder h2 = THREAD_BUS.get();
        if (h2 == null || h2.stale) {
            Thread cur = Thread.currentThread();
            Map<Thread, BusHolder> map = THREAD_BUSSES;
            synchronized (map) {
                h2 = THREAD_BUSSES.get(cur);
            }
        }
        return h2 == null || h2.stale ? null : h2.bus;
    }

    private static synchronized Bus createThreadBus() {
        BusHolder b = BusFactory.getThreadBusHolder(false);
        if (b.bus == null) {
            b.bus = BusFactory.getDefaultBus(true);
        }
        return b.bus;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clearDefaultBusForAnyThread(Bus bus) {
        Map<Thread, BusHolder> map = THREAD_BUSSES;
        synchronized (map) {
            Iterator<BusHolder> iterator2 = THREAD_BUSSES.values().iterator();
            while (iterator2.hasNext()) {
                BusHolder itBus = iterator2.next();
                if (bus != null && itBus != null && itBus.bus != null && !itBus.stale && !bus.equals(itBus.bus)) continue;
                if (itBus != null) {
                    itBus.bus = null;
                    itBus.stale = true;
                }
                iterator2.remove();
            }
        }
    }

    public static synchronized boolean possiblySetDefaultBus(Bus bus) {
        BusHolder b = BusFactory.getThreadBusHolder(false);
        if (b.bus == null) {
            b.bus = bus;
        }
        if (defaultBus == null) {
            defaultBus = bus;
            return true;
        }
        return false;
    }

    public static BusFactory newInstance() {
        return BusFactory.newInstance(null);
    }

    public static BusFactory newInstance(String className) {
        ClassLoader loader;
        if (className == null && (className = BusFactory.getBusFactoryClass(loader = Thread.currentThread().getContextClassLoader())) == null && loader != BusFactory.class.getClassLoader()) {
            className = BusFactory.getBusFactoryClass(BusFactory.class.getClassLoader());
        }
        if (className == null) {
            className = DEFAULT_BUS_FACTORY;
        }
        try {
            Class<BusFactory> busFactoryClass = ClassLoaderUtils.loadClass(className, BusFactory.class).asSubclass(BusFactory.class);
            return busFactoryClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception ex) {
            LogUtils.log(LOG, Level.SEVERE, "BUS_FACTORY_INSTANTIATION_EXC", ex);
            throw new RuntimeException(ex);
        }
    }

    protected void initializeBus(Bus bus) {
    }

    private static String getBusFactoryClass(ClassLoader classLoader) {
        String busFactoryClass = SystemPropertyAction.getPropertyOrNull(BUS_FACTORY_PROPERTY_NAME);
        if (BusFactory.isValidBusFactoryClass(busFactoryClass)) {
            return busFactoryClass;
        }
        try {
            InputStream is;
            String serviceId = "META-INF/services/org.apache.cxf.bus.factory";
            if (classLoader == null) {
                classLoader = Thread.currentThread().getContextClassLoader();
            }
            if ((is = classLoader == null ? ClassLoader.getSystemResourceAsStream(serviceId) : classLoader.getResourceAsStream(serviceId)) == null) {
                serviceId = "META-INF/cxf/org.apache.cxf.bus.factory";
                if (classLoader == null) {
                    classLoader = Thread.currentThread().getContextClassLoader();
                }
                is = classLoader == null ? ClassLoader.getSystemResourceAsStream(serviceId) : classLoader.getResourceAsStream(serviceId);
            }
            String busFactoryCondition = null;
            if (is != null) {
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));){
                    busFactoryClass = rd.readLine();
                    busFactoryCondition = rd.readLine();
                }
            }
            if (BusFactory.isValidBusFactoryClass(busFactoryClass) && busFactoryCondition != null) {
                try {
                    Class<BusFactory> cls = ClassLoaderUtils.loadClass(busFactoryClass, BusFactory.class).asSubclass(BusFactory.class);
                    if (busFactoryCondition.startsWith("#")) {
                        busFactoryCondition = busFactoryCondition.substring(1);
                    }
                    int idx = busFactoryCondition.indexOf(44);
                    while (idx != -1) {
                        cls.getClassLoader().loadClass(busFactoryCondition.substring(0, idx));
                        busFactoryCondition = busFactoryCondition.substring(idx + 1);
                        idx = busFactoryCondition.indexOf(44);
                    }
                    cls.getClassLoader().loadClass(busFactoryCondition);
                }
                catch (ClassNotFoundException | NoClassDefFoundError e) {
                    busFactoryClass = DEFAULT_BUS_FACTORY;
                }
            }
        }
        catch (Exception ex) {
            LogUtils.log(LOG, Level.SEVERE, "FAILED_TO_DETERMINE_BUS_FACTORY_EXC", ex);
        }
        return busFactoryClass;
    }

    private static boolean isValidBusFactoryClass(String busFactoryClassName) {
        return busFactoryClassName != null && !busFactoryClassName.isEmpty();
    }

    static {
        THREAD_BUSSES = new WeakHashMap<Thread, BusHolder>();
        THREAD_BUS = new ThreadLocal();
        LOG = LogUtils.getL7dLogger(BusFactory.class);
    }

    static class BusHolder {
        Bus bus;
        volatile boolean stale;

        BusHolder() {
        }
    }
}

