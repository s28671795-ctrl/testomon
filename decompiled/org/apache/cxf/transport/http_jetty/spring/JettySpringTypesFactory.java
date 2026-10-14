/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty.spring;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.jsse.TLSServerParametersConfig;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@NoJSR250Annotations
public final class JettySpringTypesFactory {
    private static Map<String, ThreadingParameters> toThreadingParameters(List<ThreadingParametersIdentifiedType> list) {
        TreeMap<String, ThreadingParameters> map = new TreeMap<String, ThreadingParameters>();
        for (ThreadingParametersIdentifiedType t2 : list) {
            ThreadingParameters parameter = JettySpringTypesFactory.toThreadingParameters(t2.getThreadingParameters());
            map.put(t2.getId(), parameter);
        }
        return map;
    }

    private static ThreadingParameters toThreadingParameters(ThreadingParametersType paramtype) {
        ThreadingParameters params = new ThreadingParameters();
        params.setMaxThreads(paramtype.getMaxThreads());
        params.setMinThreads(paramtype.getMinThreads());
        params.setThreadNamePrefix(paramtype.getThreadNamePrefix());
        return params;
    }

    private static Map<String, TLSServerParameters> toTLSServerParamenters(List<TLSServerParametersIdentifiedType> list) {
        TreeMap<String, TLSServerParameters> map = new TreeMap<String, TLSServerParameters>();
        for (TLSServerParametersIdentifiedType t2 : list) {
            try {
                TLSServerParametersConfig parameter = new TLSServerParametersConfig(t2.getTlsServerParameters());
                map.put(t2.getId(), parameter);
            }
            catch (Exception e) {
                throw new RuntimeException("Could not configure TLS for id " + t2.getId(), e);
            }
        }
        return map;
    }

    public Map<String, ThreadingParameters> createThreadingParametersMap(String s2, JAXBContext ctx) throws Exception {
        Document doc = StaxUtils.read(new StringReader(s2));
        List<ThreadingParametersIdentifiedType> threadingParametersIdentifiedTypes = JettySpringTypesFactory.parseListElement(doc.getDocumentElement(), new QName("http://cxf.apache.org/transports/http-jetty/configuration", "identifiedThreadingParameters"), ThreadingParametersIdentifiedType.class, ctx);
        return JettySpringTypesFactory.toThreadingParameters(threadingParametersIdentifiedTypes);
    }

    public Map<String, TLSServerParameters> createTLSServerParametersMap(String s2, JAXBContext ctx) throws Exception {
        Document doc = StaxUtils.read(new StringReader(s2));
        List<TLSServerParametersIdentifiedType> tlsServerParameters = JettySpringTypesFactory.parseListElement(doc.getDocumentElement(), new QName("http://cxf.apache.org/transports/http-jetty/configuration", "identifiedTLSServerParameters"), TLSServerParametersIdentifiedType.class, ctx);
        return JettySpringTypesFactory.toTLSServerParamenters(tlsServerParameters);
    }

    public static <V> List<V> parseListElement(Element parent, QName name, Class<?> c, JAXBContext context) throws JAXBException {
        ArrayList<Object> list = new ArrayList<Object>();
        Node data = null;
        Unmarshaller u = context.createUnmarshaller();
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            Object obj;
            if (node.getNodeType() != 1 || !name.getLocalPart().equals(node.getLocalName()) || !name.getNamespaceURI().equals(node.getNamespaceURI()) || (obj = JettySpringTypesFactory.unmarshal(u, data = node, c)) == null) continue;
            list.add(obj);
        }
        return list;
    }

    private static Object unmarshal(Unmarshaller u, Node data, Class<?> c) {
        if (u == null) {
            return null;
        }
        JAXBElement<?> obj = null;
        try {
            obj = c != null ? u.unmarshal(data, c) : u.unmarshal(data);
            if (obj instanceof JAXBElement) {
                JAXBElement<?> el = obj;
                obj = el.getValue();
            }
        }
        catch (JAXBException e) {
            throw new RuntimeException("Could not parse configuration.", e);
        }
        return obj;
    }
}

