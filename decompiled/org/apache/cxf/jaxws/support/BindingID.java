/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

public final class BindingID {
    private BindingID() {
    }

    public static String getJaxwsBindingID(String transportID) {
        if ("http://schemas.xmlsoap.org/soap/http".equals(transportID)) {
            return "http://schemas.xmlsoap.org/wsdl/soap/http";
        }
        if ("http://www.w3.org/2003/05/soap/bindings/HTTP/".equals(transportID)) {
            return "http://www.w3.org/2003/05/soap/bindings/HTTP/";
        }
        return transportID;
    }

    public static String getBindingID(String jaxwsBindingID) {
        if ("http://schemas.xmlsoap.org/wsdl/soap/http".equals(jaxwsBindingID) || "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true".equals(jaxwsBindingID)) {
            return "http://schemas.xmlsoap.org/wsdl/soap/";
        }
        if ("http://www.w3.org/2003/05/soap/bindings/HTTP/".equals(jaxwsBindingID) || "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true".equals(jaxwsBindingID)) {
            return "http://schemas.xmlsoap.org/wsdl/soap12/";
        }
        return jaxwsBindingID;
    }
}

