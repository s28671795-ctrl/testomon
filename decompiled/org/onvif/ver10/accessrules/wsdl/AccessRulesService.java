/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.accessrules.wsdl.AccessRulesPort;

@WebServiceClient(name="AccessRulesService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/accessrules/wsdl")
public class AccessRulesService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName AccessRulesPort;

    public AccessRulesService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AccessRulesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccessRulesService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public AccessRulesService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public AccessRulesService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public AccessRulesService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="AccessRulesPort")
    public AccessRulesPort getAccessRulesPort() {
        return super.getPort(AccessRulesPort, AccessRulesPort.class);
    }

    @WebEndpoint(name="AccessRulesPort")
    public AccessRulesPort getAccessRulesPort(WebServiceFeature ... features) {
        return super.getPort(AccessRulesPort, AccessRulesPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/accessrules/wsdl", "AccessRulesService");
        AccessRulesPort = new QName("http://www.onvif.org/ver10/accessrules/wsdl", "AccessRulesPort");
        WSDL_LOCATION = null;
    }
}

