/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.AutowireCapableBeanFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
 */
package org.apache.cxf.jaxws.spring;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.bus.spring.Jsr250BeanPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class EndpointDefinitionParser
extends AbstractBeanDefinitionParser {
    private static final Class<?> EP_CLASS = SpringEndpointImpl.class;
    private static final String IMPLEMENTOR = "implementor";

    public EndpointDefinitionParser() {
        this.setBeanClass(EP_CLASS);
    }

    @Override
    protected String getSuffix() {
        return ".jaxws-endpoint";
    }

    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        boolean isAbstract = false;
        boolean publish = true;
        NamedNodeMap atts = element.getAttributes();
        String bus = element.getAttribute("bus");
        if (StringUtils.isEmpty(bus)) {
            this.addBusWiringAttribute(bean, BusWiringType.CONSTRUCTOR);
        } else {
            bean.addConstructorArgReference(bus);
        }
        for (int i = 0; i < atts.getLength(); ++i) {
            Attr node = (Attr)atts.item(i);
            String val = node.getValue();
            String pre = node.getPrefix();
            String name = node.getLocalName();
            if ("createdFromAPI".equals(name)) {
                bean.setAbstract(true);
                isAbstract = true;
                continue;
            }
            if (this.isAttribute(pre, name) && !"publish".equals(name) && !"bus".equals(name)) {
                if ("endpointName".equals(name) || "serviceName".equals(name)) {
                    QName q = this.parseQName(element, val);
                    bean.addPropertyValue(name, (Object)q);
                    continue;
                }
                if ("depends-on".equals(name)) {
                    bean.addDependsOn(val);
                    continue;
                }
                if (IMPLEMENTOR.equals(name)) {
                    this.loadImplementor(bean, val);
                    continue;
                }
                if ("name".equals(name)) continue;
                this.mapToProperty(bean, name, val);
                continue;
            }
            if ("abstract".equals(name)) {
                bean.setAbstract(true);
                isAbstract = true;
                continue;
            }
            if (!"publish".equals(name)) continue;
            publish = "true".equals(val);
        }
        Element elem = DOMUtils.getFirstElement(element);
        while (elem != null) {
            String name = elem.getLocalName();
            if ("properties".equals(name)) {
                Map map = ctx.getDelegate().parseMapElement(elem, (BeanDefinition)bean.getBeanDefinition());
                bean.addPropertyValue("properties", (Object)map);
            } else if ("binding".equals(name)) {
                this.setFirstChildAsProperty(elem, ctx, bean, "bindingConfig");
            } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "schemaLocations".equals(name) || "handlers".equals(name)) {
                List list = ctx.getDelegate().parseListElement(elem, (BeanDefinition)bean.getBeanDefinition());
                bean.addPropertyValue(name, (Object)list);
            } else if (IMPLEMENTOR.equals(name)) {
                ctx.getDelegate().parseConstructorArgElement(elem, (BeanDefinition)bean.getBeanDefinition());
            } else {
                this.setFirstChildAsProperty(elem, ctx, bean, name);
            }
            elem = DOMUtils.getNextElement(elem);
        }
        if (!isAbstract) {
            if (publish) {
                bean.setInitMethodName("publish");
            }
            bean.setDestroyMethodName("stop");
        }
        bean.setLazyInit(false);
    }

    private void loadImplementor(BeanDefinitionBuilder bean, String val) {
        if (!StringUtils.isEmpty(val)) {
            bean.addPropertyValue("checkBlockConstruct", (Object)Boolean.TRUE);
            if (val.startsWith("#")) {
                bean.addConstructorArgReference(val.substring(1));
            } else {
                bean.addConstructorArgValue((Object)BeanDefinitionBuilder.genericBeanDefinition((String)val).getBeanDefinition());
            }
        }
    }

    @Override
    protected String resolveId(Element elem, AbstractBeanDefinition definition, ParserContext ctx) throws BeanDefinitionStoreException {
        String id = super.resolveId(elem, definition, ctx);
        if (StringUtils.isEmpty(id)) {
            id = EndpointImpl.class.getName() + "--" + definition.hashCode();
        }
        return id;
    }

    public static final void setBlocking(ApplicationContext ctx, EndpointImpl impl) {
        AutowireCapableBeanFactory fact = ctx.getAutowireCapableBeanFactory();
        if (fact instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory)fact;
            for (BeanPostProcessor bpp : dlbf.getBeanPostProcessors()) {
                if (CommonAnnotationBeanPostProcessor.class.isInstance(bpp)) {
                    impl.getServerFactory().setBlockPostConstruct(true);
                    impl.getServerFactory().setBlockInjection(false);
                    return;
                }
                if (!(bpp instanceof Jsr250BeanPostProcessor)) continue;
                impl.getServerFactory().setBlockInjection(true);
            }
        }
    }

    @NoJSR250Annotations
    public static class SpringEndpointImpl
    extends EndpointImpl
    implements ApplicationContextAware {
        boolean checkBlockConstruct;

        public SpringEndpointImpl(Object o) {
            super(o instanceof Bus ? (Bus)o : null, o instanceof Bus ? null : o);
        }

        public SpringEndpointImpl(Bus bus, Object implementor) {
            super(bus, implementor);
        }

        public void setCheckBlockConstruct(Boolean b) {
            this.checkBlockConstruct = b;
        }

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (this.checkBlockConstruct) {
                EndpointDefinitionParser.setBlocking(ctx, this);
            }
            if (this.getBus() == null) {
                this.setBus(BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx));
            }
        }
    }
}

