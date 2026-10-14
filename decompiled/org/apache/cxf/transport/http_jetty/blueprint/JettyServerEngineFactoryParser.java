/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.apache.aries.blueprint.mutable.MutableMapMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 *  org.osgi.service.blueprint.reflect.NonNullMetadata
 *  org.osgi.service.blueprint.reflect.ValueMetadata
 */
package org.apache.cxf.transport.http_jetty.blueprint;

import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutableMapMetadata;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.transport.http_jetty.blueprint.JettyHTTPServerEngineFactoryHolder;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.NonNullMetadata;
import org.osgi.service.blueprint.reflect.ValueMetadata;
import org.w3c.dom.Element;

public class JettyServerEngineFactoryParser
extends AbstractBPBeanDefinitionParser {
    public static final String JETTY_TRANSPORT = "http://cxf.apache.org/transports/http-jetty/configuration";
    public static final String JETTY_THREADING = "http://cxf.apache.org/configuration/parameterized-types";

    public static String getIdOrName(Element elem) {
        StringTokenizer st;
        String names;
        String id = elem.getAttribute("id");
        if ((null == id || "".equals(id)) && null != (names = elem.getAttribute("name")) && (st = new StringTokenizer(names, ",")).countTokens() > 0) {
            id = st.nextToken();
        }
        return id;
    }

    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata ef = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        if (!StringUtils.isEmpty(JettyServerEngineFactoryParser.getIdOrName(element))) {
            ef.setId(JettyServerEngineFactoryParser.getIdOrName(element));
        } else {
            ef.setId("jetty.engine.factory-holder-" + UUID.randomUUID().toString());
        }
        ef.setRuntimeClass(JettyHTTPServerEngineFactoryHolder.class);
        try {
            String xmlString = StaxUtils.toString(element);
            ef.addProperty("parsedElement", (Metadata)JettyServerEngineFactoryParser.createValue(context, xmlString));
            ef.setInitMethod("init");
            ef.setActivation(1);
            ef.setDestroyMethod("destroy");
            List<Element> engines = DOMUtils.getChildrenWithName(element, JETTY_TRANSPORT, "engine");
            ef.addProperty("connectorMap", this.parseEngineConnector(engines, (ComponentMetadata)ef, context));
            ef.addProperty("handlersMap", this.parseEngineHandlers(engines, (ComponentMetadata)ef, context));
            return ef;
        }
        catch (Exception e) {
            throw new RuntimeException("Could not process configuration.", e);
        }
    }

    protected Metadata parseEngineConnector(List<Element> engines, ComponentMetadata enclosingComponent, ParserContext context) {
        MutableMapMetadata map = (MutableMapMetadata)context.createMetadata(MutableMapMetadata.class);
        map.setKeyType("java.lang.String");
        map.setValueType("org.eclipse.jetty.server.Connector");
        for (Element engine : engines) {
            String port = engine.getAttribute("port");
            ValueMetadata keyValue = JettyServerEngineFactoryParser.createValue(context, port);
            Element connector = DOMUtils.getFirstChildWithName(engine, JETTY_TRANSPORT, "connector");
            if (connector == null) continue;
            Element first = DOMUtils.getFirstElement(connector);
            Metadata valValue = (Metadata)context.parseElement(Metadata.class, enclosingComponent, first);
            map.addEntry((NonNullMetadata)keyValue, valValue);
        }
        return map;
    }

    protected Metadata parseEngineHandlers(List<Element> engines, ComponentMetadata enclosingComponent, ParserContext context) {
        MutableMapMetadata map = (MutableMapMetadata)context.createMetadata(MutableMapMetadata.class);
        map.setKeyType("java.lang.String");
        map.setValueType("java.util.List");
        for (Element engine : engines) {
            String port = engine.getAttribute("port");
            ValueMetadata keyValue = JettyServerEngineFactoryParser.createValue(context, port);
            Element handlers = DOMUtils.getFirstChildWithName(engine, JETTY_TRANSPORT, "handlers");
            if (handlers == null) continue;
            Metadata valValue = this.parseListData(context, enclosingComponent, handlers);
            map.addEntry((NonNullMetadata)keyValue, valValue);
        }
        return map;
    }
}

