/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;

@NoJSR250Annotations
public class SoapBindingConfiguration
extends BindingConfiguration {
    private SoapVersion soapVersion = Soap11.getInstance();
    private String style;
    private String use;
    private String transportURI = "http://schemas.xmlsoap.org/soap/http";
    private String defaultSoapAction = "";
    private boolean mtomEnabled;
    private QName bindingName;
    private String bindingNamePostfix = "SoapBinding";

    @Override
    public String getBindingId() {
        return "http://schemas.xmlsoap.org/soap/";
    }

    protected boolean isHeader(BindingOperationInfo op, MessagePartInfo part) {
        Object isHeader = part.getProperty("messagepart.isheader");
        return Boolean.TRUE.equals(isHeader);
    }

    public String getSoapAction(OperationInfo op) {
        String action = (String)op.getProperty("action");
        if (action == null) {
            return this.defaultSoapAction;
        }
        return action;
    }

    public String getTransportURI() {
        return this.transportURI;
    }

    public void setTransportURI(String transportURI) {
        this.transportURI = transportURI;
    }

    public boolean isSetStyle() {
        return this.style != null;
    }

    public String getStyle() {
        return this.style == null ? "document" : this.style;
    }

    public String getStyle(OperationInfo op) {
        return this.getStyle();
    }

    public SoapVersion getVersion() {
        return this.soapVersion;
    }

    public void setVersion(SoapVersion sv) {
        this.soapVersion = sv;
    }

    public String getUse() {
        return this.use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isMtomEnabled() {
        return this.mtomEnabled;
    }

    public void setMtomEnabled(boolean mtomEnabled) {
        this.mtomEnabled = mtomEnabled;
    }

    public QName getBindingName(ServiceInfo si) {
        if (this.bindingName == null) {
            return new QName(si.getName().getNamespaceURI(), si.getName().getLocalPart() + this.bindingNamePostfix);
        }
        return this.bindingName;
    }

    public QName getBindingName() {
        return this.bindingName;
    }

    public void setBindingName(QName b) {
        this.bindingName = b;
    }

    public String getBindingNamePostfix() {
        return this.bindingNamePostfix;
    }

    public void setBindingNamePostfix(String b) {
        this.bindingNamePostfix = b;
    }
}

