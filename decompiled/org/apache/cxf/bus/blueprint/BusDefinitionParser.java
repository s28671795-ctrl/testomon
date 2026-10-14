/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.bus.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class BusDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        String bname = element.hasAttribute("bus") ? element.getAttribute("bus") : "cxf";
        String id = element.hasAttribute("id") ? element.getAttribute("id") : null;
        MutableBeanMetadata cxfBean = this.getBus(context, bname);
        this.parseAttributes(element, context, cxfBean);
        this.parseChildElements(element, context, cxfBean);
        context.getComponentDefinitionRegistry().removeComponentDefinition(bname);
        if (!StringUtils.isEmpty(id)) {
            cxfBean.addProperty("id", (Metadata)BusDefinitionParser.createValue(context, id));
        }
        return cxfBean;
    }

    @Override
    protected void processBusAttribute(Element element, ParserContext ctx, MutableBeanMetadata bean, String val) {
    }

    @Override
    protected boolean hasBusProperty() {
        return false;
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name)) {
            bean.addProperty(name, this.parseListData(ctx, (ComponentMetadata)bean, el));
        } else if ("properties".equals(name)) {
            bean.addProperty(name, this.parseMapData(ctx, (ComponentMetadata)bean, el));
        }
    }
}

