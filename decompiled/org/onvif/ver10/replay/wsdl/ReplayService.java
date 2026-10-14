/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.replay.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.replay.wsdl.ReplayPort;

@WebServiceClient(name="ReplayService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/replay/wsdl")
public class ReplayService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName ReplayPort;

    public ReplayService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ReplayService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReplayService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ReplayService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ReplayService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ReplayService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="ReplayPort")
    public ReplayPort getReplayPort() {
        return super.getPort(ReplayPort, ReplayPort.class);
    }

    @WebEndpoint(name="ReplayPort")
    public ReplayPort getReplayPort(WebServiceFeature ... features) {
        return super.getPort(ReplayPort, ReplayPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/replay/wsdl", "ReplayService");
        ReplayPort = new QName("http://www.onvif.org/ver10/replay/wsdl", "ReplayPort");
        WSDL_LOCATION = null;
    }
}

