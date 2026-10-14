/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.context.support.AbstractApplicationContext
 */
package org.apache.cxf.bus.spring;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.ExtensionManagerBus;
import org.apache.cxf.bus.spring.BusApplicationContextResourceResolver;
import org.apache.cxf.bus.spring.BusDefinitionParser;
import org.apache.cxf.bus.spring.SpringBeanLocator;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.configuration.spring.ConfigurerImpl;
import org.apache.cxf.resource.ResourceManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringBus
extends ExtensionManagerBus
implements ApplicationContextAware {
    AbstractApplicationContext ctx;
    boolean closeContext;

    public void setBusConfig(BusDefinitionParser.BusConfig bc) {
        bc.setBus(this);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = (AbstractApplicationContext)applicationContext;
        ApplicationListener listener = new ApplicationListener(){

            public void onApplicationEvent(ApplicationEvent event) {
                SpringBus.this.onApplicationEvent(event);
            }
        };
        this.ctx.addApplicationListener(listener);
        for (ApplicationContext ac = applicationContext.getParent(); ac != null; ac = ac.getParent()) {
            if (!(ac instanceof AbstractApplicationContext)) continue;
            ((AbstractApplicationContext)ac).addApplicationListener(listener);
        }
        this.setExtension(applicationContext.getClassLoader(), ClassLoader.class);
        this.setExtension(new ConfigurerImpl(applicationContext), Configurer.class);
        ResourceManager m4 = this.getExtension(ResourceManager.class);
        m4.addResourceResolver(new BusApplicationContextResourceResolver(applicationContext));
        this.setExtension(applicationContext, ApplicationContext.class);
        ConfiguredBeanLocator loc = this.getExtension(ConfiguredBeanLocator.class);
        if (!(loc instanceof SpringBeanLocator)) {
            this.setExtension(new SpringBeanLocator(applicationContext, this), ConfiguredBeanLocator.class);
        }
        if (this.getState() != Bus.BusState.RUNNING) {
            this.initialize();
        }
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (this.ctx == null) {
            return;
        }
        boolean doIt = false;
        for (AbstractApplicationContext ac = this.ctx; ac != null; ac = ac.getParent()) {
            if (event.getSource() != ac) continue;
            doIt = true;
            break;
        }
        if (doIt) {
            if (event instanceof ContextRefreshedEvent) {
                if (this.getState() != Bus.BusState.RUNNING) {
                    this.initialize();
                }
            } else if (event instanceof ContextClosedEvent && this.getState() == Bus.BusState.RUNNING) {
                this.shutdown();
            }
        }
    }

    @Override
    public void destroyBeans() {
        if (this.closeContext) {
            this.ctx.close();
        }
        super.destroyBeans();
    }

    @Override
    public String getId() {
        if (this.id == null) {
            try {
                Class<?> clsbc = Class.forName("org.osgi.framework.BundleContext");
                Class<?> clsb = Class.forName("org.osgi.framework.Bundle");
                Object o = this.getExtension(clsbc);
                Object o2 = clsbc.getMethod("getBundle", new Class[0]).invoke(o, new Object[0]);
                String s2 = (String)clsb.getMethod("getSymbolicName", new Class[0]).invoke(o2, new Object[0]);
                this.id = s2 + "-" + "cxf" + Integer.toString(this.hashCode());
            }
            catch (Throwable t2) {
                this.id = super.getId();
            }
        }
        return this.id;
    }

    public void setCloseContext(boolean b) {
        this.closeContext = b;
    }
}

