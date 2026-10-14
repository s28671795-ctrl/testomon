/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.receiver.wsdl.ReceiverPort;

@WebServiceClient(name="ReceiverService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
public class ReceiverService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName ReceiverPort;

    public ReceiverService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ReceiverService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReceiverService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ReceiverService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ReceiverService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ReceiverService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="ReceiverPort")
    public ReceiverPort getReceiverPort() {
        return super.getPort(ReceiverPort, ReceiverPort.class);
    }

    @WebEndpoint(name="ReceiverPort")
    public ReceiverPort getReceiverPort(WebServiceFeature ... features) {
        return super.getPort(ReceiverPort, ReceiverPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/receiver/wsdl", "ReceiverService");
        ReceiverPort = new QName("http://www.onvif.org/ver10/receiver/wsdl", "ReceiverPort");
        WSDL_LOCATION = null;
    }
}

