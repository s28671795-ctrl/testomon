/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 */
package org.apache.cxf.frontend.blueprint;

import javax.xml.namespace.QName;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.configuration.blueprint.SimpleBPBeanDefinitionParser;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.w3c.dom.Element;

public class ClientProxyFactoryBeanDefinitionParser
extends SimpleBPBeanDefinitionParser {
    public ClientProxyFactoryBeanDefinitionParser() {
        this(ClientProxyFactoryBean.class);
    }

    public ClientProxyFactoryBeanDefinitionParser(Class<?> cls) {
        super(cls);
    }

    @Override
    public String getFactorySuffix() {
        return ".proxyFactory";
    }

    @Override
    public String getFactoryCreateType(Element element) {
        return element.getAttribute("serviceClass");
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
        } else if ("binding".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "bindingConfig");
        } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "schemaLocations".equals(name) || "handlers".equals(name)) {
            bean.addProperty(name, this.parseListData(ctx, (ComponentMetadata)bean, el));
        } else {
            this.setFirstChildAsProperty(el, ctx, bean, name);
        }
    }

    @Override
    protected boolean hasBusProperty() {
        return true;
    }
}

