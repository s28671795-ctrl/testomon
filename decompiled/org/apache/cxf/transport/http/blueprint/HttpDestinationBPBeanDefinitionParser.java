/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.transport.http.blueprint;

import javax.xml.namespace.QName;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class HttpDestinationBPBeanDefinitionParser
extends AbstractBPBeanDefinitionParser {
    private static final String HTTP_NS = "http://cxf.apache.org/transports/http/configuration";

    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata bean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        bean.setRuntimeClass(AbstractHTTPDestination.class);
        this.mapElementToJaxbProperty(context, bean, element, new QName(HTTP_NS, "server"), "server", HTTPServerPolicy.class);
        this.mapElementToJaxbProperty(context, bean, element, new QName(HTTP_NS, "fixedParameterOrder"), "fixedParameterOrder", Boolean.class);
        this.mapElementToJaxbProperty(context, bean, element, new QName(HTTP_NS, "contextMatchStrategy"), "contextMatchStrategy", String.class);
        this.parseAttributes(element, context, bean);
        this.parseChildElements(element, context, bean);
        bean.setScope("prototype");
        return bean;
    }

    @Override
    protected void processNameAttribute(Element element, ParserContext context, MutableBeanMetadata bean, String val) {
        bean.setId(val);
    }
}

