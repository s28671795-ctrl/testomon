/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import org.apache.cxf.Bus;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxws.handler.HandlerChainBuilder;
import org.apache.cxf.jaxws.handler.types.PortComponentHandlerType;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AnnotationHandlerChainBuilder
extends HandlerChainBuilder {
    private static final Logger LOG = LogUtils.getL7dLogger(AnnotationHandlerChainBuilder.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private static JAXBContext context;
    private ClassLoader classLoader;

    public AnnotationHandlerChainBuilder() {
    }

    public AnnotationHandlerChainBuilder(Bus bus) {
        super(bus);
    }

    public List<Handler> buildHandlerChainFromClass(Class<?> clz, List<Handler> existingHandlers, QName portQName, QName serviceQName, String bindingID) {
        LOG.fine("building handler chain");
        this.classLoader = AnnotationHandlerChainBuilder.getClassLoader(clz);
        HandlerChainAnnotation hcAnn = this.findHandlerChainAnnotation(clz, true);
        ArrayList<Handler<Object>> chain = null;
        if (hcAnn == null) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("no HandlerChain annotation on " + clz);
            }
            chain = new ArrayList<Handler>();
        } else {
            hcAnn.validate();
            try {
                URL handlerFileURL = this.resolveHandlerChainFile(clz, hcAnn.getFileName());
                if (handlerFileURL == null) {
                    throw new WebServiceException(new Message("HANDLER_CFG_FILE_NOT_FOUND_EXC", BUNDLE, hcAnn.getFileName()).toString());
                }
                Document doc = StaxUtils.read(handlerFileURL.openStream());
                Element el = doc.getDocumentElement();
                if (!"http://java.sun.com/xml/ns/javaee".equals(el.getNamespaceURI()) || !"handler-chains".equals(el.getLocalName())) {
                    String xml = StaxUtils.toString(el);
                    throw new WebServiceException(BundleUtils.getFormattedString(BUNDLE, "NOT_VALID_ROOT_ELEMENT", "http://java.sun.com/xml/ns/javaee".equals(el.getNamespaceURI()), "handler-chains".equals(el.getLocalName()), xml, handlerFileURL));
                }
                chain = new ArrayList();
                for (Node node = el.getFirstChild(); node != null; node = node.getNextSibling()) {
                    if (!(node instanceof Element)) continue;
                    el = (Element)node;
                    if (!"http://java.sun.com/xml/ns/javaee".equals(el.getNamespaceURI()) || !"handler-chain".equals(el.getLocalName())) {
                        String xml = StaxUtils.toString(el);
                        throw new WebServiceException(BundleUtils.getFormattedString(BUNDLE, "NOT_VALID_ELEMENT_IN_HANDLER", xml));
                    }
                    this.processHandlerChainElement(el, chain, portQName, serviceQName, bindingID);
                }
            }
            catch (WebServiceException e) {
                throw e;
            }
            catch (Exception e) {
                throw new WebServiceException(BUNDLE.getString("CHAIN_NOT_SPECIFIED_EXC"), e);
            }
        }
        assert (chain != null);
        if (existingHandlers != null) {
            chain.addAll(existingHandlers);
        }
        return this.sortHandlers(chain);
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    private void processHandlerChainElement(Element el, List<Handler> chain, QName portQName, QName serviceQName, String bindingID) {
        for (Node node = el.getFirstChild(); node != null; node = node.getNextSibling()) {
            Node cur = node;
            if (!(cur instanceof Element)) continue;
            el = (Element)cur;
            if (!"http://java.sun.com/xml/ns/javaee".equals(el.getNamespaceURI())) {
                String xml = StaxUtils.toString(el);
                throw new WebServiceException(BundleUtils.getFormattedString(BUNDLE, "NOT_VALID_ELEMENT_IN_HANDLER", xml));
            }
            String name = el.getLocalName();
            if ("port-name-pattern".equals(name)) {
                if (this.patternMatches(el, portQName)) continue;
                return;
            }
            if ("service-name-pattern".equals(name)) {
                if (this.patternMatches(el, serviceQName)) continue;
                return;
            }
            if (!"protocol-bindings".equals(name)) continue;
            if (this.protocolMatches(el, bindingID)) continue;
            return;
        }
    }

    private boolean protocolMatches(Element el, String id) {
        if (id == null) {
            return true;
        }
        String name = el.getTextContent().trim();
        StringTokenizer st = new StringTokenizer(name, " ", false);
        boolean result = false;
        while (st.hasMoreTokens() && !result) {
            result = result || this.singleProtocolMatches(st.nextToken(), id);
        }
        return result;
    }

    private boolean singleProtocolMatches(String name, String id) {
        if ("##SOAP11_HTTP".equals(name)) {
            return "http://schemas.xmlsoap.org/wsdl/soap/http".contains(id) || "http://schemas.xmlsoap.org/soap/".contains(id);
        }
        if ("##SOAP11_HTTP_MTOM".equals(name)) {
            return "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true".contains(id) || "http://schemas.xmlsoap.org/soap/?mtom=true".contains(id);
        }
        if ("##SOAP12_HTTP".equals(name)) {
            return "http://www.w3.org/2003/05/soap/bindings/HTTP/".contains(id) || "http://schemas.xmlsoap.org/wsdl/soap12/".contains(id);
        }
        if ("##SOAP12_HTTP_MTOM".equals(name)) {
            return "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true".contains(id) || "http://schemas.xmlsoap.org/wsdl/soap12/?mtom=true".contains(id);
        }
        if ("##XML_HTTP".equals(name)) {
            name = "http://www.w3.org/2004/08/wsdl/http";
        }
        return name.contains(id);
    }

    private boolean patternMatches(Element el, QName comp) {
        if (comp == null) {
            return true;
        }
        String namePattern = el.getTextContent().trim();
        if ("*".equals(namePattern)) {
            return true;
        }
        int idx = namePattern.indexOf(58);
        if (idx < 0) {
            String xml = StaxUtils.toString(el);
            throw new WebServiceException(BundleUtils.getFormattedString(BUNDLE, "NOT_A_QNAME_PATTER", namePattern, xml));
        }
        String pfx = namePattern.substring(0, idx);
        String ns = el.lookupNamespaceURI(pfx);
        if (ns == null) {
            ns = pfx;
        }
        if (!ns.equals(comp.getNamespaceURI())) {
            return false;
        }
        String localPart = namePattern.substring(idx + 1, namePattern.length());
        if (localPart.contains("*")) {
            return Pattern.matches(this.mapPattern(localPart), comp.getLocalPart());
        }
        return localPart.equals(comp.getLocalPart());
    }

    private String mapPattern(String s2) {
        StringBuilder buf = new StringBuilder(s2);
        block4: for (int x = 0; x < buf.length(); ++x) {
            switch (buf.charAt(x)) {
                case '*': {
                    buf.insert(x, '.');
                    ++x;
                    continue block4;
                }
                case '$': 
                case '(': 
                case ')': 
                case '.': 
                case '\\': 
                case '^': 
                case '{': 
                case '}': {
                    buf.insert(x, '\\');
                    ++x;
                    continue block4;
                }
            }
        }
        return buf.toString();
    }

    private void processHandlerElement(Element el, List<Handler> chain) {
        try {
            JAXBContext ctx = AnnotationHandlerChainBuilder.getContextForPortComponentHandlerType();
            PortComponentHandlerType pt = JAXBUtils.unmarshall(ctx, el, PortComponentHandlerType.class).getValue();
            chain.addAll(this.buildHandlerChain(pt, this.classLoader));
        }
        catch (JAXBException e) {
            throw new IllegalArgumentException("Could not unmarshal handler chain", e);
        }
    }

    private static synchronized JAXBContext getContextForPortComponentHandlerType() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(PortComponentHandlerType.class);
        }
        return context;
    }

    public List<Handler> buildHandlerChainFromClass(Class<?> clz, QName portQName, QName serviceQName, String bindingID) {
        return this.buildHandlerChainFromClass(clz, null, portQName, serviceQName, bindingID);
    }

    protected URL resolveHandlerChainAnnotationFile(Class<?> clazz, String name) {
        return clazz.getResource(name);
    }

    private HandlerChainAnnotation findHandlerChainAnnotation(Class<?> clz, boolean searchSEI) {
        if (clz == null) {
            return null;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Checking for HandlerChain annotation on " + clz.getName());
        }
        HandlerChainAnnotation hcAnn = null;
        HandlerChain ann = clz.getAnnotation(HandlerChain.class);
        if (ann == null) {
            WebService ws;
            if (searchSEI && (ws = clz.getAnnotation(WebService.class)) != null && !StringUtils.isEmpty(ws.endpointInterface())) {
                String seiClassName = ws.endpointInterface().trim();
                Class<?> seiClass = null;
                try {
                    seiClass = ClassLoaderUtils.loadClass(seiClassName, clz);
                }
                catch (ClassNotFoundException e) {
                    throw new WebServiceException(BUNDLE.getString("SEI_LOAD_FAILURE_EXC"), e);
                }
                hcAnn = this.findHandlerChainAnnotation(seiClass, false);
            }
            if (hcAnn == null) {
                for (Class<?> iface : clz.getInterfaces()) {
                    if (LOG.isLoggable(Level.FINE)) {
                        LOG.fine("Checking for HandlerChain annotation on " + iface.getName());
                    }
                    if ((ann = iface.getAnnotation(HandlerChain.class)) == null) continue;
                    hcAnn = new HandlerChainAnnotation(ann, iface);
                    break;
                }
                if (hcAnn == null) {
                    hcAnn = this.findHandlerChainAnnotation(clz.getSuperclass(), false);
                }
            }
        } else {
            hcAnn = new HandlerChainAnnotation(ann, clz);
        }
        return hcAnn;
    }

    static class HandlerChainAnnotation {
        private final Class<?> declaringClass;
        private final HandlerChain ann;

        HandlerChainAnnotation(HandlerChain hc, Class<?> clz) {
            this.ann = hc;
            this.declaringClass = clz;
        }

        public Class<?> getDeclaringClass() {
            return this.declaringClass;
        }

        public String getFileName() {
            return this.ann.file();
        }

        public void validate() {
            if (null == this.ann.file() || "".equals(this.ann.file())) {
                throw new WebServiceException(BUNDLE.getString("ANNOTATION_WITHOUT_URL_EXC"));
            }
        }

        public String toString() {
            return "[" + this.declaringClass + "," + this.ann + "]";
        }
    }
}

