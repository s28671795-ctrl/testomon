/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.deviceio.wsdl.DeviceIOPort;

@WebServiceClient(name="DeviceIOPService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
public class DeviceIOPService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName DeviceIOPort;

    public DeviceIOPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DeviceIOPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DeviceIOPService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DeviceIOPService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DeviceIOPService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DeviceIOPService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="DeviceIOPort")
    public DeviceIOPort getDeviceIOPort() {
        return super.getPort(DeviceIOPort, DeviceIOPort.class);
    }

    @WebEndpoint(name="DeviceIOPort")
    public DeviceIOPort getDeviceIOPort(WebServiceFeature ... features) {
        return super.getPort(DeviceIOPort, DeviceIOPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "DeviceIOPService");
        DeviceIOPort = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "DeviceIOPort");
        WSDL_LOCATION = null;
    }
}

