/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.binding.soap.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.binding.soap.blueprint.SoapBindingBPInfoConfigDefinitionParser;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SoapBindingBPHandler
extends BaseNamespaceHandler {
    public URL getSchemaLocation(String s2) {
        if ("http://cxf.apache.org/blueprint/bindings/soap".equals(s2)) {
            return this.getClass().getClassLoader().getResource("schemas/configuration/blueprint/soap.xsd");
        }
        return super.findCoreSchemaLocation(s2);
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public Metadata parse(Element element, ParserContext context) {
        return new SoapBindingBPInfoConfigDefinitionParser().parse(element, context);
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata componentMetadata, ParserContext context) {
        return null;
    }
}

