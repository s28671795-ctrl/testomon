/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.frontend.blueprint;

import javax.xml.namespace.QName;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.SimpleBPBeanDefinitionParser;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class ServerFactoryBeanDefinitionParser
extends SimpleBPBeanDefinitionParser {
    public ServerFactoryBeanDefinitionParser() {
        this(BPServerFactoryBean.class);
    }

    public ServerFactoryBeanDefinitionParser(Class<?> cls) {
        super(cls);
    }

    @Override
    protected void mapAttribute(MutableBeanMetadata bean, Element e, String name, String val, ParserContext context) {
        if ("endpointName".equals(name) || "serviceName".equals(name)) {
            QName q = this.parseQName(e, val);
            bean.addProperty(name, this.createValue(context, q));
        } else {
            this.mapToProperty(bean, name, val, context);
        }
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("properties".equals(name)) {
            bean.addProperty("properties", this.parseMapData(ctx, (ComponentMetadata)bean, el));
        } else if ("executor".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "serviceFactory.executor");
        } else if ("invoker".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "serviceFactory.invoker");
        } else if ("binding".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "bindingConfig");
        } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "schemaLocations".equals(name)) {
            bean.addProperty(name, this.parseListData(ctx, (ComponentMetadata)bean, el));
        } else {
            this.setFirstChildAsProperty(el, ctx, bean, name);
        }
    }

    @Override
    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata bean = (MutableBeanMetadata)super.parse(element, context);
        bean.setInitMethod("init");
        bean.setDestroyMethod("destroy");
        bean.setActivation(1);
        return bean;
    }

    @Override
    public String getId(Element elem, ParserContext context) {
        String id = super.getId(elem, context);
        if (StringUtils.isEmpty(id)) {
            id = this.cls.getName() + "--" + context.getDefaultActivation();
        }
        return id;
    }

    @Override
    protected boolean hasBusProperty() {
        return true;
    }

    @NoJSR250Annotations
    public static class BPServerFactoryBean
    extends ServerFactoryBean {
        private Server server;

        public BPServerFactoryBean() {
        }

        public BPServerFactoryBean(ReflectionServiceFactoryBean fact) {
            super(fact);
        }

        @Override
        public Server getServer() {
            return this.server;
        }

        public void init() {
            this.create();
        }

        @Override
        public Server create() {
            if (this.server == null) {
                this.server = super.create();
            }
            return this.server;
        }

        public void destroy() {
            if (this.server != null) {
                this.server.destroy();
                this.server = null;
            }
        }
    }
}

