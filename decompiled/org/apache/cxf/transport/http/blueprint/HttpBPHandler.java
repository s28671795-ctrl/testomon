/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.Namespaces
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.transport.http.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.Namespaces;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.apache.cxf.transport.http.blueprint.HttpConduitBPBeanDefinitionParser;
import org.apache.cxf.transport.http.blueprint.HttpDestinationBPBeanDefinitionParser;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Namespaces(value={"http://cxf.apache.org/transports/http/configuration"})
public class HttpBPHandler
extends BaseNamespaceHandler {
    public URL getSchemaLocation(String s2) {
        if ("http://cxf.apache.org/transports/http/configuration".equals(s2)) {
            return this.getClass().getClassLoader().getResource("schemas/configuration/http-conf.xsd");
        }
        return super.findCoreSchemaLocation(s2);
    }

    public Metadata parse(Element element, ParserContext context) {
        String s2 = element.getLocalName();
        if ("conduit".equals(s2)) {
            return new HttpConduitBPBeanDefinitionParser().parse(element, context);
        }
        if ("destination".equals(s2)) {
            return new HttpDestinationBPBeanDefinitionParser().parse(element, context);
        }
        return null;
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata component, ParserContext context) {
        return null;
    }
}

