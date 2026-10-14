/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.config.ConstructorArgumentValues
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.config.RuntimeBeanReference
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apache.cxf.bus.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.helpers.CastUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class BusWiringBeanFactoryPostProcessor
implements BeanFactoryPostProcessor {
    Bus bus;
    String busName;

    public BusWiringBeanFactoryPostProcessor() {
    }

    public BusWiringBeanFactoryPostProcessor(Bus b) {
        this.bus = b;
    }

    public BusWiringBeanFactoryPostProcessor(String n) {
        this.busName = n;
    }

    private static Bus getBusForName(String name, ApplicationContext context, boolean create) {
        if (!context.containsBean(name) && (create || "cxf".equals(name))) {
            SpringBus b = new SpringBus();
            ConfigurableApplicationContext cctx = (ConfigurableApplicationContext)context;
            cctx.getBeanFactory().registerSingleton(name, (Object)b);
            b.setApplicationContext(context);
        }
        return (Bus)context.getBean(name, Bus.class);
    }

    private Object getBusForName(String name, ConfigurableListableBeanFactory factory2, boolean create, String cn) {
        if (!factory2.containsBeanDefinition(name) && (create || "cxf".equals(name))) {
            DefaultListableBeanFactory df = (DefaultListableBeanFactory)factory2;
            RootBeanDefinition rbd = new RootBeanDefinition(SpringBus.class);
            if (cn != null) {
                rbd.setAttribute("busConfig", (Object)new RuntimeBeanReference(cn));
            }
            df.registerBeanDefinition(name, (BeanDefinition)rbd);
        } else if (cn != null) {
            BeanDefinition bd = factory2.getBeanDefinition(name);
            bd.getPropertyValues().addPropertyValue("busConfig", (Object)new RuntimeBeanReference(cn));
        }
        return new RuntimeBeanReference(name);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory2) {
        Object inject = this.bus;
        if (inject == null) {
            inject = this.getBusForName("cxf", factory2, true, null);
        } else if (!factory2.containsBeanDefinition("cxf") && !factory2.containsSingleton("cxf")) {
            factory2.registerSingleton("cxf", (Object)this.bus);
        }
        for (String beanName : factory2.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = factory2.getBeanDefinition(beanName);
            BusWiringType type = (BusWiringType)((Object)beanDefinition.getAttribute(AbstractBeanDefinitionParser.WIRE_BUS_ATTRIBUTE));
            if (type == null) continue;
            String busname = (String)beanDefinition.getAttribute(AbstractBeanDefinitionParser.WIRE_BUS_NAME);
            String create = (String)beanDefinition.getAttribute(AbstractBeanDefinitionParser.WIRE_BUS_CREATE);
            Object inj = inject;
            if (busname != null) {
                if (this.bus != null) continue;
                inj = this.getBusForName(busname, factory2, create != null, create);
            }
            beanDefinition.removeAttribute(AbstractBeanDefinitionParser.WIRE_BUS_NAME);
            beanDefinition.removeAttribute(AbstractBeanDefinitionParser.WIRE_BUS_ATTRIBUTE);
            beanDefinition.removeAttribute(AbstractBeanDefinitionParser.WIRE_BUS_CREATE);
            if (create != null) continue;
            if (BusWiringType.PROPERTY == type) {
                beanDefinition.getPropertyValues().addPropertyValue("bus", inj);
                continue;
            }
            if (BusWiringType.CONSTRUCTOR != type) continue;
            ConstructorArgumentValues constructorArgs = beanDefinition.getConstructorArgumentValues();
            this.insertConstructorArg(constructorArgs, inj);
        }
    }

    private void insertConstructorArg(ConstructorArgumentValues constructorArgs, Object valueToInsert) {
        ArrayList genericArgs = new ArrayList(CastUtils.cast(constructorArgs.getGenericArgumentValues()));
        HashMap indexedArgs = new HashMap(CastUtils.cast(constructorArgs.getIndexedArgumentValues()));
        constructorArgs.clear();
        for (ConstructorArgumentValues.ValueHolder valueHolder : genericArgs) {
            constructorArgs.addGenericArgumentValue(valueHolder);
        }
        for (Map.Entry entry : indexedArgs.entrySet()) {
            constructorArgs.addIndexedArgumentValue((Integer)entry.getKey() + 1, (ConstructorArgumentValues.ValueHolder)entry.getValue());
        }
        constructorArgs.addIndexedArgumentValue(0, valueToInsert);
    }

    public static Bus addDefaultBus(ApplicationContext ctx) {
        if (!ctx.containsBean("cxf")) {
            Bus b = BusWiringBeanFactoryPostProcessor.getBusForName("cxf", ctx, true);
            if (ctx instanceof ConfigurableApplicationContext) {
                ConfigurableApplicationContext cctx = (ConfigurableApplicationContext)ctx;
                new BusWiringBeanFactoryPostProcessor(b).postProcessBeanFactory(cctx.getBeanFactory());
            }
        }
        return (Bus)Bus.class.cast(ctx.getBean("cxf", Bus.class));
    }

    public static Bus addBus(ApplicationContext ctx, String name) {
        return BusWiringBeanFactoryPostProcessor.getBusForName(name, ctx, true);
    }
}

