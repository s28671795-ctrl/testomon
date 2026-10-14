/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.apache.aries.blueprint.mutable.MutableCollectionMetadata
 *  org.apache.aries.blueprint.mutable.MutablePassThroughMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.ws.policy.blueprint;

import java.util.List;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutableCollectionMetadata;
import org.apache.aries.blueprint.mutable.MutablePassThroughMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.policy.WSPolicyFeature;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class PolicyFeatureBPDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        MutableCollectionMetadata ps = (MutableCollectionMetadata)context.createMetadata(MutableCollectionMetadata.class);
        ps.setCollectionClass(List.class);
        MutableCollectionMetadata prs = (MutableCollectionMetadata)context.createMetadata(MutableCollectionMetadata.class);
        ps.setCollectionClass(List.class);
        MutableBeanMetadata cxfBean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        cxfBean.setRuntimeClass(WSPolicyFeature.class);
        Element elem = DOMUtils.getFirstElement(element);
        while (elem != null) {
            if ("Policy".equals(elem.getLocalName())) {
                ps.addValue(PolicyFeatureBPDefinitionParser.createElement(context, elem));
            } else if ("PolicyReference".equals(elem.getLocalName())) {
                prs.addValue(PolicyFeatureBPDefinitionParser.createElement(context, elem));
            }
            elem = DOMUtils.getNextElement(elem);
        }
        cxfBean.addProperty("policyElements", (Metadata)ps);
        cxfBean.addProperty("policyReferenceElements", (Metadata)prs);
        super.parseChildElements(element, context, cxfBean);
        return cxfBean;
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("alternativeSelector".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, name);
        }
    }

    public static Metadata createElement(ParserContext context, Element value) {
        MutablePassThroughMetadata anElement = (MutablePassThroughMetadata)context.createMetadata(MutablePassThroughMetadata.class);
        anElement.setObject((Object)value);
        return anElement;
    }
}

