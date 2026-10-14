/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.onvif.ver10.search.wsdl.SearchPort;

@WebServiceClient(name="SearchService", wsdlLocation="null", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
public class SearchService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName SearchPort;

    public SearchService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SearchService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SearchService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SearchService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SearchService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SearchService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="SearchPort")
    public SearchPort getSearchPort() {
        return super.getPort(SearchPort, SearchPort.class);
    }

    @WebEndpoint(name="SearchPort")
    public SearchPort getSearchPort(WebServiceFeature ... features) {
        return super.getPort(SearchPort, SearchPort.class, features);
    }

    static {
        SERVICE = new QName("http://www.onvif.org/ver10/search/wsdl", "SearchService");
        SearchPort = new QName("http://www.onvif.org/ver10/search/wsdl", "SearchPort");
        WSDL_LOCATION = null;
    }
}

