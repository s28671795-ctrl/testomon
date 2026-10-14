/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.doorcontrol.wsdl.DoorControlPort;

@WebServiceClient(name="DoorControlService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl")
public class DoorControlService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName DoorControlPort;

    public DoorControlService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DoorControlService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DoorControlService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DoorControlService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DoorControlService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DoorControlService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="DoorControlPort")
    public DoorControlPort getDoorControlPort() {
        return super.getPort(DoorControlPort, DoorControlPort.class);
    }

    @WebEndpoint(name="DoorControlPort")
    public DoorControlPort getDoorControlPort(WebServiceFeature ... features) {
        return super.getPort(DoorControlPort, DoorControlPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/doorcontrol/wsdl", "DoorControlService");
        DoorControlPort = new QName("http://www.onvif.org/ver10/doorcontrol/wsdl", "DoorControlPort");
        WSDL_LOCATION = null;
    }
}

