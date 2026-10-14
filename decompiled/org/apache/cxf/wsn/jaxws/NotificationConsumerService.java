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
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;

@WebServiceClient(name="NotificationConsumerService", wsdlLocation="file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl", targetNamespace="http://cxf.apache.org/wsn/jaxws")
public class NotificationConsumerService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName NotificationConsumerPort;

    public NotificationConsumerService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NotificationConsumerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NotificationConsumerService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public NotificationConsumerService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public NotificationConsumerService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public NotificationConsumerService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="NotificationConsumerPort")
    public NotificationConsumer getNotificationConsumerPort() {
        return super.getPort(NotificationConsumerPort, NotificationConsumer.class);
    }

    @WebEndpoint(name="NotificationConsumerPort")
    public NotificationConsumer getNotificationConsumerPort(WebServiceFeature ... features) {
        return super.getPort(NotificationConsumerPort, NotificationConsumer.class, features);
    }

    static {
        SERVICE = new QName("http://cxf.apache.org/wsn/jaxws", "NotificationConsumerService");
        NotificationConsumerPort = new QName("http://cxf.apache.org/wsn/jaxws", "NotificationConsumerPort");
        URL url2 = null;
        try {
            url2 = new URL("file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(NotificationConsumerService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        WSDL_LOCATION = url2;
    }
}

