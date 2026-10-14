/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver20.imaging.wsdl.ImagingPort;

@WebServiceClient(name="ImagingService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
public class ImagingService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName ImagingPort;

    public ImagingService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ImagingService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ImagingService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ImagingService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ImagingService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ImagingService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="ImagingPort")
    public ImagingPort getImagingPort() {
        return super.getPort(ImagingPort, ImagingPort.class);
    }

    @WebEndpoint(name="ImagingPort")
    public ImagingPort getImagingPort(WebServiceFeature ... features) {
        return super.getPort(ImagingPort, ImagingPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver20/imaging/wsdl", "ImagingService");
        ImagingPort = new QName("http://www.onvif.org/ver20/imaging/wsdl", "ImagingPort");
        WSDL_LOCATION = null;
    }
}

