/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractDescriptionElement;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.NamedItem;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public class EndpointInfo
extends AbstractDescriptionElement
implements NamedItem {
    String transportId;
    ServiceInfo service;
    BindingInfo binding;
    QName name;
    EndpointReferenceType address;

    public EndpointInfo() {
    }

    public EndpointInfo(ServiceInfo serv, String ns) {
        this.transportId = ns;
        this.service = serv;
    }

    @Override
    public DescriptionInfo getDescription() {
        if (this.service == null) {
            return null;
        }
        return this.service.getDescription();
    }

    public String getTransportId() {
        return this.transportId;
    }

    public void setTransportId(String tid) {
        this.transportId = tid;
    }

    public InterfaceInfo getInterface() {
        if (this.service == null) {
            return null;
        }
        return this.service.getInterface();
    }

    public void setService(ServiceInfo s2) {
        this.service = s2;
    }

    public ServiceInfo getService() {
        return this.service;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    public void setName(QName n) {
        this.name = n;
    }

    public BindingInfo getBinding() {
        return this.binding;
    }

    public void setBinding(BindingInfo b) {
        this.binding = b;
    }

    public String getAddress() {
        return null != this.address && null != this.address.getAddress() ? this.address.getAddress().getValue() : null;
    }

    public void setAddress(String addr) {
        if (null == this.address) {
            EndpointReferenceType reference = new EndpointReferenceType();
            AttributedURIType a = new AttributedURIType();
            a.setValue(addr);
            reference.setAddress(a);
            this.address = reference;
        } else {
            AttributedURIType a = new AttributedURIType();
            a.setValue(addr);
            this.address.setAddress(a);
        }
    }

    public void setAddress(EndpointReferenceType endpointReference) {
        this.address = endpointReference;
    }

    @Override
    public <T> T getTraversedExtensor(T defaultValue, Class<T> type) {
        T value = this.getExtensor(type);
        if (value == null) {
            if (this.binding != null) {
                value = this.binding.getExtensor(type);
            }
            if (this.service != null && value == null) {
                value = this.service.getExtensor(type);
            }
            if (value == null) {
                value = defaultValue;
            }
        }
        return value;
    }

    public EndpointReferenceType getTarget() {
        return this.address;
    }

    public boolean isSameAs(EndpointInfo epInfo) {
        if (this == epInfo) {
            return true;
        }
        if (epInfo == null) {
            return false;
        }
        return this.binding.getName().equals(epInfo.binding.getName()) && this.service.getName().equals(epInfo.service.getName()) && this.name.equals(epInfo.name);
    }

    public String toString() {
        return "BindingQName=" + (this.binding == null ? "" : this.binding.getName() + ", ServiceQName=" + (this.binding.getService() == null ? "" : this.binding.getService().getName())) + ", QName=" + this.name;
    }
}

