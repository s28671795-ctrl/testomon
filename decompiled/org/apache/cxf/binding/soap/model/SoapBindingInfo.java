/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.model;

import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;

public class SoapBindingInfo
extends BindingInfo {
    private SoapVersion soapVersion;
    private String style;
    private String transportURI;

    public SoapBindingInfo(ServiceInfo serv, String n) {
        this(serv, n, null);
        this.resolveSoapVersion(n);
    }

    public SoapBindingInfo(ServiceInfo serv, String n, SoapVersion soapVersion) {
        super(serv, n);
        this.soapVersion = soapVersion;
    }

    private void resolveSoapVersion(String n) {
        if ("http://schemas.xmlsoap.org/wsdl/soap/".equalsIgnoreCase(n)) {
            this.soapVersion = Soap11.getInstance();
        } else if ("http://schemas.xmlsoap.org/wsdl/soap12/".equalsIgnoreCase(n)) {
            this.soapVersion = Soap12.getInstance();
        } else {
            throw new RuntimeException("Unknow bindingId: " + n + ". Can not resolve the SOAP version");
        }
    }

    public SoapVersion getSoapVersion() {
        if (this.soapVersion == null) {
            this.resolveSoapVersion(this.getBindingId());
        }
        return this.soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    public String getStyle() {
        return this.style;
    }

    public String getStyle(OperationInfo operation) {
        SoapOperationInfo opInfo = this.getOperation(operation.getName()).getExtensor(SoapOperationInfo.class);
        if (opInfo != null) {
            return opInfo.getStyle();
        }
        return this.style;
    }

    public OperationInfo getOperationByAction(String action) {
        for (BindingOperationInfo b : this.getOperations()) {
            SoapOperationInfo opInfo = b.getExtensor(SoapOperationInfo.class);
            if (!opInfo.getAction().equals(action)) continue;
            return b.getOperationInfo();
        }
        return null;
    }

    public String getSoapAction(OperationInfo operation) {
        BindingOperationInfo b = this.getOperation(operation.getName());
        SoapOperationInfo opInfo = b.getExtensor(SoapOperationInfo.class);
        if (opInfo != null && opInfo.getAction() != null) {
            return opInfo.getAction();
        }
        return "";
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTransportURI() {
        return this.transportURI;
    }

    public void setTransportURI(String transportURI) {
        this.transportURI = transportURI;
    }
}

