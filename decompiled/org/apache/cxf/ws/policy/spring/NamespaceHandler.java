/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.ws.policy.spring;

import org.apache.cxf.ws.policy.spring.ExternalAttachmentProviderBeanDefinitionParser;
import org.apache.cxf.ws.policy.spring.PolicyEngineBeanDefinitionParser;
import org.apache.cxf.ws.policy.spring.PolicyFeatureBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("policies", (BeanDefinitionParser)new PolicyFeatureBeanDefinitionParser());
        this.registerBeanDefinitionParser("engine", (BeanDefinitionParser)new PolicyEngineBeanDefinitionParser());
        this.registerBeanDefinitionParser("externalAttachment", (BeanDefinitionParser)new ExternalAttachmentProviderBeanDefinitionParser());
    }
}

