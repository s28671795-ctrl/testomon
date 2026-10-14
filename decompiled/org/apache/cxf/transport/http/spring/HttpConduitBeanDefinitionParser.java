/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.transport.http.spring;

import javax.xml.namespace.QName;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.configuration.jsse.TLSClientParametersConfig;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CipherSuites;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class HttpConduitBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    private static final String HTTP_NS = "http://cxf.apache.org/transports/http/configuration";
    private static final String SECURITY_NS = "http://cxf.apache.org/configuration/security";

    @Override
    public void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        bean.setAbstract(true);
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "client"), "client", HTTPClientPolicy.class);
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "proxyAuthorization"), "proxyAuthorization", ProxyAuthorizationPolicy.class);
        this.mapElementToJaxbProperty(element, bean, new QName(HTTP_NS, "authorization"), "authorization", AuthorizationPolicy.class);
        this.mapSpecificElements(element, bean);
    }

    private void mapSpecificElements(Element parent, BeanDefinitionBuilder bean) {
        Node n = parent.getFirstChild();
        while (n != null) {
            if (1 != n.getNodeType() || !HTTP_NS.equals(n.getNamespaceURI())) {
                n = n.getNextSibling();
                continue;
            }
            String elementName = n.getLocalName();
            if ("trustDecider".equals(elementName)) {
                this.mapBeanOrClassElement((Element)n, bean, MessageTrustDecider.class);
            } else if ("authSupplier".equals(elementName)) {
                this.mapBeanOrClassElement((Element)n, bean, HttpAuthSupplier.class);
            } else if ("basicAuthSupplier".equals(elementName)) {
                this.mapBeanOrClassElement((Element)n, bean, HttpAuthSupplier.class);
            } else if ("tlsClientParameters".equals(elementName)) {
                this.mapTLSClientParameters((Element)n, bean);
            }
            n = n.getNextSibling();
        }
    }

    public void mapTLSClientParameters(Element e, BeanDefinitionBuilder bean) {
        BeanDefinitionBuilder paramsbean = BeanDefinitionBuilder.rootBeanDefinition(TLSClientParametersConfig.TLSClientParametersTypeInternal.class);
        NamedNodeMap as = e.getAttributes();
        for (int i = 0; i < as.getLength(); ++i) {
            String aname;
            Attr a = (Attr)as.item(i);
            if (a.getNamespaceURI() != null || !"useHttpsURLConnectionDefaultSslSocketFactory".equals(aname = a.getLocalName()) && !"useHttpsURLConnectionDefaultHostnameVerifier".equals(aname) && !"disableCNCheck".equals(aname) && !"enableRevocation".equals(aname) && !"jsseProvider".equals(aname) && !"secureSocketProtocol".equals(aname) && !"sslCacheTimeout".equals(aname)) continue;
            paramsbean.addPropertyValue(aname, (Object)a.getValue());
        }
        Node n = e.getFirstChild();
        while (n != null) {
            if (1 != n.getNodeType() || !SECURITY_NS.equals(n.getNamespaceURI())) {
                n = n.getNextSibling();
                continue;
            }
            String ename = n.getLocalName();
            String ref = ((Element)n).getAttribute("ref");
            if ("keyManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addPropertyReference("keyManagersRef", ref);
                } else {
                    this.mapElementToJaxbProperty((Element)n, paramsbean, ename, KeyManagersType.class);
                }
            } else if ("trustManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addPropertyReference("trustManagersRef", ref);
                } else {
                    this.mapElementToJaxbProperty((Element)n, paramsbean, ename, TrustManagersType.class);
                }
            } else if ("cipherSuites".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, CipherSuites.class);
            } else if ("cipherSuitesFilter".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, FiltersType.class);
            } else if ("secureRandomParameters".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, SecureRandomParameters.class);
            } else if ("certConstraints".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, CertificateConstraintsType.class);
            } else if ("certAlias".equals(ename)) {
                paramsbean.addPropertyValue(ename, (Object)n.getTextContent());
            }
            n = n.getNextSibling();
        }
        BeanDefinitionBuilder jaxbbean = BeanDefinitionBuilder.rootBeanDefinition(TLSClientParametersConfig.class);
        jaxbbean.getRawBeanDefinition().setFactoryMethodName("createTLSClientParametersFromType");
        jaxbbean.addConstructorArgValue((Object)paramsbean.getBeanDefinition());
        bean.addPropertyValue("tlsClientParameters", (Object)jaxbbean.getBeanDefinition());
    }

    protected void mapBeanOrClassElement(Element element, BeanDefinitionBuilder bean, Class<?> elementClass) {
        String beanref;
        String elementName = element.getLocalName();
        String classProperty = element.getAttribute("class");
        if (classProperty != null && !classProperty.isEmpty()) {
            try {
                Object obj = ClassLoaderUtils.loadClass(classProperty, ((Object)((Object)this)).getClass()).newInstance();
                if (!elementClass.isInstance(obj)) {
                    throw new IllegalArgumentException("Element '" + elementName + "' must be of type " + elementClass.getName() + ".");
                }
                bean.addPropertyValue(elementName, obj);
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                throw new IllegalArgumentException("Element '" + elementName + "' could not load " + classProperty + " - " + ex);
            }
        }
        if ((beanref = element.getAttribute("bean")) != null && !beanref.isEmpty()) {
            if (classProperty != null && !classProperty.isEmpty()) {
                throw new IllegalArgumentException("Element '" + elementName + "' cannot have both \"bean\" and \"class\" attributes.");
            }
            bean.addPropertyReference(elementName, beanref);
        } else if (classProperty == null || classProperty.isEmpty()) {
            throw new IllegalArgumentException("Element '" + elementName + "' requires at least one of the \"bean\" or \"class\" attributes.");
        }
    }

    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return HTTPConduit.class;
    }
}

