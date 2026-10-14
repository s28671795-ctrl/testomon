/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.ws.addressing.spring;

import org.apache.cxf.ws.addressing.spring.AddressingBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("addressing", (BeanDefinitionParser)new AddressingBeanDefinitionParser());
    }
}

