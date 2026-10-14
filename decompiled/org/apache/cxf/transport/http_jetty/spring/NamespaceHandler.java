/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.transport.http_jetty.spring;

import org.apache.cxf.transport.http_jetty.spring.JettyHTTPServerEngineBeanDefinitionParser;
import org.apache.cxf.transport.http_jetty.spring.JettyHTTPServerEngineFactoryBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("engine-factory", (BeanDefinitionParser)new JettyHTTPServerEngineFactoryBeanDefinitionParser());
        this.registerBeanDefinitionParser("engine", (BeanDefinitionParser)new JettyHTTPServerEngineBeanDefinitionParser());
    }
}

