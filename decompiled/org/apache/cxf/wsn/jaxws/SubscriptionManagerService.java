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
import org.oasis_open.docs.wsn.bw_2.SubscriptionManager;

@WebServiceClient(name="SubscriptionManagerService", wsdlLocation="file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl", targetNamespace="http://cxf.apache.org/wsn/jaxws")
public class SubscriptionManagerService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName SubscriptionManagerPort;

    public SubscriptionManagerService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SubscriptionManagerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SubscriptionManagerService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SubscriptionManagerService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SubscriptionManagerService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SubscriptionManagerService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="SubscriptionManagerPort")
    public SubscriptionManager getSubscriptionManagerPort() {
        return super.getPort(SubscriptionManagerPort, SubscriptionManager.class);
    }

    @WebEndpoint(name="SubscriptionManagerPort")
    public SubscriptionManager getSubscriptionManagerPort(WebServiceFeature ... features) {
        return super.getPort(SubscriptionManagerPort, SubscriptionManager.class, features);
    }

    static {
        SERVICE = new QName("http://cxf.apache.org/wsn/jaxws", "SubscriptionManagerService");
        SubscriptionManagerPort = new QName("http://cxf.apache.org/wsn/jaxws", "SubscriptionManagerPort");
        URL url2 = null;
        try {
            url2 = new URL("file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(SubscriptionManagerService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        WSDL_LOCATION = url2;
    }
}

