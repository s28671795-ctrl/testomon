/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.oasis_open.docs.wsn.brw_2.NotificationBroker;

@WebServiceClient(name="NotificationBrokerService", wsdlLocation="file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl", targetNamespace="http://cxf.apache.org/wsn/jaxws")
public class NotificationBrokerService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName NotificationBrokerPort;

    public NotificationBrokerService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NotificationBrokerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NotificationBrokerService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public NotificationBrokerService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public NotificationBrokerService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public NotificationBrokerService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="NotificationBrokerPort")
    public NotificationBroker getNotificationBrokerPort() {
        return super.getPort(NotificationBrokerPort, NotificationBroker.class);
    }

    @WebEndpoint(name="NotificationBrokerPort")
    public NotificationBroker getNotificationBrokerPort(WebServiceFeature ... features) {
        return super.getPort(NotificationBrokerPort, NotificationBroker.class, features);
    }

    static {
        SERVICE = new QName("http://cxf.apache.org/wsn/jaxws", "NotificationBrokerService");
        NotificationBrokerPort = new QName("http://cxf.apache.org/wsn/jaxws", "NotificationBrokerPort");
        URL url2 = null;
        try {
            url2 = new URL("file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(NotificationBrokerService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        WSDL_LOCATION = url2;
    }
}

