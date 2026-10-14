/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.bus.spring;

import org.apache.cxf.bus.spring.BusDefinitionParser;
import org.apache.cxf.configuration.spring.SimpleBeanDefinitionParser;
import org.apache.cxf.feature.FastInfosetFeature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.workqueue.AutomaticWorkQueueImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("bus", (BeanDefinitionParser)new BusDefinitionParser());
        this.registerBeanDefinitionParser("logging", (BeanDefinitionParser)new SimpleBeanDefinitionParser(LoggingFeature.class));
        this.registerBeanDefinitionParser("fastinfoset", (BeanDefinitionParser)new SimpleBeanDefinitionParser(FastInfosetFeature.class));
        this.registerBeanDefinitionParser("workqueue", (BeanDefinitionParser)new SimpleBeanDefinitionParser(AutomaticWorkQueueImpl.class){

            @Override
            protected void processNameAttribute(Element element, ParserContext ctx, BeanDefinitionBuilder bean, String val) {
                bean.addPropertyValue("name", (Object)val);
                element.removeAttribute("name");
                if (!element.hasAttribute("id")) {
                    element.setAttribute("id", "cxf.workqueue." + val);
                }
            }
        });
    }
}

