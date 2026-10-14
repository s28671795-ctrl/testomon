/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.Namespaces
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.transport.http_jetty.blueprint;

import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.aries.blueprint.Namespaces;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.apache.cxf.transport.http_jetty.blueprint.JettyServerEngineFactoryParser;
import org.apache.cxf.transport.http_jetty.blueprint.JettyServerEngineParser;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Namespaces(value={"http://cxf.apache.org/transports/http-jetty/configuration"})
public class HTTPJettyTransportNamespaceHandler
extends BaseNamespaceHandler {
    public static final String JETTY_TRANSPORT = "http://cxf.apache.org/transports/http-jetty/configuration";
    private static final String JETTY_ENGINE = "engine";
    private static final String JETTY_ENGINE_FACTORY = "engine-factory";
    private static final Logger LOG = LogUtils.getL7dLogger(HTTPJettyTransportNamespaceHandler.class);

    public URL getSchemaLocation(String s2) {
        if (JETTY_TRANSPORT.equals(s2)) {
            return this.getClass().getClassLoader().getResource("schemas/configuration/http-jetty.xsd");
        }
        return super.findCoreSchemaLocation(s2);
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public Metadata parse(Element element, ParserContext parserContext) {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Parsing element {{" + element.getNamespaceURI() + "}}{" + element.getLocalName() + "}");
        }
        if (JETTY_ENGINE.equals(element.getLocalName())) {
            return new JettyServerEngineParser().parse(element, parserContext);
        }
        if (JETTY_ENGINE_FACTORY.equals(element.getLocalName())) {
            return new JettyServerEngineFactoryParser().parse(element, parserContext);
        }
        return null;
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata componentMetadata, ParserContext parserContext) {
        LOG.info("Decorating node " + node + " " + componentMetadata);
        return componentMetadata;
    }
}

