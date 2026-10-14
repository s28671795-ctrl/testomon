/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 */
package org.apache.cxf.binding.soap.spring;

import org.apache.cxf.binding.soap.spring.SoapBindingInfoConfigBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("soapBinding", (BeanDefinitionParser)new SoapBindingInfoConfigBeanDefinitionParser());
    }
}

