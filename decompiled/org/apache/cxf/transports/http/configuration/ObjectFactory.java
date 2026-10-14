/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http.configuration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Server_QNAME = new QName("http://cxf.apache.org/transports/http/configuration", "server");
    private static final QName _Client_QNAME = new QName("http://cxf.apache.org/transports/http/configuration", "client");
    private static final QName _ContextMatchStrategy_QNAME = new QName("http://cxf.apache.org/transports/http/configuration", "contextMatchStrategy");
    private static final QName _FixedParameterOrder_QNAME = new QName("http://cxf.apache.org/transports/http/configuration", "fixedParameterOrder");

    public HTTPServerPolicy createHTTPServerPolicy() {
        return new HTTPServerPolicy();
    }

    public HTTPClientPolicy createHTTPClientPolicy() {
        return new HTTPClientPolicy();
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/transports/http/configuration", name="server")
    public JAXBElement<HTTPServerPolicy> createServer(HTTPServerPolicy value) {
        return new JAXBElement<HTTPServerPolicy>(_Server_QNAME, HTTPServerPolicy.class, null, value);
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/transports/http/configuration", name="client")
    public JAXBElement<HTTPClientPolicy> createClient(HTTPClientPolicy value) {
        return new JAXBElement<HTTPClientPolicy>(_Client_QNAME, HTTPClientPolicy.class, null, value);
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/transports/http/configuration", name="contextMatchStrategy", defaultValue="stem")
    public JAXBElement<String> createContextMatchStrategy(String value) {
        return new JAXBElement<String>(_ContextMatchStrategy_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/transports/http/configuration", name="fixedParameterOrder", defaultValue="false")
    public JAXBElement<Boolean> createFixedParameterOrder(Boolean value) {
        return new JAXBElement<Boolean>(_FixedParameterOrder_QNAME, Boolean.class, null, value);
    }
}

