/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.accesscontrol.wsdl.PACSPort;

@WebServiceClient(name="PACSService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/accesscontrol/wsdl")
public class PACSService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName PACSPort;

    public PACSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PACSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PACSService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public PACSService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public PACSService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public PACSService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="PACSPort")
    public PACSPort getPACSPort() {
        return super.getPort(PACSPort, PACSPort.class);
    }

    @WebEndpoint(name="PACSPort")
    public PACSPort getPACSPort(WebServiceFeature ... features) {
        return super.getPort(PACSPort, PACSPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/accesscontrol/wsdl", "PACSService");
        PACSPort = new QName("http://www.onvif.org/ver10/accesscontrol/wsdl", "PACSPort");
        WSDL_LOCATION = null;
    }
}

