/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.PropertyValue
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.bus.spring;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Element;

public class BusDefinitionParser
extends AbstractBeanDefinitionParser {
    private static AtomicInteger counter = new AtomicInteger(0);

    public BusDefinitionParser() {
        this.setBeanClass(BusConfig.class);
    }

    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        String id;
        String bus = element.getAttribute("bus");
        if (StringUtils.isEmpty(bus) && StringUtils.isEmpty(bus = element.getAttribute("name"))) {
            element.setAttribute("bus", bus);
        }
        element.removeAttribute("name");
        if (StringUtils.isEmpty(bus)) {
            bus = "cxf";
        }
        if (!StringUtils.isEmpty(id = element.getAttribute("id"))) {
            bean.addPropertyValue("id", (Object)id);
        }
        super.doParse(element, ctx, bean);
        if (ctx.getRegistry().containsBeanDefinition(bus)) {
            BeanDefinition def = ctx.getRegistry().getBeanDefinition(bus);
            this.copyProps(bean, def);
            bean.addConstructorArgValue((Object)bus);
        } else if (!"cxf".equals(bus)) {
            bean.getRawBeanDefinition().setBeanClass(SpringBus.class);
            bean.setDestroyMethodName("shutdown");
            try {
                element.setUserData("ID", bus, null);
                bean.getRawBeanDefinition().getPropertyValues().removePropertyValue("bus");
            }
            catch (Throwable throwable) {}
        } else {
            this.addBusWiringAttribute(bean, BusWiringType.PROPERTY, bus, ctx);
            bean.getRawBeanDefinition().setAttribute(WIRE_BUS_CREATE, (Object)this.resolveId(element, null, ctx));
            bean.addConstructorArgValue((Object)bus);
        }
    }

    @Override
    protected boolean processBusAttribute(Element element, ParserContext ctx, BeanDefinitionBuilder bean, String val) {
        return false;
    }

    private void copyProps(BeanDefinitionBuilder src, BeanDefinition def) {
        for (PropertyValue v : src.getBeanDefinition().getPropertyValues().getPropertyValues()) {
            if (!"bus".equals(v.getName())) {
                def.getPropertyValues().addPropertyValue(v.getName(), v.getValue());
            }
            src.getBeanDefinition().getPropertyValues().removePropertyValue(v);
        }
    }

    @Override
    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element e, String name) {
        if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name)) {
            List list = ctx.getDelegate().parseListElement(e, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue(name, (Object)list);
        } else if ("properties".equals(name)) {
            Map map = ctx.getDelegate().parseMapElement(e, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue("properties", (Object)map);
        }
    }

    @Override
    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext ctx) {
        String bus = null;
        try {
            bus = (String)element.getUserData("ID");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (bus == null) {
            bus = element.getAttribute("bus");
            if (StringUtils.isEmpty(bus)) {
                bus = element.getAttribute("name");
            }
            bus = StringUtils.isEmpty(bus) ? "cxf.config" + counter.getAndIncrement() : bus + ".config";
            try {
                element.setUserData("ID", bus, null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return bus;
    }

    @NoJSR250Annotations
    public static class BusConfig
    extends AbstractBasicInterceptorProvider
    implements ApplicationContextAware {
        Bus bus;
        String busName;
        String id;
        Collection<Feature> features;
        Map<String, Object> properties;

        public BusConfig(String busName) {
            this.busName = busName;
        }

        public void setBus(Bus bb) {
            if (this.bus == bb) {
                return;
            }
            if (this.properties != null) {
                bb.setProperties(this.properties);
                this.properties = null;
            }
            if (!this.getInInterceptors().isEmpty()) {
                bb.getInInterceptors().addAll(this.getInInterceptors());
            }
            if (!this.getOutInterceptors().isEmpty()) {
                bb.getOutInterceptors().addAll(this.getOutInterceptors());
            }
            if (!this.getInFaultInterceptors().isEmpty()) {
                bb.getInFaultInterceptors().addAll(this.getInFaultInterceptors());
            }
            if (!this.getOutFaultInterceptors().isEmpty()) {
                bb.getOutFaultInterceptors().addAll(this.getOutFaultInterceptors());
            }
            if (!StringUtils.isEmpty(this.id)) {
                bb.setId(this.id);
            }
            if (this.features != null) {
                bb.setFeatures(this.features);
                this.features = null;
            }
            this.bus = bb;
        }

        public void setApplicationContext(ApplicationContext applicationContext) {
            if (this.bus != null) {
                return;
            }
        }

        @Override
        public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
            if (this.bus != null) {
                return this.bus.getOutFaultInterceptors();
            }
            return super.getOutFaultInterceptors();
        }

        @Override
        public List<Interceptor<? extends Message>> getInFaultInterceptors() {
            if (this.bus != null) {
                return this.bus.getInFaultInterceptors();
            }
            return super.getInFaultInterceptors();
        }

        @Override
        public List<Interceptor<? extends Message>> getInInterceptors() {
            if (this.bus != null) {
                return this.bus.getInInterceptors();
            }
            return super.getInInterceptors();
        }

        @Override
        public List<Interceptor<? extends Message>> getOutInterceptors() {
            if (this.bus != null) {
                return this.bus.getOutInterceptors();
            }
            return super.getOutInterceptors();
        }

        @Override
        public void setInInterceptors(List<Interceptor<? extends Message>> interceptors) {
            if (this.bus != null) {
                this.bus.getInInterceptors().addAll(interceptors);
            } else {
                super.setInInterceptors(interceptors);
            }
        }

        @Override
        public void setInFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
            if (this.bus != null) {
                this.bus.getInFaultInterceptors().addAll(interceptors);
            } else {
                super.setInFaultInterceptors(interceptors);
            }
        }

        @Override
        public void setOutInterceptors(List<Interceptor<? extends Message>> interceptors) {
            if (this.bus != null) {
                this.bus.getOutInterceptors().addAll(interceptors);
            } else {
                super.setOutInterceptors(interceptors);
            }
        }

        @Override
        public void setOutFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
            if (this.bus != null) {
                this.bus.getOutFaultInterceptors().addAll(interceptors);
            } else {
                super.setOutFaultInterceptors(interceptors);
            }
        }

        public Collection<Feature> getFeatures() {
            if (this.bus != null) {
                return this.bus.getFeatures();
            }
            return this.features;
        }

        public void setFeatures(Collection<? extends Feature> features) {
            if (this.bus != null) {
                this.bus.setFeatures(features);
            } else {
                this.features = CastUtils.cast(features);
            }
        }

        public Map<String, Object> getProperties() {
            if (this.bus != null) {
                return this.bus.getProperties();
            }
            return this.properties;
        }

        public void setProperties(Map<String, Object> s2) {
            if (this.bus != null) {
                this.bus.setProperties(s2);
            } else {
                this.properties = s2;
            }
        }

        public void setId(String s2) {
            this.id = s2;
        }
    }
}

