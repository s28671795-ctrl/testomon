/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.events.wsdl.EventPortType;

@WebServiceClient(name="EventService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/events/wsdl")
public class EventService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName EventPort;

    public EventService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EventService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EventService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public EventService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public EventService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public EventService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="EventPort")
    public EventPortType getEventPort() {
        return super.getPort(EventPort, EventPortType.class);
    }

    @WebEndpoint(name="EventPort")
    public EventPortType getEventPort(WebServiceFeature ... features) {
        return super.getPort(EventPort, EventPortType.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/events/wsdl", "EventService");
        EventPort = new QName("http://www.onvif.org/ver10/events/wsdl", "EventPort");
        WSDL_LOCATION = null;
    }
}

