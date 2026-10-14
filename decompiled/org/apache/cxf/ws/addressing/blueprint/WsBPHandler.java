/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.NamespaceHandler
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.ws.addressing.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.configuration.blueprint.SimpleBPBeanDefinitionParser;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WsBPHandler
implements NamespaceHandler {
    public URL getSchemaLocation(String s2) {
        if ("http://cxf.apache.org/ws/addressing".equals(s2)) {
            return this.getClass().getClassLoader().getResource("schemas/ws-addr-conf.xsd");
        }
        return null;
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public Metadata parse(Element element, ParserContext context) {
        return new SimpleBPBeanDefinitionParser(WSAddressingFeature.class).parse(element, context);
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata componentMetadata, ParserContext context) {
        return null;
    }
}

