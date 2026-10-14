/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.ws.policy.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class PolicyEngineBPDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata policyEngineConfig = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        policyEngineConfig.setRuntimeClass(PolicyEngineConfig.class);
        String bus = element.getAttribute("bus");
        if (StringUtils.isEmpty(bus)) {
            bus = "cxf";
        }
        policyEngineConfig.addArgument((Metadata)this.getBusRef(context, bus), Bus.class.getName(), 0);
        this.parseAttributes(element, context, policyEngineConfig);
        this.parseChildElements(element, context, policyEngineConfig);
        policyEngineConfig.setId(PolicyEngineConfig.class.getName() + context.generateId());
        return policyEngineConfig;
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("alternativeSelector".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, name);
        }
    }

    public static class PolicyEngineConfig
    extends AbstractFeature {
        private PolicyEngineImpl engine;

        public PolicyEngineConfig(Bus bus) {
            this.engine = (PolicyEngineImpl)bus.getExtension(PolicyEngine.class);
        }

        public boolean getEnabled() {
            return this.engine.isEnabled();
        }

        public void setEnabled(boolean enabled) {
            this.engine.setEnabled(enabled);
        }

        public boolean getIgnoreUnknownAssertions() {
            return this.engine.isIgnoreUnknownAssertions();
        }

        public void setIgnoreUnknownAssertions(boolean ignoreUnknownAssertions) {
            this.engine.setIgnoreUnknownAssertions(ignoreUnknownAssertions);
        }

        public AlternativeSelector getAlternativeSelector() {
            return this.engine.getAlternativeSelector();
        }

        public void setAlternativeSelector(AlternativeSelector alternativeSelector) {
            this.engine.setAlternativeSelector(alternativeSelector);
        }
    }
}

