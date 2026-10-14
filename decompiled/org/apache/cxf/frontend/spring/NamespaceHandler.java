/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.frontend.spring;

import org.apache.cxf.configuration.spring.StringBeanDefinitionParser;
import org.apache.cxf.frontend.spring.ClientProxyFactoryBeanDefinitionParser;
import org.apache.cxf.frontend.spring.ServerFactoryBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("client", (BeanDefinitionParser)new ClientProxyFactoryBeanDefinitionParser());
        this.registerBeanDefinitionParser("server", (BeanDefinitionParser)new ServerFactoryBeanDefinitionParser());
        this.registerBeanDefinitionParser("schemaLocation", (BeanDefinitionParser)new StringBeanDefinitionParser());
    }
}

