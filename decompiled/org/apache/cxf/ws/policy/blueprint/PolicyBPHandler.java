/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.ws.policy.blueprint;

import java.net.URL;
import java.util.Set;
import org.apache.aries.blueprint.ParserContext;
import org.apache.cxf.helpers.BaseNamespaceHandler;
import org.apache.cxf.ws.policy.blueprint.ExternalAttachmentProviderBPDefinitionParser;
import org.apache.cxf.ws.policy.blueprint.PolicyEngineBPDefinitionParser;
import org.apache.cxf.ws.policy.blueprint.PolicyFeatureBPDefinitionParser;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PolicyBPHandler
extends BaseNamespaceHandler {
    public URL getSchemaLocation(String s2) {
        String location = null;
        if ("http://cxf.apache.org/policy".equals(s2)) {
            location = "schemas/policy.xsd";
        } else if ("http://www.w3.org/ns/ws-policy".equals(s2)) {
            location = "schemas/ws-policy-200702.xsd";
        } else if ("http://www.w3.org/2006/07/ws-policy".equals(s2)) {
            location = "schemas/ws-policy-200607.xsd";
        } else if ("http://schemas.xmlsoap.org/ws/2004/09/policy".equals(s2)) {
            location = "schemas/ws-policy-200409.xsd";
        } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(s2)) {
            location = "schemas/oasis-200401-wss-wssecurity-secext-1.0.xsd";
        } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(s2)) {
            location = "schemas/oasis-200401-wss-wssecurity-utility-1.0.xsd";
        } else if ("http://www.w3.org/2000/09/xmldsig#".equals(s2)) {
            location = "schemas/xmldsig-core-schema.xsd";
        } else if ("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702".equals(s2)) {
            location = "schemas/ws-securitypolicy-1.2.xsd";
        }
        if (location != null) {
            return this.getClass().getClassLoader().getResource(location);
        }
        return super.findCoreSchemaLocation(s2);
    }

    public Set<Class> getManagedClasses() {
        return null;
    }

    public Metadata parse(Element element, ParserContext context) {
        String s2 = element.getLocalName();
        if ("policies".equals(s2)) {
            return new PolicyFeatureBPDefinitionParser().parse(element, context);
        }
        if ("engine".equals(s2)) {
            return new PolicyEngineBPDefinitionParser().parse(element, context);
        }
        if ("externalAttachment".equals(s2)) {
            return new ExternalAttachmentProviderBPDefinitionParser().parse(element, context);
        }
        return null;
    }

    public ComponentMetadata decorate(Node node, ComponentMetadata componentMetadata, ParserContext context) {
        return null;
    }
}

