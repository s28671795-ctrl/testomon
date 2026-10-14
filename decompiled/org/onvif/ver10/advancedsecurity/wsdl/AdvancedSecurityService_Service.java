/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.advancedsecurity.wsdl.AdvancedSecurityService;
import org.onvif.ver10.advancedsecurity.wsdl.Keystore;
import org.onvif.ver10.advancedsecurity.wsdl.TLSServer;

@WebServiceClient(name="AdvancedSecurityService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
public class AdvancedSecurityService_Service
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName TLSServerPort;
    public static final QName KeystorePort;
    public static final QName AdvancedSecurityServicePort;

    public AdvancedSecurityService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AdvancedSecurityService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AdvancedSecurityService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public AdvancedSecurityService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public AdvancedSecurityService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public AdvancedSecurityService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="TLSServerPort")
    public TLSServer getTLSServerPort() {
        return super.getPort(TLSServerPort, TLSServer.class);
    }

    @WebEndpoint(name="TLSServerPort")
    public TLSServer getTLSServerPort(WebServiceFeature ... features) {
        return super.getPort(TLSServerPort, TLSServer.class, features);
    }

    @WebEndpoint(name="KeystorePort")
    public Keystore getKeystorePort() {
        return super.getPort(KeystorePort, Keystore.class);
    }

    @WebEndpoint(name="KeystorePort")
    public Keystore getKeystorePort(WebServiceFeature ... features) {
        return super.getPort(KeystorePort, Keystore.class, features);
    }

    @WebEndpoint(name="AdvancedSecurityServicePort")
    public AdvancedSecurityService getAdvancedSecurityServicePort() {
        return super.getPort(AdvancedSecurityServicePort, AdvancedSecurityService.class);
    }

    @WebEndpoint(name="AdvancedSecurityServicePort")
    public AdvancedSecurityService getAdvancedSecurityServicePort(WebServiceFeature ... features) {
        return super.getPort(AdvancedSecurityServicePort, AdvancedSecurityService.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "AdvancedSecurityService");
        TLSServerPort = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "TLSServerPort");
        KeystorePort = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "KeystorePort");
        AdvancedSecurityServicePort = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "AdvancedSecurityServicePort");
        WSDL_LOCATION = null;
    }
}

