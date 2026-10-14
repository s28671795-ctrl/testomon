/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.ws.policy.spring;

import org.apache.cxf.ws.policy.PolicyBean;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class PolicyBeanDefinitionParser
extends AbstractSingleBeanDefinitionParser {
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        bean.addPropertyValue("element", (Object)element);
    }

    protected String resolveId(Element element, AbstractBeanDefinition bean, ParserContext ctx) throws BeanDefinitionStoreException {
        return element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    protected Class<?> getBeanClass(Element el) {
        return PolicyBean.class;
    }
}

