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
package org.apache.cxf.jaxws.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.Namespaces;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.blueprint.ClientProxyFactoryBeanDefinitionParser;
import org.apache.cxf.frontend.blueprint.ServerFactoryBeanDefinitionParser;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.blueprint.EndpointDefinitionParser;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Namespaces(value={"http://cxf.apache.org/blueprint/jaxws"})
public class JAXWSBPNamespaceHandler
extends BaseNamespaceHandler {
    private BlueprintContainer blueprintContainer;

    public URL getSchemaLocation(String namespace) {
        if ("http://cxf.apache.org/blueprint/jaxws".equals(namespace)) {
            return this.getClass().getClassLoader().getResource("schemas/blueprint/jaxws.xsd");
        }
        return super.findCoreSchemaLocation(namespace);
    }

    public Metadata parse(Element element, ParserContext context) {
        String s2 = element.getLocalName();
        if ("endpoint".equals(s2)) {
            return new EndpointDefinitionParser().parse(element, context);
        }
        if ("server".equals(s2)) {
            return new ServerFactoryBeanDefinitionParser(BPJaxWsServerFactoryBean.class).parse(element, context);
        }
        if ("client".equals(s2)) {
            return new ClientProxyFactoryBeanDefinitionParser(JaxWsProxyFactoryBean.class).parse(element, context);
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

    @NoJSR250Annotations
    public static class BPJaxWsServerFactoryBean
    extends JaxWsServerFactoryBean {
        private Server server;

        public BPJaxWsServerFactoryBean() {
        }

        public BPJaxWsServerFactoryBean(JaxWsServiceFactoryBean fact) {
            super(fact);
        }

        @Override
        public Server getServer() {
            return this.server;
        }

        public void init() {
            this.create();
        }

        @Override
        public Server create() {
            if (this.server == null) {
                this.server = super.create();
            }
            return this.server;
        }

        public void destroy() {
            if (this.server != null) {
                this.server.destroy();
                this.server = null;
            }
        }
    }
}

