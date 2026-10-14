/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.ws.policy.spring;

import org.apache.cxf.Bus;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class PolicyEngineBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        super.addBusWiringAttribute(bean, BusWiringType.CONSTRUCTOR);
        super.doParse(element, ctx, bean);
    }

    @Override
    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element e, String name) {
        if ("alternativeSelector".equals(name)) {
            this.setFirstChildAsProperty(e, ctx, bean, name);
        }
    }

    @Override
    protected Class<?> getBeanClass(Element el) {
        return PolicyEngineConfig.class;
    }

    @Override
    protected String resolveId(Element e, AbstractBeanDefinition abd, ParserContext ctx) throws BeanDefinitionStoreException {
        return PolicyEngineConfig.class.getName();
    }

    public static class PolicyEngineConfig {
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

