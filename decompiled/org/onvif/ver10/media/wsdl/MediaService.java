/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.media.wsdl.Media;

@WebServiceClient(name="MediaService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
public class MediaService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName MediaPort;

    public MediaService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MediaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MediaService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public MediaService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public MediaService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public MediaService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="MediaPort")
    public Media getMediaPort() {
        return super.getPort(MediaPort, Media.class);
    }

    @WebEndpoint(name="MediaPort")
    public Media getMediaPort(WebServiceFeature ... features) {
        return super.getPort(MediaPort, Media.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/media/wsdl", "MediaService");
        MediaPort = new QName("http://www.onvif.org/ver10/media/wsdl", "MediaPort");
        WSDL_LOCATION = null;
    }
}

