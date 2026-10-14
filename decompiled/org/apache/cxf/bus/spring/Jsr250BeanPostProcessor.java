/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.Ordered
 */
package org.apache.cxf.bus.spring;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.BusApplicationContextResourceResolver;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.resource.ResourceManager;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

public class Jsr250BeanPostProcessor
implements DestructionAwareBeanPostProcessor,
Ordered,
ApplicationContextAware {
    private ResourceManager resourceManager;
    private ApplicationContext context;
    private boolean isProcessing = true;

    Jsr250BeanPostProcessor() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
        try {
            Class<?> cls = Class.forName("org.springframework.context.annotation.CommonAnnotationBeanPostProcessor");
            this.isProcessing = this.context.getBeanNamesForType(cls, true, false).length == 0;
        }
        catch (ClassNotFoundException e) {
            this.isProcessing = true;
        }
    }

    public int getOrder() {
        return 1010;
    }

    private boolean injectable(Object bean, String beanId) {
        return !"cxf".equals(beanId) && ResourceInjector.processable(bean.getClass(), bean);
    }

    private ResourceManager getResourceManager(Object bean) {
        if (this.resourceManager == null) {
            boolean temp = this.isProcessing;
            this.isProcessing = false;
            if (bean instanceof ResourceManager) {
                this.resourceManager = (ResourceManager)bean;
                this.resourceManager.addResourceResolver(new BusApplicationContextResourceResolver(this.context));
            } else if (bean instanceof Bus) {
                Bus b = (Bus)bean;
                ResourceManager m4 = b.getExtension(ResourceManager.class);
                if (m4 != null) {
                    this.resourceManager = m4;
                    if (!(b instanceof SpringBus)) {
                        this.resourceManager.addResourceResolver(new BusApplicationContextResourceResolver(this.context));
                    }
                }
            } else {
                ResourceManager m5 = null;
                Bus b = null;
                try {
                    m5 = (ResourceManager)this.context.getBean(ResourceManager.class.getName());
                }
                catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
                    // empty catch block
                }
                if (m5 == null) {
                    b = (Bus)this.context.getBean("cxf");
                    m5 = b.getExtension(ResourceManager.class);
                }
                if (m5 != null) {
                    this.resourceManager = m5;
                    if (!(b instanceof SpringBus)) {
                        this.resourceManager.addResourceResolver(new BusApplicationContextResourceResolver(this.context));
                    }
                }
            }
            this.isProcessing = temp;
        }
        return this.resourceManager;
    }

    public Object postProcessAfterInitialization(Object bean, String beanId) {
        if (!this.isProcessing) {
            if (this.resourceManager == null && bean instanceof ResourceManager) {
                this.resourceManager = (ResourceManager)bean;
                this.resourceManager.addResourceResolver(new BusApplicationContextResourceResolver(this.context));
            }
            return bean;
        }
        if (bean != null && this.injectable(bean, beanId)) {
            new ResourceInjector(this.getResourceManager(bean)).construct(bean);
        }
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanId) {
        if (!this.isProcessing) {
            return bean;
        }
        if (bean instanceof Bus) {
            this.getResourceManager(bean);
        }
        if (bean != null && this.injectable(bean, beanId)) {
            new ResourceInjector(this.getResourceManager(bean)).inject(bean);
        }
        return bean;
    }

    public void postProcessBeforeDestruction(Object bean, String beanId) {
        if (!this.isProcessing) {
            return;
        }
        if (bean != null && this.injectable(bean, beanId)) {
            new ResourceInjector(this.getResourceManager(bean)).destroy(bean);
        }
    }

    public boolean requiresDestruction(Object bean) {
        return this.isProcessing;
    }
}

