/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.network.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.network.wsdl.DiscoveryLookupPort;
import org.onvif.ver10.network.wsdl.RemoteDiscoveryPort;

@WebServiceClient(name="DiscoveryService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/network/wsdl")
public class DiscoveryService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName RemoteDiscoveryPort;
    public static final QName DiscoveryLookupPort;

    public DiscoveryService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DiscoveryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DiscoveryService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DiscoveryService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DiscoveryService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DiscoveryService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="RemoteDiscoveryPort")
    public RemoteDiscoveryPort getRemoteDiscoveryPort() {
        return super.getPort(RemoteDiscoveryPort, RemoteDiscoveryPort.class);
    }

    @WebEndpoint(name="RemoteDiscoveryPort")
    public RemoteDiscoveryPort getRemoteDiscoveryPort(WebServiceFeature ... features) {
        return super.getPort(RemoteDiscoveryPort, RemoteDiscoveryPort.class, features);
    }

    @WebEndpoint(name="DiscoveryLookupPort")
    public DiscoveryLookupPort getDiscoveryLookupPort() {
        return super.getPort(DiscoveryLookupPort, DiscoveryLookupPort.class);
    }

    @WebEndpoint(name="DiscoveryLookupPort")
    public DiscoveryLookupPort getDiscoveryLookupPort(WebServiceFeature ... features) {
        return super.getPort(DiscoveryLookupPort, DiscoveryLookupPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/network/wsdl", "DiscoveryService");
        RemoteDiscoveryPort = new QName("http://www.onvif.org/ver10/network/wsdl", "RemoteDiscoveryPort");
        DiscoveryLookupPort = new QName("http://www.onvif.org/ver10/network/wsdl", "DiscoveryLookupPort");
        WSDL_LOCATION = null;
    }
}

