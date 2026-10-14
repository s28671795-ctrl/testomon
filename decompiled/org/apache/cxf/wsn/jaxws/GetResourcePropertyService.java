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
import org.oasis_open.docs.wsrf.rpw_2.GetResourceProperty;

@WebServiceClient(name="GetResourcePropertyService", wsdlLocation="file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl", targetNamespace="http://cxf.apache.org/wsn/jaxws")
public class GetResourcePropertyService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName GetResourcePropertyPort;

    public GetResourcePropertyService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetResourcePropertyService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetResourcePropertyService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public GetResourcePropertyService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public GetResourcePropertyService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public GetResourcePropertyService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="GetResourcePropertyPort")
    public GetResourceProperty getGetResourcePropertyPort() {
        return super.getPort(GetResourcePropertyPort, GetResourceProperty.class);
    }

    @WebEndpoint(name="GetResourcePropertyPort")
    public GetResourceProperty getGetResourcePropertyPort(WebServiceFeature ... features) {
        return super.getPort(GetResourcePropertyPort, GetResourceProperty.class, features);
    }

    static {
        SERVICE = new QName("http://cxf.apache.org/wsn/jaxws", "GetResourcePropertyService");
        GetResourcePropertyPort = new QName("http://cxf.apache.org/wsn/jaxws", "GetResourcePropertyPort");
        URL url2 = null;
        try {
            url2 = new URL("file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(GetResourcePropertyService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "file:/Users/dkulp/working/cxf/target/checkout/services/wsn/wsn-api/src/main/resources/org/apache/cxf/wsn/wsdl/wsn.wsdl");
        }
        WSDL_LOCATION = url2;
    }
}

