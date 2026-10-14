/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.apache.aries.blueprint.mutable.MutablePassThroughMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 *  org.osgi.service.blueprint.reflect.Target
 */
package org.apache.cxf.binding.soap.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutablePassThroughMetadata;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.binding.soap.blueprint.SoapVersionTypeConverter;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.Target;
import org.w3c.dom.Element;

public class SoapBindingBPInfoConfigDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        if (!context.getComponentDefinitionRegistry().containsComponentDefinition(SoapVersionTypeConverter.class.getName())) {
            MutablePassThroughMetadata md = (MutablePassThroughMetadata)context.createMetadata(MutablePassThroughMetadata.class);
            md.setObject((Object)new SoapVersionTypeConverter());
            md.setId(SoapVersionTypeConverter.class.getName());
            context.getComponentDefinitionRegistry().registerTypeConverter((Target)md);
        }
        MutableBeanMetadata cxfBean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        cxfBean.setRuntimeClass(SoapBindingConfiguration.class);
        this.parseAttributes(element, context, cxfBean);
        this.parseChildElements(element, context, cxfBean);
        return cxfBean;
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("version".equals(name) || "mtomEnabled".equals(name) || "style".equals(name) || "use".equals(name)) {
            bean.addProperty(name, this.parseMapData(ctx, (ComponentMetadata)bean, el));
        }
    }
}

