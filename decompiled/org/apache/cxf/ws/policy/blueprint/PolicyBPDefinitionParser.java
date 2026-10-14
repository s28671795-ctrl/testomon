/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.apache.aries.blueprint.mutable.MutablePassThroughMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 *  org.osgi.service.blueprint.reflect.Target
 */
package org.apache.cxf.ws.policy.blueprint;

import java.util.UUID;
import java.util.concurrent.Callable;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutablePassThroughMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.ws.policy.PolicyBean;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.Target;
import org.w3c.dom.Element;

public class PolicyBPDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        MutablePassThroughMetadata factory2 = (MutablePassThroughMetadata)context.createMetadata(MutablePassThroughMetadata.class);
        factory2.setId(this.resolveId(element, context) + UUID.randomUUID().toString());
        PolicyBean policyBean = new PolicyBean();
        policyBean.setElement(element);
        factory2.setObject(new PassThroughCallable<PolicyBean>(policyBean));
        MutableBeanMetadata resourceBean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        resourceBean.setId(this.resolveId(element, context));
        resourceBean.setFactoryComponent((Target)factory2);
        resourceBean.setFactoryMethod("call");
        return resourceBean;
    }

    protected String resolveId(Element element, ParserContext ctx) {
        return element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public static class PassThroughCallable<T>
    implements Callable<T> {
        private T value;

        public PassThroughCallable(T value) {
            this.value = value;
        }

        @Override
        public T call() throws Exception {
            return this.value;
        }
    }
}

