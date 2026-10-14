/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.wsn.util.CXFWSNHelper;
import org.apache.cxf.wsn.wsdl.WSNWSDLLocator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WSNHelper {
    private static volatile WSNHelper instance;
    protected boolean setClassLoader = true;

    public static WSNHelper getInstance() {
        if (instance == null) {
            WSNHelper.createInstance();
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private static synchronized void createInstance() {
        if (instance != null) {
            return;
        }
        Provider p = Provider.provider();
        instance = p.getClass().getName().contains("apache.cxf") ? new CXFWSNHelper() : new WSNHelper();
    }

    public boolean setClassLoader() {
        return this.setClassLoader;
    }

    public void setClassLoader(boolean cl) {
        this.setClassLoader = cl;
    }

    public boolean supportsExtraClasses() {
        return false;
    }

    public Endpoint publish(String address, Object o, Class<?> ... extraClasses) {
        if (extraClasses != null && extraClasses.length > 0) {
            throw new UnsupportedOperationException("Pure JAX-WS does not support the extraClasses");
        }
        Endpoint endpoint = Endpoint.create(o);
        URL wsdlLocation = WSNWSDLLocator.getWSDLUrl();
        if (wsdlLocation != null) {
            try {
                if (endpoint.getProperties() == null) {
                    endpoint.setProperties(new HashMap<String, Object>());
                }
                endpoint.getProperties().put("javax.xml.ws.wsdl.description", wsdlLocation.toExternalForm());
                ArrayList<Source> mt = new ArrayList<Source>();
                StreamSource src = new StreamSource(wsdlLocation.openStream(), wsdlLocation.toExternalForm());
                mt.add(src);
                endpoint.setMetadata(mt);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        endpoint.publish(address);
        return endpoint;
    }

    public <T> T getPort(EndpointReference ref, Class<T> serviceInterface, Class<?> ... extraClasses) {
        if (!(ref instanceof W3CEndpointReference)) {
            throw new IllegalArgumentException("Unsupported endpoint reference: " + (ref != null ? ref.toString() : "null"));
        }
        W3CEndpointReference w3cEpr = (W3CEndpointReference)ref;
        String address = this.getWSAAddress(w3cEpr);
        return this.getPort(address, serviceInterface, extraClasses);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T getPort(String address, Class<T> serviceInterface, Class<?> ... extraClasses) {
        if (extraClasses != null && extraClasses.length > 0) {
            throw new UnsupportedOperationException("Pure JAX-WS does not support the extraClasses");
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            if (this.setClassLoader) {
                Thread.currentThread().setContextClassLoader(WSNHelper.class.getClassLoader());
            }
            Service service2 = Service.create(WSNWSDLLocator.getWSDLUrl(), new QName("http://cxf.apache.org/wsn/jaxws", serviceInterface.getSimpleName() + "Service"));
            T t2 = service2.getPort(this.createWSA(address), serviceInterface, new WebServiceFeature[0]);
            return t2;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public W3CEndpointReference createWSA(String address) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            if (this.setClassLoader) {
                Thread.currentThread().setContextClassLoader(WSNHelper.class.getClassLoader());
            }
            W3CEndpointReference w3CEndpointReference = new W3CEndpointReferenceBuilder().address(address).build();
            return w3CEndpointReference;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    public String getWSAAddress(W3CEndpointReference ref) {
        Element element = DOMUtils.getEmptyDocument().createElement("elem");
        ref.writeTo(new DOMResult(element));
        NodeList nl = element.getElementsByTagNameNS("http://www.w3.org/2005/08/addressing", "Address");
        if (nl != null && nl.getLength() > 0) {
            Element e = (Element)nl.item(0);
            return DOMUtils.getContent(e).trim();
        }
        return null;
    }
}

