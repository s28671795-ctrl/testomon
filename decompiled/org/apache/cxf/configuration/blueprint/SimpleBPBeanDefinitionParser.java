/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.BeanProperty
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 *  org.osgi.service.blueprint.reflect.Target
 */
package org.apache.cxf.configuration.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.osgi.service.blueprint.reflect.BeanProperty;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.Target;
import org.w3c.dom.Element;

public class SimpleBPBeanDefinitionParser
extends AbstractBPBeanDefinitionParser {
    protected Class<?> cls;

    public SimpleBPBeanDefinitionParser(Class<?> cls) {
        this.cls = cls;
    }

    public String getFactorySuffix() {
        return null;
    }

    public String getFactoryCreateType(Element element) {
        return null;
    }

    public String getId(Element element, ParserContext context) {
        return element.hasAttribute("id") ? element.getAttribute("id") : null;
    }

    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata cxfBean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        cxfBean.setRuntimeClass(this.cls);
        String fact = this.getFactorySuffix();
        if (fact == null) {
            cxfBean.setId(this.getId(element, context));
        } else {
            cxfBean.setId(this.getId(element, context) + fact);
        }
        this.parseAttributes(element, context, cxfBean);
        this.parseChildElements(element, context, cxfBean);
        if (this.hasBusProperty()) {
            boolean foundBus = false;
            for (BeanProperty bp : cxfBean.getProperties()) {
                if (!"bus".equals(bp.getName())) continue;
                foundBus = true;
            }
            if (!foundBus) {
                cxfBean.addProperty("bus", (Metadata)this.getBusRef(context, "cxf"));
            }
        }
        if (fact != null) {
            context.getComponentDefinitionRegistry().registerComponentDefinition((ComponentMetadata)cxfBean);
            MutableBeanMetadata bean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
            bean.setId(this.getId(element, context));
            bean.setFactoryComponent((Target)cxfBean);
            bean.setFactoryMethod("create");
            bean.setClassName(this.getFactoryCreateType(element));
            return bean;
        }
        return cxfBean;
    }
}

