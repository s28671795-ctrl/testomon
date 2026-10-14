/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.jaxws.blueprint;

import java.util.StringTokenizer;
import java.util.UUID;
import javax.xml.namespace.QName;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.jaxws.EndpointImpl;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

class EndpointDefinitionParser
extends AbstractBPBeanDefinitionParser {
    private static final Class<?> EP_CLASS = EndpointImpl.class;

    EndpointDefinitionParser() {
    }

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
        MutableBeanMetadata cxfBean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        if (!StringUtils.isEmpty(EndpointDefinitionParser.getIdOrName(element))) {
            cxfBean.setId(EndpointDefinitionParser.getIdOrName(element));
        } else {
            cxfBean.setId("cxf.endpoint." + UUID.randomUUID().toString());
        }
        cxfBean.setRuntimeClass(EP_CLASS);
        boolean isAbstract = false;
        boolean publish = true;
        NamedNodeMap atts = element.getAttributes();
        String bus = null;
        Metadata impl = null;
        for (int i = 0; i < atts.getLength(); ++i) {
            Attr node = (Attr)atts.item(i);
            String val = node.getValue();
            String pre = node.getPrefix();
            String name = node.getLocalName();
            if ("createdFromAPI".equals(name) || "abstract".equals(name)) {
                cxfBean.setScope("prototype");
                isAbstract = true;
                continue;
            }
            if ("publish".equals(name)) {
                publish = Boolean.parseBoolean(val);
                continue;
            }
            if ("bus".equals(name)) {
                bus = val;
                continue;
            }
            if (!this.isAttribute(pre, name)) continue;
            if ("endpointName".equals(name) || "serviceName".equals(name)) {
                QName q = this.parseQName(element, val);
                cxfBean.addProperty(name, this.createValue(context, q));
                continue;
            }
            if ("depends-on".equals(name)) {
                cxfBean.addDependsOn(val);
                continue;
            }
            if ("implementor".equals(name)) {
                if (val.startsWith("#")) {
                    impl = EndpointDefinitionParser.createRef(context, val.substring(1));
                    continue;
                }
                impl = EndpointDefinitionParser.createObjectOfClass(context, val);
                continue;
            }
            if ("name".equals(name)) continue;
            cxfBean.addProperty(name, (Metadata)EndpointDefinitionParser.createValue(context, val));
        }
        Element elem = DOMUtils.getFirstElement(element);
        while (elem != null) {
            String name = elem.getLocalName();
            if ("properties".equals(name)) {
                Metadata map = this.parseMapData(context, (ComponentMetadata)cxfBean, elem);
                cxfBean.addProperty(name, map);
            } else if ("binding".equals(name)) {
                this.setFirstChildAsProperty(elem, context, cxfBean, "bindingConfig");
            } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "schemaLocations".equals(name) || "handlers".equals(name)) {
                Metadata list = this.parseListData(context, (ComponentMetadata)cxfBean, elem);
                cxfBean.addProperty(name, list);
            } else if ("implementor".equals(name)) {
                impl = (Metadata)context.parseElement(Metadata.class, (ComponentMetadata)cxfBean, elem);
            } else {
                this.setFirstChildAsProperty(elem, context, cxfBean, name);
            }
            elem = DOMUtils.getNextElement(elem);
        }
        if (StringUtils.isEmpty(bus)) {
            bus = "cxf";
        }
        cxfBean.addArgument((Metadata)this.getBusRef(context, bus), Bus.class.getName(), 0);
        cxfBean.addArgument(impl, Object.class.getName(), 1);
        if (!isAbstract) {
            if (publish) {
                cxfBean.setInitMethod("publish");
            }
            cxfBean.setDestroyMethod("stop");
        }
        cxfBean.setActivation(1);
        return cxfBean;
    }
}

