/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver20.ptz.wsdl.PTZ;

@WebServiceClient(name="PtzService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
public class PtzService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName PtzPort;

    public PtzService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PtzService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PtzService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public PtzService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public PtzService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public PtzService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="PtzPort")
    public PTZ getPtzPort() {
        return super.getPort(PtzPort, PTZ.class);
    }

    @WebEndpoint(name="PtzPort")
    public PTZ getPtzPort(WebServiceFeature ... features) {
        return super.getPort(PtzPort, PTZ.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver20/ptz/wsdl", "PtzService");
        PtzPort = new QName("http://www.onvif.org/ver20/ptz/wsdl", "PtzPort");
        WSDL_LOCATION = null;
    }
}

