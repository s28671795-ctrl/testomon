/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.display.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.display.wsdl.DisplayPort;

@WebServiceClient(name="DisplayService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/display/wsdl")
public class DisplayService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName DisplayPort;

    public DisplayService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DisplayService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DisplayService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DisplayService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DisplayService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DisplayService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="DisplayPort")
    public DisplayPort getDisplayPort() {
        return super.getPort(DisplayPort, DisplayPort.class);
    }

    @WebEndpoint(name="DisplayPort")
    public DisplayPort getDisplayPort(WebServiceFeature ... features) {
        return super.getPort(DisplayPort, DisplayPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/display/wsdl", "DisplayService");
        DisplayPort = new QName("http://www.onvif.org/ver10/display/wsdl", "DisplayPort");
        WSDL_LOCATION = null;
    }
}

