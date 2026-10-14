/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.NamespaceHandler
 *  org.apache.aries.blueprint.Namespaces
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.internal;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.aries.blueprint.Namespaces;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.bus.blueprint.BusDefinitionParser;
import org.apache.cxf.configuration.blueprint.SimpleBPBeanDefinitionParser;
import org.apache.cxf.feature.FastInfosetFeature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.workqueue.AutomaticWorkQueueImpl;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Namespaces(value={"http://cxf.apache.org/blueprint/core", "http://cxf.apache.org/configuration/beans", "http://cxf.apache.org/configuration/parameterized-types", "http://cxf.apache.org/configuration/security", "http://schemas.xmlsoap.org/wsdl/", "http://www.w3.org/2005/08/addressing", "http://schemas.xmlsoap.org/ws/2004/08/addressing"})
public class CXFAPINamespaceHandler
implements NamespaceHandler {
    public URL getSchemaLocation(String namespace) {
        String location = null;
        if ("http://cxf.apache.org/configuration/beans".equals(namespace) || "http://cxf.apache.org/schemas/configuration/cxf-beans.xsd".equals(namespace)) {
            location = "schemas/configuration/cxf-beans.xsd";
        } else if ("http://cxf.apache.org/configuration/parameterized-types".equals(namespace) || "http://cxf.apache.org/schemas/configuration/parameterized-types.xsd".equals(namespace)) {
            location = "schemas/configuration/parameterized-types.xsd";
        } else if ("http://cxf.apache.org/configuration/security".equals(namespace) || "http://cxf.apache.org/schemas/configuration/security.xsd".equals(namespace)) {
            location = "schemas/configuration/security.xsd";
        } else if ("http://schemas.xmlsoap.org/wsdl/".equals(namespace) || "http://schemas.xmlsoap.org/wsdl/2003-02-11.xsd".equals(namespace)) {
            location = "schemas/wsdl/wsdl.xsd";
        } else if ("http://www.w3.org/2005/08/addressing".equals(namespace) || "http://www.w3.org/2006/03/addressing/ws-addr.xsd".equals(namespace)) {
            location = "schemas/wsdl/ws-addr.xsd";
        } else if ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(namespace)) {
            location = "schemas/wsdl/addressing.xsd";
        } else if ("http://cxf.apache.org/blueprint/core".equals(namespace)) {
            location = "schemas/blueprint/core.xsd";
        }
        if (location != null) {
            return this.getClass().getClassLoader().getResource(location);
        }
        return null;
    }

    public Metadata parse(Element element, ParserContext context) {
        String s2 = element.getLocalName();
        if ("bus".equals(s2)) {
            return new BusDefinitionParser().parse(element, context);
        }
        if ("logging".equals(s2)) {
            return new SimpleBPBeanDefinitionParser(LoggingFeature.class).parse(element, context);
        }
        if ("fastinfoset".equals(s2)) {
            return new SimpleBPBeanDefinitionParser(FastInfosetFeature.class).parse(element, context);
        }
        if ("workqueue".equals(s2)) {
            return new SimpleBPBeanDefinitionParser(AutomaticWorkQueueImpl.class){

                @Override
                public String getId(Element element, ParserContext context) {
                    String id;
                    String string = id = element.hasAttribute("id") ? element.getAttribute("id") : null;
                    if (id == null) {
                        id = "cxf.workqueue.";
                        id = id + (element.hasAttribute("name") ? element.getAttribute("name") : "def");
                    }
                    return id;
                }

                @Override
                protected void processNameAttribute(Element element, ParserContext ctx, MutableBeanMetadata bean, String val) {
                    bean.addProperty("name", (Metadata)1.createValue(ctx, val));
                }
            }.parse(element, context);
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

