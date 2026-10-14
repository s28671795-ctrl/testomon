/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.transports.http_jetty.configuration.JettyHTTPServerEngineConfigType;
import org.apache.cxf.transports.http_jetty.configuration.JettyHTTPServerEngineFactoryConfigType;
import org.apache.cxf.transports.http_jetty.configuration.ParametersRefType;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _EngineFactory_QNAME = new QName("http://cxf.apache.org/transports/http-jetty/configuration", "engine-factory");

    public JettyHTTPServerEngineFactoryConfigType createJettyHTTPServerEngineFactoryConfigType() {
        return new JettyHTTPServerEngineFactoryConfigType();
    }

    public ThreadingParametersType createThreadingParametersType() {
        return new ThreadingParametersType();
    }

    public ThreadingParametersIdentifiedType createThreadingParametersIdentifiedType() {
        return new ThreadingParametersIdentifiedType();
    }

    public TLSServerParametersIdentifiedType createTLSServerParametersIdentifiedType() {
        return new TLSServerParametersIdentifiedType();
    }

    public ParametersRefType createParametersRefType() {
        return new ParametersRefType();
    }

    public JettyHTTPServerEngineConfigType createJettyHTTPServerEngineConfigType() {
        return new JettyHTTPServerEngineConfigType();
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/transports/http-jetty/configuration", name="engine-factory")
    public JAXBElement<JettyHTTPServerEngineFactoryConfigType> createEngineFactory(JettyHTTPServerEngineFactoryConfigType value) {
        return new JAXBElement<JettyHTTPServerEngineFactoryConfigType>(_EngineFactory_QNAME, JettyHTTPServerEngineFactoryConfigType.class, null, value);
    }
}

