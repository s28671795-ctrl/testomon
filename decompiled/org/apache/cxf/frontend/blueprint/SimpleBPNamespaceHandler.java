/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.Namespaces
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.container.BlueprintContainer
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.frontend.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.Namespaces;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.frontend.blueprint.ClientProxyFactoryBeanDefinitionParser;
import org.apache.cxf.frontend.blueprint.ServerFactoryBeanDefinitionParser;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Namespaces(value={"http://cxf.apache.org/blueprint/simple"})
public class SimpleBPNamespaceHandler
extends BaseNamespaceHandler {
    private BlueprintContainer blueprintContainer;

    public URL getSchemaLocation(String namespace) {
        if ("http://cxf.apache.org/blueprint/simple".equals(namespace)) {
            return this.getClass().getClassLoader().getResource("schemas/blueprint/simple.xsd");
        }
        return super.findCoreSchemaLocation(namespace);
    }

    public Metadata parse(Element element, ParserContext context) {
        String s2 = element.getLocalName();
        if ("server".equals(s2)) {
            return new ServerFactoryBeanDefinitionParser().parse(element, context);
        }
        if ("client".equals(s2)) {
            return new ClientProxyFactoryBeanDefinitionParser().parse(element, context);
        }
        return null;
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata component, ParserContext context) {
        return null;
    }

    public BlueprintContainer getBlueprintContainer() {
        return this.blueprintContainer;
    }

    public void setBlueprintContainer(BlueprintContainer blueprintContainer) {
        this.blueprintContainer = blueprintContainer;
    }
}

