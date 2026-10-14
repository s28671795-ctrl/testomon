/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.transport.http.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.configuration.jsse.TLSClientParametersConfig;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CipherSuites;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TLSClientParametersType;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class HttpConduitBPBeanDefinitionParser
extends AbstractBPBeanDefinitionParser {
    private static final String SECURITY_NS = "http://cxf.apache.org/configuration/security";

    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata bean = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        bean.setRuntimeClass(HTTPConduit.class);
        this.parseAttributes(element, context, bean);
        this.parseChildElements(element, context, bean);
        bean.setScope("prototype");
        return bean;
    }

    @Override
    protected void processNameAttribute(Element element, ParserContext context, MutableBeanMetadata bean, String val) {
        bean.setId(val);
    }

    @Override
    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
        if ("tlsClientParameters".equals(name)) {
            this.mapTLSClientParameters(ctx, bean, el);
        } else if ("trustDecider".equals(name)) {
            this.mapBeanOrClassElement(ctx, bean, el, MessageTrustDecider.class);
        } else if ("authSupplier".equals(name)) {
            this.mapBeanOrClassElement(ctx, bean, el, HttpAuthSupplier.class);
        } else if ("client".equals(name)) {
            this.mapElementToJaxbProperty(ctx, bean, el, name, HTTPClientPolicy.class);
        } else if ("proxyAuthorization".equals(name)) {
            this.mapElementToJaxbProperty(ctx, bean, el, name, ProxyAuthorizationPolicy.class);
        } else if ("authorization".equals(name)) {
            this.mapElementToJaxbProperty(ctx, bean, el, name, AuthorizationPolicy.class);
        }
    }

    private void mapTLSClientParameters(ParserContext ctx, MutableBeanMetadata bean, Element el) {
        MutableBeanMetadata paramsbean = (MutableBeanMetadata)ctx.createMetadata(MutableBeanMetadata.class);
        paramsbean.setRuntimeClass(TLSClientParametersConfig.TLSClientParametersTypeInternal.class);
        NamedNodeMap as = el.getAttributes();
        for (int i = 0; i < as.getLength(); ++i) {
            String aname;
            Attr a = (Attr)as.item(i);
            if (a.getNamespaceURI() != null || !"useHttpsURLConnectionDefaultSslSocketFactory".equals(aname = a.getLocalName()) && !"useHttpsURLConnectionDefaultHostnameVerifier".equals(aname) && !"disableCNCheck".equals(aname) && !"enableRevocation".equals(aname) && !"jsseProvider".equals(aname) && !"secureSocketProtocol".equals(aname) && !"sslCacheTimeout".equals(aname)) continue;
            paramsbean.addProperty(aname, (Metadata)HttpConduitBPBeanDefinitionParser.createValue(ctx, a.getValue()));
        }
        Node n = el.getFirstChild();
        while (n != null) {
            if (1 != n.getNodeType() || !SECURITY_NS.equals(n.getNamespaceURI())) {
                n = n.getNextSibling();
                continue;
            }
            String ename = n.getLocalName();
            String ref = ((Element)n).getAttribute("ref");
            if ("keyManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addProperty("keyManagersRef", (Metadata)HttpConduitBPBeanDefinitionParser.createRef(ctx, ref));
                } else {
                    this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, KeyManagersType.class);
                }
            } else if ("trustManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addProperty("trustManagersRef", (Metadata)HttpConduitBPBeanDefinitionParser.createRef(ctx, ref));
                } else {
                    this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, TrustManagersType.class);
                }
            } else if ("cipherSuites".equals(ename)) {
                this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, CipherSuites.class);
            } else if ("cipherSuitesFilter".equals(ename)) {
                this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, FiltersType.class);
            } else if ("secureRandomParameters".equals(ename)) {
                this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, SecureRandomParameters.class);
            } else if ("certConstraints".equals(ename)) {
                this.mapElementToJaxbProperty(ctx, paramsbean, (Element)n, ename, CertificateConstraintsType.class);
            } else if ("certAlias".equals(ename)) {
                paramsbean.addProperty(ename, (Metadata)HttpConduitBPBeanDefinitionParser.createValue(ctx, n.getTextContent()));
            }
            n = n.getNextSibling();
        }
        MutableBeanMetadata jaxbbean = (MutableBeanMetadata)ctx.createMetadata(MutableBeanMetadata.class);
        jaxbbean.setRuntimeClass(TLSClientParametersConfig.class);
        jaxbbean.setFactoryMethod("createTLSClientParametersFromType");
        jaxbbean.addArgument((Metadata)paramsbean, TLSClientParametersType.class.getName(), 0);
        bean.addProperty("tlsClientParameters", (Metadata)jaxbbean);
    }

    private void mapBeanOrClassElement(ParserContext ctx, MutableBeanMetadata bean, Element el, Class<?> cls) {
        String elementName = el.getLocalName();
        String classProperty = el.getAttribute("class");
        String beanref = el.getAttribute("bean");
        if (classProperty != null && !classProperty.isEmpty()) {
            bean.addProperty(elementName, (Metadata)HttpConduitBPBeanDefinitionParser.createObjectOfClass(ctx, classProperty));
        } else if (beanref != null && !beanref.isEmpty()) {
            bean.addProperty(elementName, (Metadata)HttpConduitBPBeanDefinitionParser.createRef(ctx, beanref));
        }
    }
}

