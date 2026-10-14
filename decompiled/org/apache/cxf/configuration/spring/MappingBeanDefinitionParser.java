/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.xml.AbstractBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.configuration.spring;

import java.util.Collections;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.staxutils.transform.OutTransformWriter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class MappingBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    private final Map<String, String> transformMap;

    public MappingBeanDefinitionParser(String oldns, String newns) {
        this.transformMap = Collections.singletonMap("{" + oldns + "}*", "{" + newns + "}*");
    }

    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        return (AbstractBeanDefinition)parserContext.getDelegate().parseCustomElement(this.transformElement(element));
    }

    private Element transformElement(Element element) {
        W3CDOMStreamWriter domWriter = new W3CDOMStreamWriter();
        OutTransformWriter transformWriter = new OutTransformWriter(domWriter, this.transformMap);
        try {
            StaxUtils.copy(element, (XMLStreamWriter)transformWriter);
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return domWriter.getDocument().getDocumentElement();
    }
}

