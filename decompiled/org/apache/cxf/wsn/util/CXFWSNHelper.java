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
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.wsn.util.WSNHelper;
import org.apache.cxf.wsn.wsdl.WSNWSDLLocator;

public class CXFWSNHelper
extends WSNHelper {
    @Override
    public boolean supportsExtraClasses() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T getPort(String address, Class<T> serviceInterface, Class<?> ... extraClasses) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            if (this.setClassLoader) {
                Thread.currentThread().setContextClassLoader(WSNHelper.class.getClassLoader());
            }
            JaxWsProxyFactoryBean jwfb = new JaxWsProxyFactoryBean();
            jwfb.getClientFactoryBean().setWsdlURL(WSNWSDLLocator.getWSDLUrl().toExternalForm());
            jwfb.setServiceName(new QName("http://cxf.apache.org/wsn/jaxws", serviceInterface.getSimpleName() + "Service"));
            jwfb.setEndpointName(new QName("http://cxf.apache.org/wsn/jaxws", serviceInterface.getSimpleName() + "Port"));
            jwfb.setAddress(address);
            if (extraClasses != null && extraClasses.length > 0) {
                HashMap<String, Object> props = new HashMap<String, Object>();
                props.put("jaxb.additionalContextClasses", extraClasses);
                jwfb.getClientFactoryBean().getServiceFactory().setProperties(props);
            }
            T t2 = jwfb.create(serviceInterface);
            return t2;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    @Override
    public Endpoint publish(String address, Object o, Class<?> ... extraClasses) {
        URL wsdlLocation;
        Endpoint endpoint = Endpoint.create("http://www.w3.org/2003/05/soap/bindings/HTTP/", o);
        if (extraClasses != null && extraClasses.length > 0) {
            HashMap<String, Object> props = new HashMap<String, Object>();
            props.put("jaxb.additionalContextClasses", extraClasses);
            endpoint.setProperties(props);
        }
        if ((wsdlLocation = WSNWSDLLocator.getWSDLUrl()) != null) {
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
}

