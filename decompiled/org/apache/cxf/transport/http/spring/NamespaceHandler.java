/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.transport.http.spring;

import org.apache.cxf.transport.http.spring.HttpAuthSupplierBeanDefinitionParser;
import org.apache.cxf.transport.http.spring.HttpConduitBeanDefinitionParser;
import org.apache.cxf.transport.http.spring.HttpDestinationBeanDefinitionParser;
import org.apache.cxf.transport.http.spring.MessageTrustDeciderBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("conduit", (BeanDefinitionParser)new HttpConduitBeanDefinitionParser());
        this.registerBeanDefinitionParser("trustDecider", (BeanDefinitionParser)new MessageTrustDeciderBeanDefinitionParser());
        this.registerBeanDefinitionParser("authSupplier", (BeanDefinitionParser)new HttpAuthSupplierBeanDefinitionParser());
        this.registerBeanDefinitionParser("destination", (BeanDefinitionParser)new HttpDestinationBeanDefinitionParser());
    }
}

