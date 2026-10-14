/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.recording.wsdl.RecordingPort;

@WebServiceClient(name="RecordingService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
public class RecordingService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName RecordingPort;

    public RecordingService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RecordingService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RecordingService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public RecordingService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public RecordingService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public RecordingService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="RecordingPort")
    public RecordingPort getRecordingPort() {
        return super.getPort(RecordingPort, RecordingPort.class);
    }

    @WebEndpoint(name="RecordingPort")
    public RecordingPort getRecordingPort(WebServiceFeature ... features) {
        return super.getPort(RecordingPort, RecordingPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/recording/wsdl", "RecordingService");
        RecordingPort = new QName("http://www.onvif.org/ver10/recording/wsdl", "RecordingPort");
        WSDL_LOCATION = null;
    }
}

