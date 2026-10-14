/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.device.wsdl.Device;

@WebServiceClient(name="DeviceService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
public class DeviceService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName DevicePort;

    public DeviceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DeviceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DeviceService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DeviceService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DeviceService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DeviceService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="DevicePort")
    public Device getDevicePort() {
        return super.getPort(DevicePort, Device.class);
    }

    @WebEndpoint(name="DevicePort")
    public Device getDevicePort(WebServiceFeature ... features) {
        return super.getPort(DevicePort, Device.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/device/wsdl", "DeviceService");
        DevicePort = new QName("http://www.onvif.org/ver10/device/wsdl", "DevicePort");
        WSDL_LOCATION = null;
    }
}

