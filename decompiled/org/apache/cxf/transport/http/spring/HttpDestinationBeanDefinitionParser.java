/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.transport.http.spring;

import javax.xml.namespace.QName;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class HttpDestinationBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    private static final String HTTP_NS = "http://cxf.apache.org/transports/http/configuration";

    @Override
    public void doParse(Element element, ParserContext ctc, BeanDefinitionBuilder bean) {
        bean.setAbstract(true);
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "server"), "server");
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "fixedParameterOrder"), "fixedParameterOrder");
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "contextMatchStrategy"), "contextMatchStrategy");
    }

    @Override
    protected String getJaxbPackage() {
        return "org.apache.cxf.transports.http.configuration";
    }

    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return AbstractHTTPDestination.class;
    }
}

