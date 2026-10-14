/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.xml.NamespaceHandlerResolver
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.io.Resource
 */
package org.apache.cxf.bus.spring;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.bus.spring.BusApplicationContext;
import org.apache.cxf.bus.spring.SpringBeanLocator;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

public class SpringBusFactory
extends BusFactory {
    private static final Logger LOG = LogUtils.getL7dLogger(SpringBusFactory.class);
    private final ApplicationContext context;
    private NamespaceHandlerResolver resolver;

    public SpringBusFactory() {
        this.context = null;
    }

    public SpringBusFactory(ApplicationContext context) {
        this.context = context;
        this.resolver = SpringBusFactory.tryFindNamespaceHandler(context);
    }

    public SpringBusFactory(NamespaceHandlerResolver r) {
        this.context = null;
        this.resolver = r;
    }

    private static NamespaceHandlerResolver tryFindNamespaceHandler(ApplicationContext ctx) {
        try {
            SpringBeanLocator sbl = new SpringBeanLocator(ctx);
            List<NamespaceHandlerResolver> r = sbl.getOSGiServices(NamespaceHandlerResolver.class);
            if (r != null && !r.isEmpty()) {
                return r.get(0);
            }
        }
        catch (Exception t2) {
            LOG.log(Level.FINEST, "Could not find NamespaceHandlerResolver", t2);
        }
        return null;
    }

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    public void setNamespaceHandlerResolver(NamespaceHandlerResolver r) {
        this.resolver = r;
    }

    @Override
    public Bus createBus() {
        return this.createBus((String)null);
    }

    private boolean defaultBusNotExists() {
        if (null != this.context) {
            return !this.context.containsBean("cxf");
        }
        return true;
    }

    public Bus createBus(String cfgFile) {
        return this.createBus(cfgFile, this.defaultBusNotExists());
    }

    public Bus createBus(String[] cfgFiles) {
        return this.createBus(cfgFiles, this.defaultBusNotExists());
    }

    protected Bus finishCreatingBus(ConfigurableApplicationContext bac) {
        Bus bus = (Bus)bac.getBean("cxf");
        bus.setExtension(bac, ApplicationContext.class);
        if (bac instanceof BusApplicationContext) {
            bus.setExtension((BusApplicationContext)bac, BusApplicationContext.class);
        }
        SpringBusFactory.possiblySetDefaultBus(bus);
        this.initializeBus(bus);
        this.registerApplicationContextLifeCycleListener(bus, bac);
        if (bus instanceof SpringBus && this.defaultBusNotExists()) {
            ((SpringBus)bus).setCloseContext(true);
        }
        return bus;
    }

    public Bus createBus(String cfgFile, boolean includeDefaults) {
        if (cfgFile == null) {
            return this.createBus((String[])null, includeDefaults);
        }
        return this.createBus(new String[]{cfgFile}, includeDefaults);
    }

    public Bus createBus(String[] cfgFiles, boolean includeDefaults) {
        try {
            String userCfgFile = SystemPropertyAction.getPropertyOrNull("cxf.config.file");
            String sysCfgFileUrl = SystemPropertyAction.getPropertyOrNull("cxf.config.file.url");
            final Resource r = BusApplicationContext.findResource("cxf.xml");
            boolean exists = true;
            if (r != null) {
                exists = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        return r.exists();
                    }
                });
            }
            if (this.context == null && userCfgFile == null && cfgFiles == null && sysCfgFileUrl == null && (r == null || !exists) && includeDefaults) {
                return new CXFBusFactory().createBus();
            }
            return this.finishCreatingBus(this.createApplicationContext(cfgFiles, includeDefaults));
        }
        catch (BeansException ex) {
            LogUtils.log(LOG, Level.WARNING, "APP_CONTEXT_CREATION_FAILED_MSG", (Throwable)ex, (Object[])null);
            throw new RuntimeException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ConfigurableApplicationContext createApplicationContext(String[] cfgFiles, boolean includeDefaults) {
        try {
            return new BusApplicationContext(cfgFiles, includeDefaults, this.context, this.resolver);
        }
        catch (BeansException ex) {
            LogUtils.log(LOG, Level.WARNING, "INITIAL_APP_CONTEXT_CREATION_FAILED_MSG", (Throwable)ex, (Object[])null);
            ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
            if (contextLoader != BusApplicationContext.class.getClassLoader()) {
                Thread.currentThread().setContextClassLoader(BusApplicationContext.class.getClassLoader());
                try {
                    BusApplicationContext busApplicationContext = new BusApplicationContext(cfgFiles, includeDefaults, this.context);
                    return busApplicationContext;
                }
                finally {
                    Thread.currentThread().setContextClassLoader(contextLoader);
                }
            }
            throw ex;
        }
    }

    public Bus createBus(URL url2) {
        return this.createBus(url2, this.defaultBusNotExists());
    }

    public Bus createBus(URL[] urls2) {
        return this.createBus(urls2, this.defaultBusNotExists());
    }

    public Bus createBus(URL url2, boolean includeDefaults) {
        if (url2 == null) {
            return this.createBus((URL[])null, includeDefaults);
        }
        return this.createBus(new URL[]{url2}, includeDefaults);
    }

    public Bus createBus(URL[] urls2, boolean includeDefaults) {
        try {
            return this.finishCreatingBus(this.createAppContext(urls2, includeDefaults));
        }
        catch (BeansException ex) {
            LogUtils.log(LOG, Level.WARNING, "APP_CONTEXT_CREATION_FAILED_MSG", (Throwable)ex, (Object[])null);
            throw new RuntimeException(ex);
        }
    }

    protected ConfigurableApplicationContext createAppContext(URL[] urls2, boolean includeDefaults) {
        return new BusApplicationContext(urls2, includeDefaults, this.context, this.resolver);
    }

    void registerApplicationContextLifeCycleListener(Bus bus, ConfigurableApplicationContext bac) {
        BusLifeCycleManager lm = bus.getExtension(BusLifeCycleManager.class);
        if (null != lm) {
            lm.registerLifeCycleListener(new BusApplicationContextLifeCycleListener(bac));
        }
    }

    static class BusApplicationContextLifeCycleListener
    implements BusLifeCycleListener {
        private ConfigurableApplicationContext bac;

        BusApplicationContextLifeCycleListener(ConfigurableApplicationContext b) {
            this.bac = b;
        }

        @Override
        public void initComplete() {
        }

        @Override
        public void preShutdown() {
        }

        @Override
        public void postShutdown() {
            this.bac.close();
        }
    }
}

